package com.soolsul.soolsulserver.user.mypage.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserEditFormResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.auth.vo.CurrentUserDto;
import com.soolsul.soolsulserver.user.mypage.common.dto.reqeust.UserInfoEditRequest;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserReplyListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageCommandFacade;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageQueryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.FEED_FIND_ALL_SCRAP_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.MYPAGE_POSTS_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.MYPAGE_REPLIES_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.MYPAGE_USER_INFO_EDIT_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.MYPAGE_USER_INFO_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.USER_LOOK_UP_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypages")
public class MyPageController {

    private final MyPageQueryFacade myPageQueryFacade;
    private final MyPageCommandFacade myPageCommandFacade;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserLookUpResponse>> searchMe(@CurrentUser CurrentUserDto currentUserDto) {
        UserLookUpResponse userLookUpResponse = myPageQueryFacade.findUserWithDetailInfo(currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(USER_LOOK_UP_SUCCESS, userLookUpResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserLookUpResponse>> searchUser(@PathVariable String userId) {
        UserLookUpResponse userLookUpResponse = myPageQueryFacade.findUserWithDetailInfo(userId);
        return ResponseEntity.ok(new BaseResponse<>(USER_LOOK_UP_SUCCESS, userLookUpResponse));
    }

    @GetMapping("/scraps")
    public ResponseEntity<BaseResponse<ScrapedPostListLookUpResponse>> findAllScrapedPost(@CurrentUser CurrentUserDto currentUserDto) {
        ScrapedPostListLookUpResponse scrapedPostListResponse = myPageQueryFacade.findAllScrapedPost(currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(FEED_FIND_ALL_SCRAP_SUCCESS, scrapedPostListResponse));
    }

    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<UserPostListLookUpResponse>> findAllUserPost(@CurrentUser CurrentUserDto currentUserDto) {
        UserPostListLookUpResponse userPostListLookUpResponse = myPageQueryFacade.findAllUserPost(currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(MYPAGE_POSTS_FIND_SUCCESS, userPostListLookUpResponse));
    }

    @GetMapping("/replies")
    public ResponseEntity<BaseResponse<UserReplyListLookUpResponse>> findAllUserReply(@CurrentUser CurrentUserDto currentUserDto) {
        UserReplyListLookUpResponse userReplyListLookUpResponse = myPageQueryFacade.findAllUserReplies(currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(MYPAGE_REPLIES_FIND_SUCCESS, userReplyListLookUpResponse));
    }

    @GetMapping("/edit")
    public ResponseEntity<BaseResponse<UserEditFormResponse>> findUserEditForm(@CurrentUser CurrentUserDto currentUserDto) {
        UserEditFormResponse userEditForm = myPageQueryFacade.findUserEditForm(currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(MYPAGE_USER_INFO_FIND_SUCCESS, userEditForm));
    }

    @PatchMapping("/edit")
    public ResponseEntity<BaseResponse<Void>> editUserInfo(@Valid @RequestBody UserInfoEditRequest editRequest, @CurrentUser CurrentUserDto currentUserDto) {
        myPageCommandFacade.editUserInfo(editRequest, currentUserDto.id());
        return ResponseEntity.ok(new BaseResponse<>(MYPAGE_USER_INFO_EDIT_SUCCESS, null));
    }
}
