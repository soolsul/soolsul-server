package com.soolsul.soolsulserver.bar.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholTagRepository extends JpaRepository<BarAlcoholTag, String>, AlcoholTagQueryRepository {
}
