package it.dani.selfhomeapp.middle.persistence;

public class SelfHomePersistenceException extends RuntimeException {

    public SelfHomePersistenceException() {
        super();
    }

    public SelfHomePersistenceException(String message) {
        super(message);
    }

    public SelfHomePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfHomePersistenceException(Throwable cause) {
        super(cause);
    }
}
