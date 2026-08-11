package com.maryna.LanguageCard.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CardExistsRepository {
    public final JdbcClient _jdbc;
    public CardExistsRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public Boolean exists(int cardId){
        var count = _jdbc.sql("SELECT COUNT(*) FROM CARDS WHERE ID = :id")
                .param("id", cardId)
                .query(Integer.class)
                .single();
        return count == 1;
    }
}
