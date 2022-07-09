package it.dani.selfhomeapp.middle.view;

import java.util.function.Consumer;

class DisplayerThread<T> implements Runnable{

    private Consumer<T> consumer;
    private T element;

    DisplayerThread(Consumer<T> consumer, T element)
    {
        this.consumer = consumer;
        this.element = element;
    }

    @Override
    public void run()
    {
        this.consumer.accept(this.element);
    }
}
