package pt.iade.gardenmarket.appgarden.models.repositories;

import org.springframework.data.repository.CrudRepository;

import pt.iade.gardenmarket.appgarden.models.Advertisement;

public interface AdRepository extends CrudRepository<Advertisement, Integer> {
    /*
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO advertisements " + "(sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) "
            + "values(:#{#ad.seller.id}, :#{#ad.category.id}, :#{#ad.title}, :#{#ad.description}, :#{#ad.price}, :#{false})", nativeQuery = true)
    void insertAd(@Param("ad") Advertisement ad);
    */
}
