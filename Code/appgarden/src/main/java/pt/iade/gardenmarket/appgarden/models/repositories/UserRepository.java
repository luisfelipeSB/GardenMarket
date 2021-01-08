package pt.iade.gardenmarket.appgarden.models.repositories;

import org.springframework.data.repository.CrudRepository;

import pt.iade.gardenmarket.appgarden.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    
}
