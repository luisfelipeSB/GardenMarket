package pt.iade.gardenmarket.appgarden.models.repositories;

import org.springframework.data.repository.CrudRepository;

import pt.iade.gardenmarket.appgarden.models.AdCategory;

public interface AdCategoryRepository extends CrudRepository<AdCategory, Integer> {
    
}
