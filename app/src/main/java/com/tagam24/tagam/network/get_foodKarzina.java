package com.tagam24.tagam.network;


import android.util.Log;

import com.tagam24.tagam.Constants.Constants;
import com.tagam24.tagam.karzina.Karzina;
import com.tagam24.tagam.models.Model_Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by User on 27.06.2018.
 */

public class get_foodKarzina {
   public  static String k;
    public  static int con=0;
    static String l;
    public static   Thread get_data=new Thread();
    public static void  get_Data(){
          Log.d("gelenok","ok");
        get_data=new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL url=new URL("http://"+Api.url+"tagam24/get_food1.php");
                    Log.d("url",""+url);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(Constants.categoryFood, "UTF-8")+
                            "&"+URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode(""+0, "UTF-8")
                    +"&"+URLEncoder.encode("cafeID", "UTF-8") + "=" + URLEncoder.encode(""+ Constants.cafesel.getId(), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String row = "";
                    Log.d("post",post_data);
                    while ((row = br.readLine()) != null) {
                        sb.append(row + "\n");}
                    br.close();
                    in.close();
                    conn.disconnect();
                    if(sb.toString().length()<30)Constants.iter=false;
                    if (!sb.toString().equals(""))GetJson(sb.toString());//idlar gelyar tazelemeli

                    Thread.currentThread().interrupt();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }) ;
       if(con==0) get_data.start();
    }

    public static void GetJson(final String s){
        Thread json=new Thread(new Runnable() {
            @Override
            public void run() {
                try{//bashda data basadan hemme informasiyasyny alyas
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        if(!Constants.ids.contains(data.optString("id")))Constants.cart_food.add(new Model_Food(data.optString("id"),
                                data.optString("image"),data.optString("image1"),
                                data.optString("name"),data.optString("price"),
                                data.optString("rating"),data.optString("rate_number"),
                                data.optString("cafeID"),data.optString("vip")));
                    }
                    Log.d("Alimerdan","size:"+Constants.cart_food.size()+"geldi:"+s);
                   Karzina.s3.sendEmptyMessage(1);
                    Log.d("conn",""+con);
                    Thread.currentThread().interrupt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        });
        json.start();
    }

}
