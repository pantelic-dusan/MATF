package simpleHttp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        Path publicHtmlDir = Paths.get("src/simpleHttp/public_html");
        SimpleHttpServer server = new SimpleHttpServer(publicHtmlDir, 12345, 5);
        server.startLogic();
    }


    private final Path publicHtmlDir;
    private final int maxCacheAliveTime;
    private final int port;
    private Map<String, ByteBuffer> responseBuffers;

    private SimpleHttpServer(Path publicHtmlDir, int port, int cacheAliveSeconds) throws IOException {
        this.publicHtmlDir = publicHtmlDir;
        this.port = port;
        this.maxCacheAliveTime = cacheAliveSeconds * 1000;
        this.fillLocalCache(this.publicHtmlDir);
    }

    private void startLogic() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
            Selector selector = Selector.open()
        ) {
            serverChannel.bind(new InetSocketAddress(this.port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.err.println("Server started!");

            long lastCacheUpdateTime = System.currentTimeMillis();
            int clients = 0;

            while (true) {
                if (clients == 0 && System.currentTimeMillis() - lastCacheUpdateTime >= this.maxCacheAliveTime) {
                    System.err.println("Updating server cache...");
                    this.fillLocalCache(this.publicHtmlDir);
                    lastCacheUpdateTime = System.currentTimeMillis();

                    selector.select();

                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        try {
                            if (key.isAcceptable()) {
                                ServerSocketChannel server = (ServerSocketChannel)key.channel();
                                SocketChannel channel = server.accept();
                                channel.configureBlocking(false);
                                channel.register(selector, SelectionKey.OP_READ);
                                System.err.println("Client found. Awaiting request...");
                                clients++;
                            } else if (key.isReadable()) {
                                SocketChannel channel = (SocketChannel)key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(4096);

                                channel.read(buffer);

                                String filename = new String(buffer.array())
                                                .codePoints()
                                        .takeWhile(c -> c > 32 && c < 127)
                                        .collect(StringBuilder::new,
                                                StringBuilder::appendCodePoint,
                                                StringBuilder::append)
                                        .toString()
                                        ;
                                System.err.println("Server received request for file: " + filename);
                                if (this.responseBuffers.containsKey(filename))
                                    key.attach(this.responseBuffers.get(filename).duplicate());
                                else
                                    key.attach(this.responseBuffers.get("404").duplicate());

                                key.interestOps(SelectionKey.OP_WRITE);
                            }  else if(key.isWritable()) {
                                SocketChannel channel = (SocketChannel)key.channel();
                                ByteBuffer buffer = (ByteBuffer)key.attachment();
                                if (buffer.hasRemaining()) {
                                    System.err.println("Writing to client...");
                                    channel.write(buffer);
                                } else {
                                    // Per HTTP, if we are done with response, we close connection
                                    System.err.println("Finished working with the client.");
                                    channel.close();
                                    clients--;
                                }
                            }
                        } catch(IOException ex) {
                            key.cancel();
                            clients--;
                            try {
                                key.channel().close();
                            } catch (IOException cex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillLocalCache(Path publicHtmlDir) throws IOException {
        this.responseBuffers = new HashMap<>();

        for (Path p : Files.newDirectoryStream(publicHtmlDir)) {
            if (Files.isRegularFile(p)) {
                FileInfo fi = FileInfo.get(p, StandardCharsets.UTF_8);
                ByteBuffer responseBuffer = this.createResponseBuffer(fi);
                this.responseBuffers.put(p.getFileName().toString(), responseBuffer);
            }
        }
        ByteBuffer nfBuffer = this.createNotFoundBuffer();
        this.responseBuffers.put("404", nfBuffer);
    }

    private ByteBuffer createResponseBuffer(FileInfo fi) {
        ByteBuffer data = fi.getData();
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: SimpleHTTP v1.0\r\n"
                + "Content-length: " + data.limit() + "\r\n"
                + "Content-type: " + fi.getMIMEType() + "\r\n\r\n";
        byte[] headerData = header.getBytes(fi.getEncoding());
        ByteBuffer buf = ByteBuffer.allocate(headerData.length + data.limit());
        buf.put(headerData);
        buf.put(data);
        buf.flip();
        return buf;
    }

    private ByteBuffer createNotFoundBuffer() {
        String nfHeader = "HTTP/1.0 404 Not found\r\n"
                + "Server: SimpleHTTP v1.0\r\n\r\n";
        byte[] nfHeaderData = nfHeader.getBytes(StandardCharsets.UTF_8);
        ByteBuffer nfBuffer = ByteBuffer.allocate(nfHeaderData.length);
        nfBuffer.put(nfHeaderData);
        nfBuffer.flip();
        return nfBuffer;
    }
}
