package game.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.ItemList;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class TestNet {

    public static void main(String[] args) throws InterruptedException, ParseException {
//        Client tst=new Client("127.0.0.1","asdas");
        JSONObject a=new JSONObject();
        a.put("json message","connect");
        a.put("name","asd");
        System.out.println(a.get("s"));
        JSONParser d=new JSONParser();
       // JSONObject k=(JSONObject)d.parse(new String(a.toJSONString().getBytes()));
        //System.out.println(k.toJSONString());
       // tst.send(a.toJSONString());

        JSONArray g = new JSONArray();
        JSONObject j=new JSONObject();
        j.put("name","1");
        j.put("pos","12 8");
        g.add(j);
        JSONObject q=new JSONObject();
        q.put("name","1");
        q.put("pos","12 8");
        q.put("s",1);
        g.add(q);
        a.put("player",g);
        System.out.println(a.toJSONString());

    }

}
