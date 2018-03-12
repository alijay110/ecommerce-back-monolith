package pl.cba.gibcode.alabackend.cardlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cba.gibcode.alabackend.cardlist.model.Card;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api")
public class CardListController {

    @GetMapping("cards")
    public ResponseEntity<List<Card>> getCardList(){

        return ResponseEntity.ok(Stream.generate(Card::buildRandomCard)
                .limit(1000)
                .collect(Collectors.toList()));
    }
}
