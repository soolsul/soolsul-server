package com.soolsul.soolsulserver.common.data;

import com.soolsul.soolsulserver.bar.domain.BarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final String STORE_UUID = "store_uuid";

    private final BarRepository barRepository;

    public DataLoader(BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public void loadData() {
        log.info("[call DataLoader]");
        //barRepository.save(new Restaurant(STORE_UUID, "temp_region_id", "temp_category_id", null));
        log.info("[init complete DataLoader]");
    }
}
