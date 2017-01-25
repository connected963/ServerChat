package server;

import configurations.Parameters;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final SSLServerSocket serverSocket;

    private final Map<String, Connection> connections;

    private Server() {
        this.connections = new ConcurrentHashMap<>();

        try {
            final SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            this.serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(Parameters.socketPort);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalArgumentException("Socket port is invalid", e);
        }

    }

    public static Server getInstance() {
        return ServerInstance.instance;
    }

    public SSLServerSocket getServerSocket() {
        return serverSocket;
    }

    public Map<String, Connection> getConnections() {
        return connections;
    }

    private static class ServerInstance {
        private static final Server instance = new Server();
    }

}
