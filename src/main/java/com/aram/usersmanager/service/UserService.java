package com.aram.usersmanager.service;

import com.aram.usersmanager.domain.User;
import com.aram.usersmanager.domain.UserRepository;
import com.aram.usersmanager.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public UserDto save(UserDto userdto) {
        User user = mapToRegularUser(userdto);
        if(alreadyExists(user)) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        User savedUser =  userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Transactional
    private boolean alreadyExists(User user) {
        String username = user.getUsername();
        Optional<User> userOptional = userRepository.findByUsernameUsername(username);
        return userOptional.isPresent();
    }

    @Transactional
    User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameUsername(username);
        return userOptional.orElseThrow(UsernameDoesNotExistException::new);
    }

    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsernameUsername(username);
    }

    public UserDto findUserDtoByUsername(String username) {
        User user = findByUsername(username);
        return mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        return userMapper.mapToDto(user);
    }

    public User mapToRegularUser(UserDto userDto) {
        return userMapper.mapToRegularUser(userDto);
    }


}
