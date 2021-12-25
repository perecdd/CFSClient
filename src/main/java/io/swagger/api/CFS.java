package io.swagger.api;

import io.swagger.model.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CFS {
    public final static Integer port = 3939;
    public final static String ip = "http://localhost"; // TODO http://cfs
    public static HttpURLConnection con;

    static JSONArray GetProductsByUser(String email, String password){
        try {
            URL url = new URL (ip + ":" + port + "/user");

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

            return jsonArray;
        }
        catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    static JSONArray GetProducts(String name, Integer minPrice, Integer maxPrice, Integer companyID, Integer count, Integer productID){
        try {
            URL url = new URL (ip + ":" + port + "/product");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            if(name != null) con.setRequestProperty("name", name);
            if(minPrice != null) con.setRequestProperty("minPrice", String.valueOf(minPrice));
            if(maxPrice != null) con.setRequestProperty("maxPrice", String.valueOf(maxPrice));
            if(companyID != null) con.setRequestProperty("companyID", String.valueOf(companyID));
            if(count != null) con.setRequestProperty("count", String.valueOf(count));
            if(productID != null) con.setRequestProperty("productID", String.valueOf(productID));
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

            return jsonArray;
        }
        catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    static boolean loginUser(String email, String password){
        try {
            URL url = new URL (ip + ":" + port + "/user");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("GET");
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

    static boolean UpdateUserBasket(String email, String password, JSONArray basket){
        try {
            URL url = new URL(ip + ":" + port + "/user");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("PATCH");
            con.setDoOutput(true);
            con.setDoInput(true);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
            out.println(basket.toString());

            int responseCode = con.getResponseCode();
            out.close();
            con.disconnect();

            return responseCode == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    static boolean createUser(JSONObject UserJSON){
        try {
            int responseCode1 = -1;
            int responseCode2 = -1;

            JSONArray basket = (JSONArray) UserJSON.get("basket");
            {
                URL url = new URL(ip + ":" + port + "/user");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", "ShopOwnerApplication");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
                UserJSON.remove("basket");
                out.println(UserJSON.toString());

                responseCode1 = con.getResponseCode();
                out.close();
                con.disconnect();
            }

            return responseCode1 == 200 && UpdateUserBasket((String) UserJSON.get("email"), (String) UserJSON.get("password"),basket);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    static boolean AddOrRemoveProductInUserBucket(String email, String password, JSONObject product){
        try {
            JSONArray jsonArray = GetProductsByUser(email, password);

            boolean finded = false;
            JSONArray result = new JSONArray();

            for(Object jsonProduct : jsonArray.toArray()){
                if(((JSONObject) jsonProduct).equals(product)){
                    finded = true;
                }
                else{
                    result.add((JSONObject) jsonProduct);
                }
            }

            URL url = new URL(ip + ":" + port + "/user");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            con.setDoInput(true);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
            out.println(result.toString());

            int responseCode = con.getResponseCode();
            out.close();
            con.disconnect();

            return responseCode == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    static boolean UpdateUserInformation(String email, String password, JSONObject UserWithoutBasketJSON){
        try {
            URL url = new URL(ip + ":" + port + "/user");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            con.setDoInput(true);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
            out.println(UserWithoutBasketJSON.toString());

            int responseCode = con.getResponseCode();
            out.close();
            con.disconnect();

            return responseCode == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
