package com.soolsul.soolsulserver.user.mypage.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserEditFormResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageCommandFacade;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageQueryFacade;
import com.soolsul.soolsulserver.user.mypage.common.dto.reqeust.UserInfoEditRequest;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserReplyListLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.FEED_FIND_ALL_SCRAP_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.MYPAGE_POSTS_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.MYPAGE_REPLIES_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.MYPAGE_USER_INFO_EDIT_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.MYPAGE_USER_INFO_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_LOOK_UP_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypages")
public class MyPageController {

    private final MyPageQueryFacade myPageQueryFacade;
    private final MyPageCommandFacade myPageCommandFacade;
    private final MessageSource messageSource;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserLookUpResponse>> searchUser(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        UserLookUpResponse userLookUpResponse = myPageQueryFacade.findUserWithDetailInfo(currentUser.getId());

        String message = messageSource.getMessage(USER_LOOK_UP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<UserLookUpResponse> baseResponse = new BaseResponse<>(
                USER_LOOK_UP_SUCCESS.getCode(),
                message,
                userLookUpResponse
        );

        return ResponseEntity.ok(baseResponse);
    }


    @GetMapping("/scraps")
    public ResponseEntity<BaseResponse<ScrapedPostListLookUpResponse>> findAllScrapedPost(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        ScrapedPostListLookUpResponse scrapedPostListResponse = myPageQueryFacade.findAllScrapedPost(currentUser.getId());

        String message = messageSource.getMessage(FEED_FIND_ALL_SCRAP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<ScrapedPostListLookUpResponse> baseResponse = new BaseResponse<>(
                FEED_FIND_ALL_SCRAP_SUCCESS.getCode(),
                message,
                scrapedPostListResponse
        );

        return ResponseEntity.ok(baseResponse);
    }


    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<UserPostListLookUpResponse>> findAllUserPost(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        UserPostListLookUpResponse userPostListLookUpResponse = myPageQueryFacade.findAllUserPost(currentUser.getId());

        String message = messageSource.getMessage(MYPAGE_POSTS_FIND_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<UserPostListLookUpResponse> baseResponse = new BaseResponse<>(
                MYPAGE_POSTS_FIND_SUCCESS.getCode(),
                message,
                userPostListLookUpResponse
        );

        return ResponseEntity.ok(baseResponse);
    }


    @GetMapping("/replies")
    public ResponseEntity<BaseResponse<UserReplyListLookUpResponse>> findAllUserReply(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        UserReplyListLookUpResponse userReplyListLookUpResponse = myPageQueryFacade.findAllUserReplies(currentUser.getId());

        String message = messageSource.getMessage(MYPAGE_REPLIES_FIND_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<UserReplyListLookUpResponse> baseResponse = new BaseResponse<>(
                MYPAGE_REPLIES_FIND_SUCCESS.getCode(),
                message,
                userReplyListLookUpResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/edit")
    public ResponseEntity<BaseResponse<UserEditFormResponse>> findUserEditForm(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        UserEditFormResponse userEditForm = myPageQueryFacade.findUserEditForm(currentUser.getId());

        String message = messageSource.getMessage(MYPAGE_USER_INFO_FIND_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<UserEditFormResponse> baseResponse = new BaseResponse<>(
                MYPAGE_USER_INFO_FIND_SUCCESS.getCode(),
                message,
                userEditForm
        );

        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/edit")
    public ResponseEntity<BaseResponse<Void>> editUserInfo(
            @Valid @RequestBody UserInfoEditRequest editRequest,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        myPageCommandFacade.editUserInfo(editRequest, currentUser.getId());

        String message = messageSource.getMessage(MYPAGE_USER_INFO_EDIT_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(
                MYPAGE_USER_INFO_EDIT_SUCCESS.getCode(),
                message,
                null
        );

        return ResponseEntity.ok(baseResponse);
    }
}
