package chargen;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClientNonBlocking {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 19000;

        try {
            SocketAddress address = new InetSocketAddress(host, port);
            SocketChannel client = SocketChannel.open(address);

            ByteBuffer buffer = ByteBuffer.allocate(74);

            WritableByteChannel out = Channels.newChannel(System.out);

            client.configureBlocking(false);

            while (true) {
                int n = client.read(buffer);
                if (n>0) {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                } else if (n == -1) {
                    // This shouldn't happen unless the server is misbehaving.
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
