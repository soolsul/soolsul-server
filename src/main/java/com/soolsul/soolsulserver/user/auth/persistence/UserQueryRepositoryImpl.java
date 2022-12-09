package com.soolsul.soolsulserver.user.auth.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.soolsul.soolsulserver.user.auth.QCustomUser.customUser;
import static com.soolsul.soolsulserver.user.auth.QUserInfo.userInfo;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserLookUpResponse> findUserDetailInfoById(String userId) {
        UserLookUpResponse userLookUpResponse = queryFactory
                .select(Projections.constructor(UserLookUpResponse.class,
                        customUser.id,
                        customUser.email,
                        customUser.password,
                        userInfo.phone,
                        userInfo.name,
                        userInfo.nickname,
                        userInfo.profileImage))
                .from(customUser)
                .join(userInfo).on(customUser.id.eq(userInfo.userId))
                .where(customUser.id.eq(userId))
                .fetchOne();

        return Optional.of(userLookUpResponse);
    }
}
