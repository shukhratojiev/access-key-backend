package com.example.accesskeybackend.template.controller;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.*;

@RestController
@RequestMapping("/api/public/web/")
public class CheckIPv6Support {

    @GetMapping("/checkIpv6Support")
    public ResponseEntity<?> checkIPv6Support(@RequestParam String url){

        ObjectNode json = JsonNodeFactory.instance.objectNode();
        boolean supportsIPv6 = false;

        String host = null;
        try{
            host = new URL(url).getHost();
        } catch (MalformedURLException e) {
            System.out.println("Provided domain, not url");
            host = url;
        }

        try {
            InetAddress[] addresses = InetAddress.getAllByName(host);

            for(InetAddress adr : addresses) {

                if(adr instanceof Inet6Address) {
                    supportsIPv6 = true;
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Invalid domain");
            json.put("failure message", "Invalid domain");
        }

        json.put("success", supportsIPv6);

        return new ResponseEntity<ObjectNode>(json, HttpStatusCode.valueOf(200));
    }
}
