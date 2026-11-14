package com.maryna.LanguageCard.Controllers;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.DTOs.Users.LoginResponseDto;
import com.maryna.LanguageCard.Models.UserModel;
import com.maryna.LanguageCard.Services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService _userService;
    public UsersController(UserService userService){
        _userService = userService;
    }
    @PostMapping("/register")
    public void createUser(@RequestBody UserModel userModel) throws BadRequestException{
        _userService.create(userModel);
    }
    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody UserModel userModel) throws BadRequestException{
        return _userService.loginUser(userModel);
    }
    @PutMapping("/me")
    public int updateUser(@RequestBody UserModel userModel) throws BadRequestException{
        return _userService.update(userModel);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(int id) throws BadRequestException{
        _userService.delete(id);
    }
    @GetMapping()
    public List<UserModel> getUsers() {
        return _userService.getAll();
    }

    @GetMapping("/me")
    public UserModel getMe(@RequestHeader (value = "Auth") String userId)throws BadRequestException{
        return _userService.getMe(Integer.parseInt(userId));
    }
    @GetMapping("/{userId}/cards")
    public List<CardModel> getAllCards(int userId)throws BadRequestException{
        return _userService.getAllCards(userId);
    }

}
