package com.maryna.LanguageCard.Repositories;


import com.maryna.LanguageCard.Models.CardModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCardbyIDRepository {
    private final JdbcClient _jdbc;
    public  GetCardbyIDRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public Optional<CardModel> getById(int id){
        return _jdbc.sql("SELECT * FROM CARDS WHERE ID = :id")
                .param("id", id)
                .query(CardModel.class)
                .optional();

    }
}
