package main.java.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by haorui on 2019/5/23.
 * 因为米粉，所以小米
 */
public class BioPooledServer {
    public static void main(String[] args) throws Exception {
        final int PORT = 6666;
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = null;
//        ExecutorService service = Executors.newFixedThreadPool(50);
        ExecutorService service = Executors.newCachedThreadPool();
        new Thread(() -> {
            while (true) {
                System.out.println("#########################################");
                System.out.println("thread count " + ((ThreadPoolExecutor)service).getActiveCount());
                System.out.println("#########################################");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("BioPooledServer start ..............." + PORT);
        while (true) {
            socket = serverSocket.accept();
            Socket finalSocket = socket;
            service.execute(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(finalSocket.getOutputStream())), true);
                    System.out.println("get data " + in.readLine());
                    out.write("finish\r\n");
                    out.write("haha");
                    out.write("\n");
                    out.flush();
                } catch (Exception ignore) {

                } finally {
                    try {
                        finalSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
