import java.io.*;
import java.net.Socket;

/**
 * Created by Ali_Iman on 2/10/17.
 */

public class ServerClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("188.166.78.119", 8081);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        out.println("AV THR MHD 05Feb");

        while (true) {
            System.out.println(in.readLine());
            if(!in.ready())
                break;
        }

        socket.close();
    }
}
