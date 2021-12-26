package io.swagger.api;

import io.swagger.model.Product;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T13:53:42.910Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Product>> getLogin(@Parameter(in = ParameterIn.HEADER, description = "email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, HttpServletResponse response) {
        String accept = request.getHeader("Accept");

        Cookie cookie = new Cookie("email", email);
        cookie.setMaxAge(7 * 24 * 6000);

        Cookie cookie1 = new Cookie("password", password);
        cookie1.setMaxAge(7 * 24 * 6000);

        response.addCookie(cookie);
        response.addCookie(cookie1);

        if (accept != null && accept.contains("application/json")) {
            try {
                JSONArray jsonArray = CFS.GetProductsByUser(email, password);
                if(jsonArray != null) {
                    return new ResponseEntity<List<Product>>(objectMapper.readValue(jsonArray.toString(), List.class), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Product>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> postLogin(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body) {
        String accept = request.getHeader("Accept");
        JSONObject user = new JSONObject();

        user.put("email", body.getEmail());
        user.put("id", body.getId());
        user.put("name", body.getName());
        user.put("phone", body.getPhone());
        user.put("email", body.getEmail());
        user.put("surname", body.getSurname());
        user.put("password", body.getPassword());

        JSONObject address = new JSONObject();
        address.put("city", body.getAddress().getCity());
        address.put("country", body.getAddress().getCountry());
        address.put("flat", body.getAddress().getFlat());
        address.put("house", body.getAddress().getHouse());
        address.put("street", body.getAddress().getStreet());

        user.put("address", address);

        JSONArray products = new JSONArray();
        for(Product product : body.getBasket()){
            JSONObject JSONproduct = new JSONObject();
            JSONproduct.put("name", product.getName());
            JSONproduct.put("Photo", product.getPhoto());
            JSONproduct.put("companyid", product.getCompanyid());
            JSONproduct.put("productid", product.getProductid());
            JSONproduct.put("price", product.getPrice());
            JSONproduct.put("count", product.getCount());
            JSONproduct.put("description", product.getDescription());
        }
        user.put("basket", products);

        System.out.println(body.getPassword());
        if(CFS.createUser(user)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
