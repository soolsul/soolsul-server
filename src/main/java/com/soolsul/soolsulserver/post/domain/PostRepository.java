package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.post.domain.query.PostQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String>, PostQueryRepository {
}
