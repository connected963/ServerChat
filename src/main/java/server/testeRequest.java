package server;

import communication.SocketHelper;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

/**
 * Created by pedro.sirtoli on 25/01/2017.
 */
public class testeRequest {

    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.trustStore","C:/Program Files/Java/jre1.8.0_121/bin/keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword","password");
       //System.setProperty("javax.net.debug","all");


        for (int j = 0; j < 1; j++) {

            new Thread(() -> {
                for (int i = 0; i < 1; i++) {
                    final SSLSocket sslSocket;

                    try {
                        final LocalTime start = LocalTime.now();

                        sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("127.0.0.1", 1212);

                        SocketHelper.sendInt(sslSocket, 1);

                        System.out.println("Thread:" + Duration.between(start, LocalTime.now()).getNano());

                        //Thread.sleep(550);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
