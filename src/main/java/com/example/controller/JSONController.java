package com.example.controller;

import com.example.json.util.RestResponse;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chemistry")
public class JSONController {

    @GetMapping(value = "/test-json")
    public boolean getIsControllerWorks(){
        return true;
    }

    @PostMapping(value = "/test-json")
    public RestResponse saveNotes(@RequestBody String booleanJson) throws JsonParseException {

        return new RestResponse(HttpStatus.OK.value(), "Operation is great!");
    }
}
