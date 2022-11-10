package com.soolsul.soolsulserver.bar.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodTagRepository extends JpaRepository<BarMoodTag, String>, MoodTagQueryRepository {
}
