package pl.cba.gibcode.alabackend.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.model.Category;
import pl.cba.gibcode.alabackend.card.model.CardType;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QueryDslPredicateExecutor<Category> {
    Optional<Category> findOneById(Long categoryId);
}
