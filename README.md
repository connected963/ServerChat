**How to create a self signed certificate**
    
    Run the following command to create the key and the keystore:
    
        keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048
        
    On the question about your name, fill in with the domain name of the server that users will be entering to connect to your application.
    
    You should be sure that all password must be the same.
    
    Two properties should be added to your server code, before instantiate the socket:
    System.setProperty("javax.net.ssl.keyStore","your keystore");
    System.setProperty("javax.net.ssl.keyStorePassword", "your password");
    
    And, lastly, another two properties should be add to your client, to make possible that the communication happen:
    System.setProperty("javax.net.ssl.trustStore","your keystore");
    System.setProperty("javax.net.ssl.trustStorePassword", "your password");
    
    
    Links that can help:
        https://www.owasp.org/index.php/Using_the_Java_Secure_Socket_Extensions
        https://www.sslshopper.com/article-how-to-create-a-self-signed-certificate-using-java-keytool.html
        http://stackoverflow.com/questions/15967650/caused-by-java-security-unrecoverablekeyexception-cannot-recover-key
        http://stackoverflow.com/questions/29485987/ssl-exception-javax-net-ssl-sslhandshakeexception-received-fatal-alert-certif