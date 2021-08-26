package com.fsgplus.serverless.faas.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author admin
 */
@RestController
@RequestMapping("fsgplus/health")
public class HealthController {

    @RequestMapping(value = "ping", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String ping() {
        return "pong";
    }

}
