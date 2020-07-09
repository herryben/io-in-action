package nio.mt;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class ReadState implements HandlerState {
    private SelectionKey sk;

    @Override
    public void changeState(Handler handler) {
        handler.setState(new WorkState());
    }

    @Override
    public void handle(Handler handler, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) throws IOException {
        this.sk = sk;

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int num = sc.read(byteBuffer);
        if (num == -1) {
            System.out.println("connection closed " + sc);
            handler.closeChannel();
            return;
        }
        byteBuffer.flip();
        byte[] arr = new byte[byteBuffer.remaining()];
        byteBuffer.get(arr);
        String str = new String(arr);
        handler.setState(new WorkState());
        pool.execute(new WorkThread(handler, str));
        System.out.println("get data" + str + " from " + sc);
    }

    private synchronized void process(Handler handler, String str){
//        try {
//            TimeUnit.MILLISECONDS.sleep(40);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        handler.setState(new WriteState(str));
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.selector().wakeup();
    }

    class WorkThread implements Runnable {

        private Handler handler;
        private String str;

        public WorkThread(Handler handler, String str) {
            this.handler = handler;
            this.str = str;
        }

        @Override
        public void run() {
            process(handler, str);
        }
    }
}
