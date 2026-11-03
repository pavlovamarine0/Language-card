package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemaCardRepository {
    private final JdbcClient _jdbc;
    public ThemaCardRepository(JdbcClient jdbcClient)
    {
        _jdbc = jdbcClient;
    }
    public List<Integer> getThemaIds(int cardId){
        return _jdbc.sql("SELECT THEME_ID FROM THEMES_CARDS WHERE CARD_ID = :id")
                .param("id",cardId)
                .query(Integer.class)
                .list();
    }

    public void bind(int cardId, int themaId){
        _jdbc.sql("INSERT INTO THEMES_CARDS(THEME_ID, CARD_ID) VALUES(:themaId, :cardId)")
                .param("themaId",themaId)
                .param("cardId", cardId)
                .update();
    }
    public Boolean exists(int themaId){
        return _jdbc.sql("SELECT EXISTS (SELECT 1 FROM THEMES_CARDS WHERE THEME_ID = :id)")
                .param("id", themaId)
                .query(Boolean.class)
                .single();
    }
    public void unbind(int cardId) {
        _jdbc.sql("""
        DELETE FROM themes_cards
        WHERE card_id = :cardId
        """)
                .param("cardId", cardId)
                .update();
    }

}
