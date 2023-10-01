package com.soolsul.soolsulserver.persistence;

import com.soolsul.soolsulserver.bar.persistence.BarAlcoholTagQueryRepository;
import com.soolsul.soolsulserver.bar.persistence.BarMoodTagRepository;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.persistence.BarSnackMenuRepository;
import com.soolsul.soolsulserver.common.config.QueryDslConfig;
import com.soolsul.soolsulserver.curation.persistence.CurationQueryRepository;
import com.soolsul.soolsulserver.menu.alcohol.persistence.AlcoholCategoryRepository;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.domain.query.PostQueryRepositoryImpl;
import com.soolsul.soolsulserver.reply.domain.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Import(QueryDslConfig.class)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {CurationQueryRepository.class, ReplyRepository.class, BarAlcoholTagQueryRepository.class,
                        BarSnackMenuRepository.class, BarMoodTagRepository.class, BarQueryRepository.class,
                        AlcoholCategoryRepository.class, PostRepository.class, PostQueryRepositoryImpl.class})
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {

    @Autowired
    protected ReplyRepository replyRepository;

    @Autowired
    protected CurationQueryRepository curationQueryRepository;

    @Autowired
    protected BarAlcoholTagQueryRepository barAlcoholTagQueryRepository;

    @Autowired
    protected BarSnackMenuRepository barSnackMenuRepository;

    @Autowired
    protected BarQueryRepository barQueryRepository;

    @Autowired
    protected BarMoodTagRepository barMoodTagRepository;

    @Autowired
    protected AlcoholCategoryRepository alcoholCategoryRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PostQueryRepositoryImpl postQueryRepository;

    @Autowired
    protected TestEntityManager testEntityManager;

}
