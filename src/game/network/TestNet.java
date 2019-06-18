package game.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestNet {

    public static void main(String[] args) throws InterruptedException, ParseException {
//        Client tst=new Client("127.0.0.1","asdas");
        JSONObject a=new JSONObject();
        a.put("json message","connect");
        a.put("name","asd");
        System.out.println(a.get("s"));
        JSONParser d=new JSONParser();
        JSONObject k=(JSONObject)d.parse(new String(a.toJSONString().getBytes()));
        System.out.println(k.toJSONString());
        Thread.sleep(10);
       // tst.send(a.toJSONString());
        Thread.sleep(10);
    }

}
