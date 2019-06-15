package game.network;

public class TestNet {

    public static void main(String[] args) throws InterruptedException {
        Client tst=new Client();
        System.out.println(tst.openConnection("127.0.0.1"));
        Thread.sleep(10);
        tst.send("tests");
        Thread.sleep(10);
        System.out.println(tst.receive());
        Thread.sleep(10);
        tst.send("test1");
        Thread.sleep(10);
        tst.send("test2");
    }

}
