package io.github.bluething.playground.spring.security.cryptoportofolio.portofolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {
    @GetMapping("/portfolio")
    public String portfolio() {
        return "portfolio";
    }
}
