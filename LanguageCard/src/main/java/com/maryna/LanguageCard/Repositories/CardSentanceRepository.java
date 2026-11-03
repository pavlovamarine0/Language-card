package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardSentanceRepository {
    private final JdbcClient _jdbc;
    public CardSentanceRepository(JdbcClient jdbc){
        _jdbc = jdbc;
    }

    public void bind(int sentenceId, int cardId){
        _jdbc.sql("INSERT INTO CARDS_SENTENCES(SENTENCE_ID, CARD_ID) VALUES(:id, :cardId)")
                .param("cardId",cardId)
                .param("id",sentenceId)
                .update();
    }
    public Boolean existsByCard(int cardId){
        return _jdbc.sql("SELECT EXISTS (SELECT 1 FROM CARDS_SENTENCES WHERE CARD_ID =:id)")
                .param("id", cardId)
                .query(Boolean.class)
                .single();
    }
    public void unbind(int cardId) {
        _jdbc.sql("""
        DELETE FROM CARDS_SENTENCES
        WHERE card_id = :cardId
        """)
                .param("cardId", cardId)
                .update();
    }
    public List<Integer> getSentenceIds(int cardId){
        return _jdbc.sql("SELECT SENTENCE_ID FROM CARDS_SENTENCES WHERE CARD_ID = :id")
                .param("id",cardId)
                .query(Integer.class)
                .list();
    }
    public Boolean exists(int sentenceId){
        return _jdbc.sql("SELECT EXISTS (SELECT 1 FROM CARDS_SENTENCES WHERE SENTENCE_ID = :id)")
                .param("id", sentenceId)
                .query(Boolean.class)
                .single();
    }
}
