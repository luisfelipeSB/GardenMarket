package pt.iade.gardenmarket.appgarden.controllers;

import java.util.ArrayList;
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

import pt.iade.gardenmarket.appgarden.models.Advertisement;
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.AdRepository;
import pt.iade.gardenmarket.appgarden.models.views.AdSummaryView;

@RestController
@RequestMapping(path = "/api/ads")
public class AdController {

    private Logger logger = LoggerFactory.getLogger(AdController.class);
    @Autowired
    private AdRepository adRepository;

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

    // Get all active ads
    @GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> getActiveAds() {
        logger.info("Sending all active ads");
        return adRepository.findActiveAds();
    }

    // Search for ads by title
    @GetMapping(path = "/filter_title", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> searchAdTitle(@RequestParam(value = "title", defaultValue = "") String titleKey) {
        logger.info("Sending ads with title: " + titleKey);
        return adRepository.findActiveAdsByTitle(titleKey);
    }

    // Search for ads by category
    @GetMapping(path = "/filter_category", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AdSummaryView> searchAdCategory(@RequestParam(value = "category", defaultValue = "") String categoryKey) {
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

    @PostMapping(path = "/{i}/{c}/{t}/{d}/{p}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int insertAd(@PathVariable("i") int sid, @PathVariable("c") int cid, 
    @PathVariable("t") String title, @PathVariable("d") String descrp, @PathVariable("p") float price, @RequestBody Boolean active) {
        logger.info("Saving a new ad: ");
        return adRepository.insertAd(sid, cid, title, descrp, price);
    }

}
