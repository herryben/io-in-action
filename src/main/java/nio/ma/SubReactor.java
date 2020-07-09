package main.java.nio.ma;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class SubReactor implements Runnable {

    private final Selector selector;

    public SubReactor() throws IOException {
        this.selector = Selector.open();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                if (selector.select(5) == 0) {
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
