package exceptions;

public class ApplicationContextNotStartedException extends RuntimeException {
    public ApplicationContextNotStartedException() {
        super("Application context not started");
    }
}
