package com.example.david.json;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by David on 2015/1/15.
 */
public class ParseJson  {

    private boolean last;
    private String jsonString;
    private String resultString;

    public ParseJson (String string){
        this.jsonString=string;
        init();
    }

    private void init(){
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            last=jsonObject.getBoolean("ls");
            JSONArray jsonArray=jsonObject.getJSONArray("ws");
            StringBuilder stringBuilder=new StringBuilder();

            for (int i=0;i<jsonArray.length();i++){
                JSONObject childObject=jsonArray.getJSONObject(i);
                JSONArray childArray = childObject.getJSONArray("cw");
                for (int j=0;j<childArray.length();j++){
                    stringBuilder.append(childArray.getJSONObject(j).getString("w"));
                }
            }

            resultString=stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isLast() {
        return last;
    }

    public String getResultString() {
        return resultString;
    }
}
