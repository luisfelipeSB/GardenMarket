package pt.iade.gardenmarket.appgarden.models.repositories;

import org.springframework.data.repository.CrudRepository;

import pt.iade.gardenmarket.appgarden.models.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    
}
