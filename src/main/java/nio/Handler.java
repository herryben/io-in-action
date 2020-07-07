package nio.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by haorui on 2020/7/7.
 * 因为米粉，所以小米
 */
public class Handler implements Runnable{
    private final SelectionKey sk;
    private final SocketChannel sc;
    private int state;
    private String msg;

    public Handler(SelectionKey sk, SocketChannel sc){
        this.sk = sk;
        this.sc = sc;
        this.state = 0;
    }

    @Override
    public void run() {
        try {
            if (state == 0) {
                msg = read();
            } else {
                send(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeChannel();
        }
    }

    private synchronized String read() throws IOException {
        byte[] arr = new byte[1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(arr);
        int num = sc.read(byteBuffer);
        if (num == -1) {
            System.out.println("close connection " + sc);
            closeChannel();
            return null;
        }
        String msg = new String(arr);
        process(msg);
        System.out.println("get msg " + msg + " from" + sc);
        state = 1;
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.selector().wakeup();
        return msg;
    }

    private void send(String msg) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        while (byteBuffer.hasRemaining()) {
            sc.write(byteBuffer);
        }

        state = 0;
        sk.interestOps(SelectionKey.OP_READ);
        sk.selector().wakeup();
    }

    private void closeChannel(){
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(String msg){

    }
}
