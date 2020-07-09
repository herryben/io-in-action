package nio.mt;

import java.io.IOException;

/**
 * Created by haorui on 2020/7/8.
 * 因为米粉，所以小米
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(6666);
        reactor.run();
    }
}
