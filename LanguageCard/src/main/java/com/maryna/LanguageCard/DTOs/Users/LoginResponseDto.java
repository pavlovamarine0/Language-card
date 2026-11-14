package com.maryna.LanguageCard.DTOs.Users;

public class LoginResponseDto {
    private int userId;
    public LoginResponseDto(int userId){
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
