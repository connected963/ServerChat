package request.tasks;

import communication.SocketHelper;
import request.protocol.enums.ProtocolEnum;
import request.interfaces.RequestManager;
import server.Server;

import javax.net.ssl.SSLSocket;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pedro.sirtoli on 20/01/2017.
 */
public class RequestManagerTask implements Callable<Void>, RequestManager {

    private static final Logger LOGGER = Logger.getLogger(RequestManagerTask.class.getName());

    private final AtomicLong countOfConnections;

    private boolean isCanceled;

    public RequestManagerTask(final AtomicLong countOfConnections) {
        Objects.requireNonNull(countOfConnections, "The Long countOfConnections can not be null");
        this.countOfConnections = countOfConnections;
        this.isCanceled = false;
    }

    @Override
    public Void call() throws Exception {

        while (!this.isCanceled) {
            waitForRequest();
        }

        return null;
    }

    @Override
    public void waitForRequest() {
        final SSLSocket socket;

        try {
            socket = (SSLSocket) Server.getInstance().getServerSocket().accept();

            this.countOfConnections.incrementAndGet();

            final ProtocolEnum action = ProtocolEnum.valueOf(SocketHelper.readInteger(socket)).orElseGet(() -> ProtocolEnum.ACTION_NOT_FOUND);

            action.execute(socket);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void cancel() {
        this.isCanceled = true;
    }
}
