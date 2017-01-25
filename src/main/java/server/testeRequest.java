package server;

import communication.SocketHelper;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class testeRequest {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 50; i++) {
            final SSLSocket sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("localhost", 1212);

            SocketHelper.sendInt(sslSocket, 1);
        }
    }
}
