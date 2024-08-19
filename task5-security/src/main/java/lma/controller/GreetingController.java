package lma.controller;


import lma.constants.CommonConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lma.constants.CommonConstants.ADMIN_GREETING_ENDPOINT;
import static lma.constants.CommonConstants.BASE_GREETING_ENDPOINT;
import static lma.constants.CommonConstants.USER_GREETING_ENDPOINT;

@RestController
public class GreetingController {

    @GetMapping(BASE_GREETING_ENDPOINT)
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping(USER_GREETING_ENDPOINT)
    public ResponseEntity<String> greetingUser() {
        return ResponseEntity.ok("Hello User!");
    }

    @GetMapping(ADMIN_GREETING_ENDPOINT)
    public ResponseEntity<String> greetingAdmin() {
        return ResponseEntity.ok("Hello Admin!");
    }

}
