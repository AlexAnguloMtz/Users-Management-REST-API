package com.aram.chesslocals.security.service;

import com.aram.chesslocals.security.domain.User;
import com.aram.chesslocals.security.domain.UserRepository;
import com.aram.chesslocals.security.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto save(UserDto userdto) {
        User user = mapToRegularUser(userdto);
        if(alreadyExists(user)) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        User savedUser =  userRepository.save(user);
        return mapToDto(savedUser);
    }

    private boolean alreadyExists(User aUser) {
        Optional<User> user = findByUsername(aUser.getUsername());
        return user.isPresent();
    }

    private UserDto mapToDto(User user) {
        return userMapper.mapToDto(user);
    }

    public User mapToRegularUser(UserDto userDto) {
        return userMapper.mapToRegularUser(userDto);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameUsername(username);
    }

    public UserDto loadUserDtoByUsername(String username) {
        Optional<User> userOptional = findByUsername(username);
        User foundUser = userOptional.orElseThrow(UsernameDoesNotExistException::new);
        return mapToDto(foundUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
