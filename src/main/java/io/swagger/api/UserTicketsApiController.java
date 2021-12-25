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

    public ResponseEntity<List<InlineResponse200>> getUsersUserid(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=true,schema=@Schema()) @CookieValue(value="email", required=true) String email,@Parameter(in = ParameterIn.COOKIE, description = "password" ,required=true,schema=@Schema()) @CookieValue(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<InlineResponse200>>(objectMapper.readValue("[ {\n  \"id\" : 0,\n  \"products\" : [ {\n    \"companyid\" : 0,\n    \"productid\" : 6,\n    \"price\" : 1,\n    \"name\" : \"name\",\n    \"count\" : 5,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  }, {\n    \"companyid\" : 0,\n    \"productid\" : 6,\n    \"price\" : 1,\n    \"name\" : \"name\",\n    \"count\" : 5,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  } ],\n  \"status\" : \"status\"\n}, {\n  \"id\" : 0,\n  \"products\" : [ {\n    \"companyid\" : 0,\n    \"productid\" : 6,\n    \"price\" : 1,\n    \"name\" : \"name\",\n    \"count\" : 5,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  }, {\n    \"companyid\" : 0,\n    \"productid\" : 6,\n    \"price\" : 1,\n    \"name\" : \"name\",\n    \"count\" : 5,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  } ],\n  \"status\" : \"status\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<InlineResponse200>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<InlineResponse200>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postUserTickets(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=true,schema=@Schema()) @CookieValue(value="email", required=true) String email,@Parameter(in = ParameterIn.COOKIE, description = "password" ,required=true,schema=@Schema()) @CookieValue(value="password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "ticket" ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) String ticket) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
