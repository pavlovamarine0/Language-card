package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

@Service
public class CardSentanceRepository {
    private final JdbcClient _jdbc;
    public CardSentanceRepository(JdbcClient jdbc){
        _jdbc = jdbc;
    }

    public void bind(SentanceModel sentanceModel, int cardId){
        _jdbc.sql("INSERT INTO CARDS_SENTANCE(SENTANCE_ID, CARD_ID) VALUES(:id, :cardId)")
                .param("cardId",cardId)
                .param("id",sentanceModel.getId())
                .update();
    }
    public Boolean exists(int cardId){
        return _jdbc.sql("SELECT EXISTS (SELECT 1 FROM CARDS_SENTANCE WHERE CARD_ID :id)")
                .param("id", cardId)
                .query(Boolean.class)
                .single();
    }
}
