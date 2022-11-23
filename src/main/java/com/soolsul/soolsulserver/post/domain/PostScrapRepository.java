package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.post.domain.query.PostScrapQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostScrapRepository extends JpaRepository<PostScrap, String>, PostScrapQueryRepository {
}
