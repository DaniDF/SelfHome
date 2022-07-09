package it.dani.selfhomeapp.middle.controller;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

class ControllerSlave <T> extends Thread {

    private final Supplier<T> supplier;
    private final Consumer<T> handler;
    private final Consumer<Exception> errorHandler;

    ControllerSlave(@NonNull Supplier<T> supplier, @NonNull Consumer<T> handler, Consumer<Exception> errorHandler)
    {
        this.supplier = supplier;
        this.handler = handler;
        this.errorHandler = errorHandler;
    }

    @Override
    public void run()
    {
        try
        {
            T result = this.supplier.get();
            this.handler.accept(result);
        } catch (Exception e) {
            if(this.errorHandler != null) this.errorHandler.accept(e);
        }
    }
}
