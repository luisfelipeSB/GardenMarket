package pt.iade.gardenmarket.appgarden.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.gardenmarket.appgarden.models.AdCategory;
import pt.iade.gardenmarket.appgarden.models.Advertisement;
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.AdCategoryRepository;
import pt.iade.gardenmarket.appgarden.models.repositories.AdRepository;
import pt.iade.gardenmarket.appgarden.models.repositories.TransactionRepository;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;

@RestController
@RequestMapping(path = "/api/ads")
public class AdController {

    private Logger logger = LoggerFactory.getLogger(AdController.class);
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private AdCategoryRepository adCatgRepository;
    @Autowired
    private TransactionRepository transctRepository;

    // Get all ads
    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Advertisement> getAds() {
        logger.info("Sending all advertisements");
        return adRepository.findAll();
    }

    // Get one ad
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Advertisement getAd(@PathVariable int id) {
        logger.info("Sending ad with id " + id);
        Optional<Advertisement> _ad = adRepository.findById(id);
        if (_ad.isEmpty())
            throw new NotFoundException("" + id, "ad", "id");
        else
            return _ad.get();
    }

    // Get all ad categories
    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdCategory> getAdCategories() {
        logger.info("Sending all ad categories");
        return adCatgRepository.findAll();
    }

    // Get all active ads
    @GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getActiveAds() {
        logger.info("Sending all active ads");
        return adRepository.findActiveAds();
    }

    // Search for ads by title or category
    @GetMapping(path = "/active/{filterType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getFilteredAds(@PathVariable("filterType") int filterType, @RequestParam(value = "filter", defaultValue = "") String filter) {
        logger.info("" + filterType); logger.info(filter);

        if (filterType == 1) {
            logger.info("Sending ads with title: " + filter);
            return adRepository.findActiveAdsByTitle(filter);
        } else if (filterType == 2) {
            logger.info("Sending ads of category: " + filter);
            return adRepository.findActiveAdsByCategory(filter);
        } else {
            return null;
        }
    }

    // Search for ads by title
    @GetMapping(path = "/active/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> searchAdTitle(@RequestParam(value = "t", defaultValue = "") String titleKey) {
        logger.info("Sending ads with title: " + titleKey);
        return adRepository.findActiveAdsByTitle(titleKey);
    }

    // Search for ads by category
    @GetMapping(path = "/active/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> searchAdCategory(@RequestParam(value = "c", defaultValue = "") String categoryKey) {
        logger.info("Sending ads with category: " + categoryKey);
        return adRepository.findActiveAdsByCategory(categoryKey);
    }

    // Introduce an ad to the platform
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Advertisement saveAd(@RequestBody Advertisement newAd) {
        logger.info("Saving a new ad: " + newAd);
        Advertisement ad = adRepository.save(newAd);
        return ad;
    }

    // Verifying whether an ad can be added to a user's cart
    @GetMapping(path = "/{id}/verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean certifyAd(@PathVariable("id") int id, @RequestParam(value = "buyerId", defaultValue = "") int buyerId) {
        logger.info("Verifying that ad " + id + " can be added to transaction " + buyerId);
        Advertisement ad = getAd(id);
        Iterable<AdSummaryView> cartItems = transctRepository.getUserCartItems(buyerId);

        // Checking if the ad is active, and if the user is not also the seller
        if (ad.isActive() && ad.getSeller().getId() != buyerId) {    
            
            // Checking if the ad is in the user's cart already
            for (AdSummaryView item : cartItems) 
                if (ad.getId() == item.getId()) return false;
            
            return true;
        } else {
            return false;
        }
    }
}
