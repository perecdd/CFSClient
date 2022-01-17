package io.swagger.api;

import io.swagger.model.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-25T14:15:30.636Z[GMT]")
@Validated
public interface RatingApi {
    @Operation(summary = "Gets the company's rating.", description = "Gets the rating of the company specified in the parameters.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rating.class))),

            @ApiResponse(responseCode = "400", description = "Bad request") })
    @RequestMapping(value = "/rating",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Rating> getRating(@Parameter(in = ParameterIn.HEADER, description = "Company ID" ,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid);

    @Operation(summary = "Sends a rating", description = "Sends a user assessment of the company's work.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request") })
    @RequestMapping(value = "/rating",
            method = RequestMethod.POST)
    ResponseEntity<Void> postRating(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "Company ID." ,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid, @Parameter(in = ParameterIn.HEADER, description = "Assessment of the quality of work." ,schema=@Schema()) @RequestHeader(value="rating", required=true) Integer rating);
}
