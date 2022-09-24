package br.com.driver.finder.server.Util;

import org.json.JSONObject;

public class JsonParserSerializer {
    public static JSONObject parseString(String string){
        JSONObject json;
        try {
            json = new JSONObject(string);
            json.put("status", "200");
        }catch (Exception e){
            json = new JSONObject();
            json.put("status", "400");
        }
        return json;
    }

    public static String serializeJson(JSONObject json){
        return json.toString() + "\n";
    }

    public static JSONObject getJsonStatus400(){
        JSONObject json400 = new JSONObject();
        json400.put("status", "400");
        return json400;
    }
    public static JSONObject getJsonStatus500(){
        JSONObject json500 = new JSONObject();
        json500.put("status", "500");
        return json500;
    }

}
