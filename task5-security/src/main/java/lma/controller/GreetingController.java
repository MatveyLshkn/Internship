package lma.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/greeting")
public class GreetingController {

    @GetMapping("/user")
    public ResponseEntity<String> greetingUser() {
        return ResponseEntity.ok("Hello User!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> greetingAdmin() {
        return ResponseEntity.ok("Hello Admin!");
    }

}
