package daytimeService;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaytimeClient {

    private static final int PORT = 12345;
    private static final int BUF_SIZE = 1024;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            InetAddress host = InetAddress.getByName(HOSTNAME);

            DatagramPacket request = new DatagramPacket(new byte[1], 1, host, PORT);

            DatagramPacket response = new DatagramPacket(new byte[BUF_SIZE], BUF_SIZE);

            socket.send(request);
            socket.receive(response);

            String result = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
            System.out.println(result);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DaytimeServer {

        private static final int PORT = 12345;
        private static final int BUF_SIZE = 1024;
        private static final Logger audit = Logger.getLogger("requests");
        private static final Logger errors = Logger.getLogger("errors");

        public static void main(String[] args) {

            try (DatagramSocket socket = new DatagramSocket(PORT, InetAddress.getByName("localhost")) ){

                while (true) {

                    try {
                        DatagramPacket request = new DatagramPacket(new byte[BUF_SIZE], BUF_SIZE);
                        socket.receive(request);

                        String daytime = new Date().toString();
                        byte[] data = daytime.getBytes(StandardCharsets.UTF_8);

                        DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
                        socket.send(response);

                        audit.info(daytime + " " + request.getAddress());
                    } catch (IOException e) {
                        errors.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
