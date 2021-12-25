/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.InlineResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T13:53:42.910Z[GMT]")
@Validated
public interface UserTicketsApi {

    @Operation(summary = "Your GET endpoint", description = "Get user orders", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse200.class)))) })
    @RequestMapping(value = "/user-tickets",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<InlineResponse200>> getUsersUserid(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=true,schema=@Schema()) @CookieValue(value="email", required=true) String email, @Parameter(in = ParameterIn.COOKIE, description = "password" ,required=true,schema=@Schema()) @CookieValue(value="password", required=true) String password);


    @Operation(summary = "", description = "cancel ticket", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK") })
    @RequestMapping(value = "/user-tickets",
        method = RequestMethod.POST)
    ResponseEntity<Void> postUserTickets(@Parameter(in = ParameterIn.COOKIE, description = "email" ,required=true,schema=@Schema()) @CookieValue(value="email", required=true) String email, @Parameter(in = ParameterIn.COOKIE, description = "password" ,required=true,schema=@Schema()) @CookieValue(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "ticket" ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) String ticket);

}
