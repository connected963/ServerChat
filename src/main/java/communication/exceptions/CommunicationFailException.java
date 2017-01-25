package communication.exceptions;

/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class CommunicationFailException extends RuntimeException {

    public CommunicationFailException() {
        super();
    }

    public CommunicationFailException(String message) {
        super(message);
    }

    public CommunicationFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommunicationFailException(Throwable cause) {
        super(cause);
    }
}
