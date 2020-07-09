package nio.ma;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class Handler implements Runnable {

    private final SocketChannel sc;
    private final SelectionKey sk;
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private HandlerState state;

    public Handler(SelectionKey sk, SocketChannel sc){
        this.sk = sk;
        this.sc = sc;
        state = new ReadState();
    }

    @Override
    public void run() {
        try {
            state.handle(this, sk, sc, pool);
        } catch (IOException e) {
            e.printStackTrace();
            closeChannel();
        }
    }

    public void setState(HandlerState state){
        this.state = state;
    }

    public void closeChannel(){
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
