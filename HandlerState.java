package nio.ma;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public interface HandlerState {

    void changeState(Handler handler);

    void handle(Handler handler, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) throws IOException;
}
