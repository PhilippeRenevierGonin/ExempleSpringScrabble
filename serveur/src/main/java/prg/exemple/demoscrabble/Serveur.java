package prg.exemple.demoscrabble;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Serveur {
    int value = 0;

    @GetMapping("/get/")
    public int getValue() {
        return value;
    }
}