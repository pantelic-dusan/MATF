package timeService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeServiceClient {

    public static void main(String[] args) {
        String hostname = "time.nist.gov";

        TimeZone gmt = TimeZone.getTimeZone("GMT");
        Calendar epoch1900 = Calendar.getInstance(gmt);
        epoch1900.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        long epoch1900ms = epoch1900.getTimeInMillis();

        Calendar epoch1970 = Calendar.getInstance(gmt);
        epoch1970.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
        long epoch1970ms = epoch1970.getTimeInMillis();

        long epochDifferenceMS = epoch1970ms - epoch1900ms;
        long epochDifferenceS = epochDifferenceMS/1000;

        try (Socket sock = new Socket(hostname, 37)) {

            InputStream raw = new BufferedInputStream(sock.getInputStream());
            long secondsSince1900 = 0;
            for (int i = 0; i<4; i++) {
                secondsSince1900 = (secondsSince1900<<8) | raw.read();
            }
            long secondsSince1970 = secondsSince1900 - epochDifferenceS;
            long millisecondsSince1970 = secondsSince1970*1000;

            Date now = new Date(millisecondsSince1970);

            System.out.println("It is " + now + " at " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
