package nio.ma;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class Acceptor implements Runnable{

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel ssc){
        this.selector = selector;
        this.ssc = ssc;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = ssc.accept();
            System.out.println("new connection " + sc);
            if (sc != null) {
                sc.configureBlocking(false);
                selector.wakeup();
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                selector.wakeup();
                sk.attach(new Handler(sk, sc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
