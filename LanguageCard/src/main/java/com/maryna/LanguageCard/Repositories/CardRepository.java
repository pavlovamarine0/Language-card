package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardRepository {
    private final JdbcClient _jdbc;

    public CardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }

    public CardModel create(CardModel cardModel) {
        var keyHolder = new GeneratedKeyHolder();
        _jdbc.sql("INSERT INTO CARDS(WORD, TRANSLATION, PLURAL) VALUES(:word, :translation, :plural) returning id")
                .param("word",cardModel.getWord())
                .param("translation",cardModel.getTranslation())
                .param("plural",cardModel.getPlural())
                .update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        cardModel.setId(id);
        return cardModel;
    }
    public Boolean exists(int cardId){
        var count = _jdbc.sql("SELECT COUNT(*) FROM CARDS WHERE ID = :id")
                .param("id", cardId)
                .query(Integer.class)
                .single();
        return count == 1;
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROM CARDS WHERE ID = :id")
                .param("id", id)
                .update();
    }
    public Optional<CardModel> getById(int id){
        return _jdbc.sql("SELECT * FROM CARDS WHERE ID = :id")
                .param("id", id)
                .query(CardModel.class)
                .optional();
    }
    public List<CardModel> getAll(int themaId) {
        return _jdbc.sql("SELECT * FROM CARDS " +
                        "JOIN THEMES_CARDS ON cards.id = THEMES_CARDS.card_id " +
                        "WHERE THEMES_CARDS.theme_id = :themaId;")
                .param("themaId", themaId)
                .query(CardModel.class)
                .list();
    }
    public List<CardModel> findThemaIdsIsNull(){
        return _jdbc.sql("SELECT * FROM CARDS " +
                        "LEFT JOIN THEMES_CARDS ON " +
                        "cards.id = THEMES_CARDS.card_id WHERE THEMES_CARDS.card_id IS NULL")
                .query(CardModel.class)
                .list();
    }
    public int update(CardModel cardModel){
        _jdbc.sql("UPDATE CARDS SET WORD = :word, TRANSLATION = :translation, PLURAL = :plural WHERE ID = :id")
                .param("word", cardModel.getWord())
                .param("translation", cardModel.getTranslation())
                .param("plural", cardModel.getPlural())
                .param("id", cardModel.getId())
                .update();
        return cardModel.getId();
    }
}
