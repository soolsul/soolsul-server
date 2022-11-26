package com.soolsul.soolsulserver.user.auth.presentation;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserDeleteRequest;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.USER_CREATE_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.USER_DELETE_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userDetailsService.register(userRegisterRequest);
        return new ResponseEntity<>(new BaseResponse<>(USER_CREATE_SUCCESS, null), HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@Valid @RequestBody UserDeleteRequest userDeleteRequest) {
        userDetailsService.delete(userDeleteRequest.userId());
        return new ResponseEntity<>(new BaseResponse<>(USER_DELETE_SUCCESS, null), HttpStatus.OK);
    }
}
