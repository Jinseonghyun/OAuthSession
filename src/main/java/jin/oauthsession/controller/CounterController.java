package jin.oauthsession.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    @Autowired
    private ServletWebServerApplicationContext webServerContext;

    @GetMapping
    public String count() {
        int port = webServerContext.getWebServer().getPort();
        return  "현재 포트는 : " + port;
    }
}
