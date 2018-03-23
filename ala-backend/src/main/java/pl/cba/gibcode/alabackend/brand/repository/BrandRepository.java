package pl.cba.gibcode.alabackend.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.brand.model.Brand;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, QueryDslPredicateExecutor<Brand> {
    Optional<Brand> findByName(String name);
}
