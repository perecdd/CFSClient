package io.swagger.api;

import io.swagger.model.InlineResponse200;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T13:53:42.910Z[GMT]")
@RestController
public class UserTicketsApiController implements UserTicketsApi {

    private static final Logger log = LoggerFactory.getLogger(UserTicketsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserTicketsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<InlineResponse200>> getUserTickets(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=false,schema=@Schema()) @CookieValue(value="email", required=false) String email,@Parameter(in = ParameterIn.COOKIE, description = "password" ,required=false,schema=@Schema()) @CookieValue(value="password", required=false) String password) {
        String accept = request.getHeader("Accept");
        if(email == null || password == null) return new ResponseEntity<List<InlineResponse200>>(HttpStatus.UNAUTHORIZED);

        if (accept != null && accept.contains("application/json")) {
            try {
                JSONArray result = ShopOwnerSide.getTickets(email, password);

                if(result != null) {
                    return new ResponseEntity<List<InlineResponse200>>(objectMapper.readValue(result.toString(), List.class), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<List<InlineResponse200>>(HttpStatus.BAD_REQUEST);
                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<InlineResponse200>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<InlineResponse200>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postUserTickets(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=false,schema=@Schema()) @CookieValue(value="email", required=false) String email,@Parameter(in = ParameterIn.COOKIE, description = "password" ,required=false,schema=@Schema()) @CookieValue(value="password", required=false) String password,@Parameter(in = ParameterIn.HEADER, description = "ticket" ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) Integer ticket) {
        String accept = request.getHeader("Accept");
        if(email == null || password == null || ticket == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        if(ShopOwnerSide.cancelTicket(email, password, ticket)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
