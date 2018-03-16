package pl.cba.gibcode.alabackend.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.card.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, QueryDslPredicateExecutor<Card> {

}
