package it.dani.selfhomeapp.middle.persistence;

public enum PersistenceTimeout {

    INFINITE(0),
    STANDARD(3000);

    private int value;

    PersistenceTimeout(int value)
    {
        this.value = value;
    }

    public int valueOf()
    {
        return this.value;
    }



}
