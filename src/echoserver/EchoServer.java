package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int PORT_NUMBER = 6013;

    public static void main(String[] args) throws IOException, InterruptedException {
        EchoServer server = new EchoServer();
        server.start();
    }

    private void start() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Got request!");

            // Create a new thread to handle the client connection
            Thread clientHandler = new Thread(new ClientHandler(socket));
            clientHandler.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                int data;
                while ((data = input.read()) != -1) {
                    System.out.println("Received byte:" + data);
                    output.write(data);
                }

                socket.close();
            } catch (IOException e) {
                System.out.println("We caught an unexpected exception");
                System.err.println(e);
            }
        }
    }
}