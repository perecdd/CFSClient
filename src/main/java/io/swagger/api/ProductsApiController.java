package io.swagger.api;

import io.swagger.model.Product;
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

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T14:15:30.636Z[GMT]")
@RestController
public class ProductsApiController implements ProductsApi {

    private static final Logger log = LoggerFactory.getLogger(ProductsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ProductsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Product>> getProducts(@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="name", required=false) String name,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) Integer minPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) Integer maxPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="companyID", required=false) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="count", required=false) Integer count,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="productID", required=false) Integer productID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            JSONArray jsonArray = CFS.GetProducts(name, minPrice, maxPrice, companyID, count, productID);
            if (jsonArray != null) {
                try {
                    return new ResponseEntity<List<Product>>(objectMapper.readValue(jsonArray.toString(), List.class), HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<List<Product>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else{
                return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<List<Product>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postProducts(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=true,schema=@Schema()) @CookieValue(value="email", required=true) String email,@Parameter(in = ParameterIn.COOKIE, description = "password" ,required=true,schema=@Schema()) @CookieValue(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> putProducts(@Parameter(in = ParameterIn.HEADER, description = "email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody List<Product> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}
