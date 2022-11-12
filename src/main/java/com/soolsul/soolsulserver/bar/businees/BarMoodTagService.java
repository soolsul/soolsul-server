package com.soolsul.soolsulserver.bar.businees;

import com.soolsul.soolsulserver.bar.persistence.BarMoodTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarMoodTagService {

    private final BarMoodTagRepository barMoodTagRepository;

    public List<String> findBarAlcoholTagIdsByAlcoholNames(List<String> moodTagNames) {
        return barMoodTagRepository.findBarMoodTagIdsByMoodNames(moodTagNames);
    }

}
