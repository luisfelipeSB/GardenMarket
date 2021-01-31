package pt.iade.gardenmarket.appgarden.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pt.iade.gardenmarket.appgarden.models.Advertisement;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;

public interface AdRepository extends CrudRepository<Advertisement, Integer> {

    // Getting all active ads
    String activeAdsQuery = 
        "SELECT ad_id AS id, ad_title AS title, usr_name AS seller, ad_price AS price, c.catg_name AS category " +
        "FROM advertisements a, users, adcategories c " + 
        "WHERE usr_id = sellr_id AND c.catg_id = a.catg_id " +
        "AND ad_isactive = true ";
    @Query(value = activeAdsQuery, nativeQuery = true)
    Iterable<AdSummaryView> findActiveAds();

    // Getting ads based on title as search key
    String adsByTitleQuery = activeAdsQuery + "AND ad_title like %:titleKey% ";
    @Query(value = adsByTitleQuery, nativeQuery = true)
    Iterable<AdSummaryView> findActiveAdsByTitle(@Param("titleKey") String titleKey);

    // Getting ads based on category as search key
    String adsByCategoryQuery = activeAdsQuery + "AND catg_name = :categoryKey ";
    @Query(value = adsByCategoryQuery, nativeQuery = true)
    Iterable<AdSummaryView> findActiveAdsByCategory(@Param("categoryKey") String categoryKey);
    
}