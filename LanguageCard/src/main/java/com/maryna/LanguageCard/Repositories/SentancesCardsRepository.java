package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SentancesCardsRepository {
    public final JdbcClient _jdbc;
    public SentancesCardsRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public void connect(SentanceModel sentanceModel, int cardId){
        _jdbc.sql("INSERT INTO CARDS_SENTANCE(SENTANCE_ID, CARD_ID) VALUES(:id, :cardId)")
                .param("cardId",cardId)
                .param("id",sentanceModel.getId())
                .update();
    }
}
