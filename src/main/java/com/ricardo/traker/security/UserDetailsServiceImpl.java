package com.ricardo.traker.security;

import com.ricardo.traker.model.entity.UserEntity;
import com.ricardo.traker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User with nickname" + nickname + "doesn't exist"));

        return new UserDetailsImpl(userEntity);
    }
}
