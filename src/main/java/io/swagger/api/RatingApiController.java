package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Rating;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T14:15:30.636Z[GMT]")
@RestController
public class RatingApiController implements RatingApi {
    private static final Logger log = LoggerFactory.getLogger(ProductsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RatingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Rating> getRating(@Parameter(in = ParameterIn.HEADER, description = "Company ID" ,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid){
        String accept = request.getHeader("Accept");
        JSONObject jsonObject = ShopOwnerSide.getRating(companyid);
        if(jsonObject != null){
            try {
                return new ResponseEntity<Rating>(objectMapper.readValue(jsonObject.toString(), Rating.class), HttpStatus.OK);
            }
            catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> postRating(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "Company ID." ,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid, @Parameter(in = ParameterIn.HEADER, description = "Assessment of the quality of work." ,schema=@Schema()) @RequestHeader(value="rating", required=true) Integer rating){
        String accept = request.getHeader("Accept");
        if(ShopOwnerSide.postRating(email, password, companyid, rating)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
