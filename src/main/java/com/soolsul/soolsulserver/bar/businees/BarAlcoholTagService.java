package com.soolsul.soolsulserver.bar.businees;

import com.soolsul.soolsulserver.bar.persistence.BarAlcoholTagQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarAlcoholTagService {

    private final BarAlcoholTagQueryRepository barAlcoholTagQueryRepository;

    public List<String> findBarAlcoholTagIdsByAlcoholCategoryIds(List<String> alcoholTagIds) {
        return barAlcoholTagQueryRepository.findBarAlcoholTagIdsByAlcoholCategoryIds(alcoholTagIds);
    }

}
