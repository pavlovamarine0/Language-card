package com.maryna.LanguageCard.Repositories;


import com.maryna.LanguageCard.Models.CardModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SingleCardRepository {
    private final JdbcClient _jdbc;
    public SingleCardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public int selectOne(CardModel cardModel) {
        var count = _jdbc.sql("SELECT COUNT(*) FROM CARDS WHERE ID = :id")
                .param("id", cardModel.getId())
                .query(Integer.class)
                .single();
        return count;
    }
}
