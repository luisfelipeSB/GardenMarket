package pt.iade.gardenmarket.appgarden.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.gardenmarket.appgarden.models.Advertisement;
import pt.iade.gardenmarket.appgarden.models.exceptions.NotFoundException;
import pt.iade.gardenmarket.appgarden.models.repositories.AdRepository;

@RestController
@RequestMapping(path = "/api/ads")
public class AdController {

    private Logger logger = LoggerFactory.getLogger(AdController.class);
    @Autowired
    private AdRepository adRepository;

    // Get all ads
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Advertisement> getAds() {
        logger.info("Sending all ads");
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

    // Search for ads by title
    @GetMapping(path = "/filter_title", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Advertisement> searchAdTitle(@RequestParam(value = "title", defaultValue = "") String title) {
        logger.info("Sending ads with title: " + title);
        ArrayList<Advertisement> ads = (ArrayList<Advertisement>) getAds();
        ads.removeIf((a) -> !(a.getTitle().contains(title)));
        return ads;
    }

    // Search for ads by category
    @GetMapping(path = "/filter_category", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Advertisement> searchAdCategory(
            @RequestParam(value = "category", defaultValue = "") String category) {
        logger.info("Sending ads with category: " + category);
        ArrayList<Advertisement> ads = (ArrayList<Advertisement>) getAds();
        ads.removeIf((a) -> !(a.getCategory().getName().contains(category)));
        return ads;
    }

    /*
    // Introduce an ad to the platform
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void insertAd(@RequestBody String title, @RequestBody String category, @RequestBody String description, @RequestBody float price) {
        adRepository.insertAd(new Advertisement(title, category, description, price));
    }
    */
}
