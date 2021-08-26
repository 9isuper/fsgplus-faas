package com.fsgplus.serverless.faas.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author admin
 */
@RestController
@RequestMapping("fsgplus/server")
public class ServiceInfoController {

    @RequestMapping(value = "dateTime", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
