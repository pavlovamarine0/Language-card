package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.DTOs.Users.LoginResponseDto;
import com.maryna.LanguageCard.Models.UserModel;
import com.maryna.LanguageCard.Repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository _userRepository;
    public UserService(UserRepository userRepository){
        _userRepository = userRepository;
    }
   @Transactional
    public void create(UserModel userModel) throws BadRequestException {
       var existingUser = _userRepository.getByEmail(userModel.getEmail());
       if(existingUser != null){
           throw new BadRequestException("The email exists!");
       }
        _userRepository.create(userModel);
    }
    public LoginResponseDto loginUser(UserModel userModel) throws  BadRequestException{
        var existingUser = _userRepository.getByEmail(userModel.getEmail());
        if(existingUser == null){
            throw new BadRequestException("The wrong email or password!");
        }
        if(!userModel.getPassword().equals(existingUser.getPassword())) {
            throw new BadRequestException("The wrong email or password!");
        };
        return new LoginResponseDto(existingUser.getId());
    }
    @Transactional
    public int update(UserModel userModel) throws BadRequestException{
        if(!_userRepository.exists(userModel.getId())){
            throw new BadRequestException("There is no such a user!");
        }
        return _userRepository.update(userModel);
    }
    public void delete(int id) throws BadRequestException{
        if(!_userRepository.exists(id)){
            throw new BadRequestException("There is no such a user!");
        }
        _userRepository.delete(id);
    }
    public List<UserModel> getAll(){
        return _userRepository.getAll();
    }
    public UserModel getMe(int userId) throws BadRequestException{
        if(!_userRepository.exists(userId)){
            throw new BadRequestException("There is no such a user!");
        }
        return _userRepository.getMe(userId);
    }
    public List<CardModel> getAllCards(int userId) throws BadRequestException{
        if(!_userRepository.exists(userId)){
            throw new BadRequestException("There is no such a user!");
        }
        return _userRepository.getAllCards(userId);
    }
}
