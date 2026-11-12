package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.UserModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepository {
    private final JdbcClient _jdbc;
    public UserRepository(JdbcClient jdbc){
        _jdbc = jdbc;
    }
    public void create(UserModel userModel){
        _jdbc.sql("INSERT INTO USERS (USERNAME, EMAIL, PASSWORD) VALUES(:username, :email, :password)")
                .param("username", userModel.getUsername())
                .param("email", userModel.getEmail())
                .param("password", userModel.getPassword())
                .update();
    }
    public UserModel getByEmail(String email){
        var user = _jdbc.sql("SELECT * FROM USERS WHERE EMAIL = :email")
                .param("email", email)
                .query(UserModel.class)
                .optional();
        return user.isEmpty() ? null: user.get();
    }
    public int update(UserModel userModel){
        _jdbc.sql("UPDATE USERS SET USERNAME = :username, EMAIL = :email, PASSWORD = : password WHERE ID = :id")
                .param("username", userModel.getUsername())
                .param("email", userModel.getEmail())
                .param("password", userModel.getPassword())
                .update();
        return userModel.getId();
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROME USERS WHERE ID = :id")
                .param("id", id)
                .update();
    }
    /*public Boolean existLogin(String login){
       return  _jdbc.sql("SELECT * FROM USERS WHERE LOGIN LIKE :login")
                .param("login", login)
                .query(UserModel.class)
    }*/
    public Boolean exists(int userId){
        var count = _jdbc.sql("SELECT COUNT(*) FROM USERS WHERE ID = :id")
                .param("id", userId)
                .query(Integer.class)
                .single();
        return count == 1;
    }
    public List<UserModel> getAll(){
        return _jdbc.sql("SELECT * FROM USERS")
                .query(UserModel.class)
                .list();
    }
    public UserModel getMe(int userId){
           var user = _jdbc.sql("SELECT * FROM USERS WHERE ID = :id")
                .param("id", userId)
                .query(UserModel.class)
                .optional();
           return user.isEmpty() ? null: user.get();
    }
    public List<CardModel> getAllCards(int userId){
        return  _jdbc.sql("SELECT * FROM CARDS WHERE USER_ID = : userId")
                .param("userId", userId)
                .query(CardModel.class)
                .list();
    }
}
