package com.soolsul.soolsulserver.bar.businees;

import com.soolsul.soolsulserver.bar.common.dto.response.BarSnackMenuResponse;
import com.soolsul.soolsulserver.bar.persistence.BarSnackMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarSnackMenuService {

    private final BarSnackMenuRepository barSnackMenuRepository;

    public List<BarSnackMenuResponse> findAllBarSnackMenuByBarId(String barId) {
        return barSnackMenuRepository.findAllBarSnackMenuByBarId(barId);
    }

}
