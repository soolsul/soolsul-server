package com.soolsul.soolsulserver.auth.business;

import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.Role;
import com.soolsul.soolsulserver.auth.UserContext;
import com.soolsul.soolsulserver.auth.UserInfo;
import com.soolsul.soolsulserver.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.auth.presentation.dto.RegisterRequest;
import com.soolsul.soolsulserver.auth.repository.UserInfoRepository;
import com.soolsul.soolsulserver.auth.repository.UserRepository;
import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;
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
        CustomUser user = userRepository.findByEmail(email);
        if (user == null) {
            if (userRepository.countByEmail(email) == 0) {
                throw new UsernameNotFoundException("No user found with email: " + email);
            }
        }

        List<GrantedAuthority> collect = user.getAuthorities()
                .stream()
                .map(userRole -> userRole.getAuthority())
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserContext(user, collect);
    }

    public void register(RegisterRequest registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        CustomUser newUser = new CustomUser(registerRequest.getEmail(), encodedPassword);
        newUser.addRole(Role.USER);
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
}
