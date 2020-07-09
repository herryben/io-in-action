package main.java.nio.mt;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class WorkState implements HandlerState {
    @Override
    public void changeState(Handler handler) {

    }

    @Override
    public void handle(Handler handler, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) {

    }
}
