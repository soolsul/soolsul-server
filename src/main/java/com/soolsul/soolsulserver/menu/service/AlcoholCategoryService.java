package com.soolsul.soolsulserver.menu.service;

import com.soolsul.soolsulserver.menu.persistence.AlcoholCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholCategoryService {

    private final AlcoholCategoryRepository alcoholCategoryRepository;

    List<String> findAlcoholCategoryIdsByAlcoholCategoryNames(List<String> alcoholCategoryNames) {
        return alcoholCategoryRepository.findAlcoholCategoryIdsByAlcoholCategoryNames(alcoholCategoryNames);
    }

}
