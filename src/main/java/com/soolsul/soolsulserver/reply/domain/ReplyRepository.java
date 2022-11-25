package com.soolsul.soolsulserver.reply.domain;

import com.soolsul.soolsulserver.reply.domain.query.ReplyQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, String>, ReplyQueryRepository {
}
