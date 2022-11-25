package com.soolsul.soolsulserver.user.auth.business;

import com.soolsul.soolsulserver.user.auth.CustomUser;
import com.soolsul.soolsulserver.user.auth.Role;
import com.soolsul.soolsulserver.user.auth.UserContext;
import com.soolsul.soolsulserver.user.auth.UserInfo;
import com.soolsul.soolsulserver.user.auth.exception.UserAlreadyExistsException;
import com.soolsul.soolsulserver.user.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.user.auth.exception.UserNicknameDuplicatedException;
import com.soolsul.soolsulserver.user.auth.presentation.dto.RegisterRequest;
import com.soolsul.soolsulserver.user.auth.repository.UserInfoRepository;
import com.soolsul.soolsulserver.user.auth.repository.UserRepository;
import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new UserContext(user, buildAuthorities(user));
    }

    public void register(RegisterRequest registerRequest) {
        // 중복 회원 검증
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        userInfoRepository.findByNickname(registerRequest.getNickname())
                .ifPresent(user -> {
                    throw new UserNicknameDuplicatedException();
                });

        // 신규 회원 생성
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        CustomUser newUser = new CustomUser(registerRequest.getEmail(), encodedPassword);
        newUser.addRole(Role.USER);

        // 회원 저장
        CustomUser savedUser = userRepository.save(newUser);
        userInfoRepository.save(UserInfo.of(savedUser.getId(), registerRequest));
    }

    public UserLookUpResponse findUserWithDetailInfo(String userId) {
        return userRepository.findUserDetailInfoById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public CustomUser findUserForAuthentication(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private List<GrantedAuthority> buildAuthorities(CustomUser user) {
        return user.getAuthorities()
                .stream()
                .map(userRole -> userRole.getAuthority())
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
