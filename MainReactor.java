package nio.ma;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class MainReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;
    private final SubReactor subReactor;

    public MainReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
        subReactor = new SubReactor();
        sk.attach(new Acceptor(subReactor.getSelector(), ssc));
        System.out.println("listen " + port);
    }

    @Override
    public void run() {
        new Thread(subReactor).start();
        while (!Thread.interrupted()) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                dispatch(it.next());
                it.remove();
            }
        }
    }

    public void dispatch(SelectionKey key){
        Runnable runnable = (Runnable) key.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }
}
