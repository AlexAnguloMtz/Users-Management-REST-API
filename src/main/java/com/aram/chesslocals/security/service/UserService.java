package com.aram.chesslocals.security.service;

import com.aram.chesslocals.security.domain.User;
import com.aram.chesslocals.security.domain.UserRepository;
import com.aram.chesslocals.security.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.Optional;

@Service
public class UserService {

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

    private boolean alreadyExists(User user) {
        String username = user.getUsername();
        Optional<User> userOptional = userRepository.findByUsernameUsername(username);
        return userOptional.isPresent();
    }

    private UserDto mapToDto(User user) {
        return userMapper.mapToDto(user);
    }

    public User mapToRegularUser(UserDto userDto) {
        return userMapper.mapToRegularUser(userDto);
    }

    public UserDto findUserDtoByUsername(String username) {
        User user = findByUsername(username);
        return mapToDto(user);
    }

    User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameUsername(username);
        return userOptional.orElseThrow(UsernameDoesNotExistException::new);
    }

    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsernameUsername(username);
    }

}
