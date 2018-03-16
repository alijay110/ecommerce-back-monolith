package pl.cba.gibcode.alabackend.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.card.model.CardType;

import java.util.Optional;

@Repository
public interface CardTypeRepository  extends JpaRepository<CardType, Long>, QueryDslPredicateExecutor<CardType> {

    Optional<CardType> findOneById(Long id);
}
