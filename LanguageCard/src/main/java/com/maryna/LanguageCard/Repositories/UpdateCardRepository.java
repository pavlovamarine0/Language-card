package com.maryna.LanguageCard.Repositories;


import com.maryna.LanguageCard.Models.CardModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCardRepository {
    private final JdbcClient _jdbc;
    public UpdateCardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public int update(CardModel cardModel){
        _jdbc.sql("UPDATE CARDS SET WORD = :word, TRANS_WORD = :trans_Word, PLURAL = :plural WHERE ID = :id")
                .param("word", cardModel.getWord())
                .param("trans_Word", cardModel.getTransWord())
                .param("plural", cardModel.getPlural())
                .param("id", cardModel.getId())
                .update();

        return cardModel.getId();
    }
}
