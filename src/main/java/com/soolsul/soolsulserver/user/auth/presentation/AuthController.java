package com.soolsul.soolsulserver.user.auth.presentation;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.presentation.dto.DeleteRequest;
import com.soolsul.soolsulserver.user.auth.presentation.dto.RegisterRequest;
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
    public ResponseEntity<BaseResponse<Void>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userDetailsService.register(registerRequest);
        return new ResponseEntity<>(new BaseResponse<>(USER_CREATE_SUCCESS, null), HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@Valid @RequestBody DeleteRequest deleteRequest) {
        userDetailsService.delete(deleteRequest.userId());
        return new ResponseEntity<>(new BaseResponse<>(USER_DELETE_SUCCESS, null), HttpStatus.OK);
    }
}
