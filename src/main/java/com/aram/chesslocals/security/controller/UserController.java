package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.UserService;
import com.aram.chesslocals.security.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/v1/user")
final class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    // Data validation is done in the domain,
    // so there is no need to validate user data in the controller layer
    @PostMapping("/create")
    ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        UserDto savedDto = userService.save(userDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }


    @GetMapping("/{username}")
    ResponseEntity<UserDto> loadByUsername(@PathVariable String username) {
        UserDto userDto = userService.findUserDtoByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public HttpStatus delete(@PathVariable String username) {
        userService.delete(username);
        return HttpStatus.OK;
    }
}
