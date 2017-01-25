package request.protocol.execptions;

/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class ProtocolBreakException extends RuntimeException {

    public ProtocolBreakException() {
        super();
    }

    public ProtocolBreakException(String message) {
        super(message);
    }

    public ProtocolBreakException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolBreakException(Throwable cause) {
        super(cause);
    }
}
