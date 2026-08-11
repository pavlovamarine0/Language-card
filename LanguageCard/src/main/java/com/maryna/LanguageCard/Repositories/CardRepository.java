package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CardRepository {
    private final JdbcClient _jdbc;

    public CardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }

    public CardModel create(CardModel cardModel) {
        var keyHolder = new GeneratedKeyHolder();
        _jdbc.sql("INSERT INTO CARDS(WORD, TRANS_WORD, PLURAL) VALUES(:word, :trans_Word, :plural) returning id")
                .param("word",cardModel.getWord())
                .param("trans_Word",cardModel.getTransWord())
                .param("plural",cardModel.getPlural())
                .update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        cardModel.setId(id);
        return cardModel;
    }
}
