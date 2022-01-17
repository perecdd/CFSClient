package io.swagger.api;

import io.swagger.models.auth.In;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShopOwnerSide {
    public final static Integer port = 15676;
    public final static String ip = "http://shopownerside";
    public static HttpURLConnection con;

    public static JSONArray getTickets(String email, String password){
        try {
            URL url = new URL (ip + ":" + port + "/ticketUser");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(sb.toString());

            int responseCode = con.getResponseCode();
            con.disconnect();

            if(responseCode == 200) {
                return jsonArray;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getRating(Integer companyid){
        try {
            URL url = new URL (ip + ":" + port + "/rating");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("companyid", String.valueOf(companyid));
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());

            int responseCode = con.getResponseCode();
            con.disconnect();

            if(responseCode == 200) {
                return jsonObject;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            return null;
        }
    }

    public static boolean postRating(String email, String password, Integer companyid, Integer rating){
        try {
            URL url = new URL (ip + ":" + port + "/rate");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestProperty("companyid", String.valueOf(companyid));
            con.setRequestProperty("rating", String.valueOf(rating));
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            int responseCode = con.getResponseCode();
            con.disconnect();

            if(responseCode == 200) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean cancelTicket(String email, String password, Integer ticket){
        try {
            URL url = new URL(ip + ":" + port + "/ticketUser");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestProperty("ticket", String.valueOf(ticket));
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            int responseCode = con.getResponseCode();
            con.disconnect();

            return responseCode == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
