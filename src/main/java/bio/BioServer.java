package main.java.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by haorui on 2019/5/22.
 * 因为米粉，所以小米
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        final int PORT = 6666;
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = null;
        System.out.println("BioServer start ..............." + PORT);
        while (true) {
            socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            System.out.println("get data " + in.readLine());
            out.write("finish\r\n");
            out.write("haha");
            out.write("\n");
            out.flush();
            socket.close();
        }
    }
}
