package introSSL;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SSLSocketsIntro {

    public static void main(String[] args) {
        SSLSocket socket = null;

        String[] supportedCipherSuits = socket.getSupportedCipherSuites();

        String[] enabledCipherSuits = socket.getEnabledCipherSuites();

        class HandshakeInterfaceExample implements HandshakeCompletedListener {
            @Override
            public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
                SSLSession session = handshakeCompletedEvent.getSession();
                session.getProtocol();
                session.getPeerHost();

                handshakeCompletedEvent.getSocket();
                handshakeCompletedEvent.getCipherSuite();
            }
        }
    }
}
