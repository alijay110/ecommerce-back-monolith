package pl.cba.gibcode.alabackend.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.brand.model.Category;
import pl.cba.gibcode.alabackend.brand.model.PriceRange;

import java.util.Optional;

@Repository
public interface PriceRangeRepository extends JpaRepository<PriceRange, Long>, QueryDslPredicateExecutor<PriceRange> {
    Optional<PriceRange> findOneById(Long categoryId);
}
