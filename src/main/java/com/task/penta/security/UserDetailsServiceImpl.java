package com.task.penta.security;

import com.task.penta.entity.SystemUser;
import com.task.penta.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        List<SystemUser> systemUser = systemUserRepository.findByUserId(userId);
        if (systemUser.isEmpty()) {
            throw new UsernameNotFoundException("Not Found " + userId);
        }

        return new UserDetailsImpl(systemUser.get(0));
    }
}