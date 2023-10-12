package com.soolsul.soolsulserver.documentation.base;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;

import com.soolsul.soolsulserver.bar.facade.BarQueryFacade;
import com.soolsul.soolsulserver.curation.facade.CurationQueryFacade;
import com.soolsul.soolsulserver.post.facade.PostFacadeGateway;
import com.soolsul.soolsulserver.reply.facade.ReplyFacadeGateway;
import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageCommandFacade;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageQueryFacade;

@TestConfiguration
public class DocumentTestConfig {

	@Bean
	public CustomUserDetailsService userDetailsService() {
		return mock(CustomUserDetailsService.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public BarQueryFacade barQueryFacade() {
		return mock(BarQueryFacade.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public CurationQueryFacade curationQueryFacade() {
		return mock(CurationQueryFacade.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public MyPageQueryFacade myPageQueryFacade() {
		return mock(MyPageQueryFacade.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public MyPageCommandFacade myPageCommandFacade() {
		return mock(MyPageCommandFacade.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public PostFacadeGateway postFacadeGateway() {
		return mock(PostFacadeGateway.class, MockReset.withSettings(MockReset.AFTER));
	}

	@Bean
	public ReplyFacadeGateway replyFacadeGateway() {
		return mock(ReplyFacadeGateway.class, MockReset.withSettings(MockReset.AFTER));
	}

}
