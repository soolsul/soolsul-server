package com.soolsul.soolsulserver.common.data;

import com.soolsul.soolsulserver.bar.domain.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final String STORE_UUID = "store_uuid";

    private final RestaurantRepository restaurantRepository;

    public DataLoader(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void loadData() {
        log.info("[call DataLoader]");
        //restaurantRepository.save(new Restaurant(STORE_UUID, "temp_region_id", "temp_category_id", null));
        log.info("[init complete DataLoader]");
    }
}
