package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    public static final int PORT_NUMBER = 6013;

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        client.start(args);
    }

    private void start(String[] args) throws IOException {
        String server = (args.length == 0) ? "localhost" : args[0];
        Socket socket = new Socket(server, PORT_NUMBER);
        InputStream socketInputStream = socket.getInputStream();
        OutputStream socketOutputStream = socket.getOutputStream();
        InputStream userInput = System.in;

        int data;
        while ((data = userInput.read()) != -1) {
            socketOutputStream.write(data);
            socketOutputStream.flush();

            int response = socketInputStream.read();
            System.out.write(response);
            System.out.flush();
        }

        socket.close();
    }
}