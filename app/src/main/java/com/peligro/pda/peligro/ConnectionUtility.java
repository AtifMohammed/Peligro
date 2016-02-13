package com.peligro.pda.peligro;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import android.os.StrictMode;

public class ConnectionUtility {

    static String url="http://192.168.0.8:9090/PeligroServer/";
    static String response="failure";
    public static String getResponse(String requrl){

        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
            URL url=new URL(requrl);
            HttpURLConnection http=(HttpURLConnection) url.openConnection();
            InputStream io=http.getInputStream();
            int i=0;
            StringBuilder res=new StringBuilder();
            while((i=io.read())!=-1){

                res.append((char)i);
            }
            response=res.toString();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return response;
    }

}
