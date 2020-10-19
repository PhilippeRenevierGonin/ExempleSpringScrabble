package prg.exemple.demoscrabble.serveur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Serveur {
    int value = 24;

    @GetMapping("/get/")
    public int getValue() {
        System.out.println("Serveur > requête sur value : "+value);
        return value;
    }
}