package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
        // Nothing needed here for now
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> searchResults = new ArrayList<>();

        try {
            InputStream carInput = getClass().getResourceAsStream("/rental_cars.json");
            InputStream hotelInput = getClass().getResourceAsStream("/hotels.json");

            List<SearchResult> carResults = mapper.readValue(carInput, new TypeReference<List<SearchResult>>() {});
            List<SearchResult> hotelResults = mapper.readValue(hotelInput, new TypeReference<List<SearchResult>>() {});

            searchResults.addAll(carResults);
            searchResults.addAll(hotelResults);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register the Search resource to handle /search
        environment.jersey().register(new Search(searchResults));
    }
}
