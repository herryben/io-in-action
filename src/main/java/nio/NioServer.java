package main.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    private final static int PORT = 6666;
    private final static int BUFFER_SIZE = 1024;
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(PORT));
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NioServer start ..............." + PORT);
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            SelectionKey key = null;
            while (it.hasNext()) {
                try {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key, selector);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handleInput(SelectionKey key, Selector selector) throws IOException, InterruptedException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }

            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                byteBuffer.clear();
                int readBytes = sc.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    System.out.println("get data " + new String(bytes));
                    doWrite(sc, "finish\n");
                } else if (readBytes < 0){
                    key.channel();
                    sc.close();
                }
            }
        }
    }

    private static void doWrite(SocketChannel channel, String response) throws IOException {
        System.out.println("response " + response.length());
        byteBuffer.clear();
        byte[] bytes = response.getBytes();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer);
    }
}
