package pl.cba.gibcode.alabackend.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.transaction.model.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long>, QueryDslPredicateExecutor<Transaction> {
	Optional<Transaction> findById(Long id);
}
