package com.maryna.LanguageCard.Repositories;


import com.maryna.LanguageCard.Models.CardModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCardRepository {
    private final JdbcClient _jdbc;
    public ListCardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public List<CardModel> getAll() {
        return _jdbc.sql("SELECT * FROM CARDS")
                .query(CardModel.class)
                .list();
    }
}
