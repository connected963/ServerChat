package request.interfaces;

import javax.net.ssl.SSLSocket;

/**
 * Created by pedro.sirtoli on 20/01/2017.
 */
@FunctionalInterface
public interface RequestAction {

    void execute(final SSLSocket socket);

}
