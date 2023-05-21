package it.dani.selfhome.automation.model;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AutomationValidity {
    private DayOfWeek dow;
    private LocalDate date;
    private LocalTime time;

    public AutomationValidity(@NotNull LocalTime time)
    {
        this.time = time;
    }

    public AutomationValidity(@NotNull LocalTime time, @NotNull LocalDate date)
    {
        this(time);
        this.date = date;
    }

    public AutomationValidity(@NotNull LocalTime time, @NotNull DayOfWeek dow)
    {
        this(time);
        this.dow = dow;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dow;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public boolean isValid()
    {
        boolean result = true;
        LocalDateTime now = LocalDateTime.now();

        if(this.getDayOfWeek() != null)
        {
            result = this.getDayOfWeek().equals(now.getDayOfWeek());
        }
        else if(this.getDate() != null)
        {
            result = this.getDate().getMonth().equals(now.getMonth());
            result &= this.getDate().getDayOfMonth() == now.getDayOfMonth();
        }

        result &= this.getTime().getHour() == now.getHour();
        return result && this.getTime().getMinute() == now.getMinute();
    }
}
