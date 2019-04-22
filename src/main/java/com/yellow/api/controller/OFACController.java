package com.yellow.api.controller;

import com.yellow.api.domain.SearchResult;
import com.yellow.api.service.SDNOfacService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/v1/ofac/sanctions/individuals")
@EnableAutoConfiguration
public class OFACController {
    @Autowired
    private SDNOfacService service;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @ApiOperation(value = "Get ofac individuals information", response = SearchResult[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Request!"),
            @ApiResponse(code = 404, message = "No sanctions found"),
            @ApiResponse(code = 412, message = "Some parameter is messing."),
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> findOFACIndividuals(
            @RequestHeader(value = "name", defaultValue = "", required = true) String name,
            @RequestHeader(value = "minScore", defaultValue = "", required = false) int minScore) {
        SearchResult[] result;
        long responseTime = System.currentTimeMillis();
        int httpStatus = 200;
        try {
            result = service.matcheIndividualName(name,minScore);
        } catch (Exception e) {
            logger.error(e.getMessage());
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE.value();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }finally {
            logger.info("RP:"+responseTime);
        }
        return ResponseEntity.ok(result);
    }
}
