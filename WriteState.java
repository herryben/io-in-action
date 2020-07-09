package nio.mt;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class WriteState implements HandlerState {

    private String str;

    public WriteState(){

    }

    public WriteState(String str) {
        this.str = str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public void changeState(Handler handler) {
        handler.setState(new WriteState());
    }

    @Override
    public void handle(Handler handler, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        System.out.println("response " + str + " length " + str.getBytes().length);
        while (byteBuffer.hasRemaining()) {
            sc.write(byteBuffer);
        }

        handler.setState(new ReadState());
        sk.interestOps(SelectionKey.OP_READ);
        sk.selector().wakeup();
    }
}
