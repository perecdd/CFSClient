package io.swagger.api;

import io.swagger.model.Product;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
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
            return null;
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
                System.out.println(UserJSON.get("password"));
                out.println(UserJSON.toString());

                responseCode1 = con.getResponseCode();
                out.close();
                con.disconnect();
            }

            return responseCode1 == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    static boolean AddOrRemoveProductInUserBucket(String email, String password, Product product){
        try {
            JSONArray jsonArray = GetProductsByUser(email, password);

            boolean finded = false;
            JSONArray result = new JSONArray();

            if(jsonArray != null) {
                for (Object ProductObject : jsonArray.toArray()) {
                    JSONObject jsonProduct = (JSONObject) ProductObject;
                    Product product1 = new Product();
                    product1.setCompanyid(Integer.parseInt(String.valueOf(jsonProduct.get("companyid"))));
                    product1.setProductid(Integer.parseInt(String.valueOf(jsonProduct.get("productid"))));
                    product1.setPrice(Integer.parseInt(String.valueOf(jsonProduct.get("price"))));
                    product1.setName(String.valueOf(jsonProduct.get("name")));
                    product1.setCount(Integer.parseInt(String.valueOf(jsonProduct.get("count"))));
                    product1.setDescription(String.valueOf(jsonProduct.get("description")));
                    product1.setPhoto(String.valueOf(jsonProduct.get("Photo")));

                    if (product1.equals(product)) {
                        finded = true;
                    } else {
                        result.add(jsonProduct);
                    }
                }
            }
            if(!finded){
                JSONObject JS = new JSONObject();
                JS.put("Photo", product.getPhoto());
                JS.put("companyid", product.getCompanyid());
                JS.put("count", product.getCount());
                JS.put("description", product.getDescription());
                JS.put("name", product.getName());
                JS.put("price", product.getPrice());
                JS.put("productid", product.getProductid());
                result.add(JS);
            }

            URL url = new URL(ip + ":" + port + "/user/patch");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
            out.println(result.toString());
            System.out.println(result.toString());

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

    static boolean OrderProducts(String email, String password){
        try {
            URL url = new URL(ip + ":" + port + "/product");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
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
