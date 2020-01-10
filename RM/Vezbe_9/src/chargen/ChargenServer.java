package chargen;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChargenServer {

    public static int DEFAULT_PORT = 19000;

    public static void main(String[] args) {
        System.out.println("Listening for connections on port " + DEFAULT_PORT);

        byte[] rotation = new byte[95*2];
        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()
        ) {
            if (!serverChannel.isOpen() || !selector.isOpen()) {
                System.err.println("The server socket channel or selector cannot be opened!");
                System.exit(1);
            }

            serverChannel.bind(new InetSocketAddress(DEFAULT_PORT));
            serverChannel.configureBlocking(false);

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = readyKeys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    try {
                       if (key.isAcceptable()) {
                           ServerSocketChannel server = (ServerSocketChannel) key.channel();
                           SocketChannel client = server.accept();

                           System.out.println("Accepted connection from " + client.getRemoteAddress());

                           client.configureBlocking(false);

                           SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE);

                           ByteBuffer buffer = ByteBuffer.allocate(74);

                           buffer.put(rotation,0, 72);
                           buffer.put((byte)'\r');
                           buffer.put((byte) '\n');
                           buffer.flip();

                           clientKey.attach(buffer);
                       } else if (key.isWritable()) {
                           SocketChannel client = (SocketChannel) key.channel();
                           ByteBuffer buffer = (ByteBuffer) key.attachment();

                           if (!buffer.hasRemaining()) {
                               buffer.rewind();
                               int first = buffer.get();
                               int position = first - ' ' + 1;
                               buffer.rewind();
                               buffer.put(rotation, position, 72);

                               buffer.put((byte)'\r');
                               buffer.put((byte)'\n');

                               buffer.flip();
                           }

                           client.write(buffer);
                       }
                    } catch (IOException e) {
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException cex) {
                            cex.printStackTrace();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
