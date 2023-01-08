package com.soolsul.soolsulserver.user.auth.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_CREATE_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_DELETE_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final MessageSource messageSource;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> registerUser(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest,
            Locale locale
    ) {
        userDetailsService.register(userRegisterRequest);

        String message = messageSource.getMessage(USER_CREATE_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(USER_CREATE_SUCCESS.getCode(), message, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        userDetailsService.delete(currentUser.getId());

        String message = messageSource.getMessage(USER_DELETE_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(USER_DELETE_SUCCESS.getCode(), message, null);

        return ResponseEntity.ok(baseResponse);
    }

}
