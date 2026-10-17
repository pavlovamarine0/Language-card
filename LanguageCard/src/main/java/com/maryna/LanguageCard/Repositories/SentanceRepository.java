package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SentanceRepository {
    private final JdbcClient _jdbc;
    public SentanceRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public SentanceModel create(SentanceModel sentanceModel) {
        var keyHolder = new GeneratedKeyHolder();
        _jdbc.sql("INSERT INTO SENTENCES(SENTENCE, TRANSLATE) VALUES(:sentence, :translate) returning id")
                .param("sentence", sentanceModel.getSentence())
                .param("translate",sentanceModel.getTranslate())
                .update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        sentanceModel.setId(id);
        return sentanceModel;
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROM SENTENCES WHERE ID = :id")
                .param("id", id)
                .update();
    }
    public Optional<SentanceModel> getById(int id){
        return _jdbc.sql("SELECT * FROM SENTENCES WHERE ID = :id")
                .param("id", id)
                .query(SentanceModel.class)
                .optional();
    }
    public List<SentanceModel> getAll(int cardId) {
        return _jdbc.sql("SELECT s.* FROM SENTENCES s JOIN CARDS_SENTENCES cs " +
                        "ON cs.SENTENCE_ID = s.ID WHERE cs.CARD_ID = :cardId")
                .param("cardId", cardId)
                .query(SentanceModel.class)
                .list();
    }
    public int update(SentanceModel sentanceModel){
        _jdbc.sql("UPDATE SENTENCES SET SENTENCE = :sentence, TRANSLATE = :translate WHERE ID = :id")
                .param("sentence",sentanceModel.getSentence())
                .param("translate", sentanceModel.getTranslate())
                .param("id", sentanceModel.getId())
                .update();

        return sentanceModel.getId();
    }
    public Boolean exists(int sentenceId){
        return _jdbc.sql("SELECT EXISTS (SELECT 1 FROM SENTENCES WHERE SENTENCE_ID = :id)")
                .param("id", sentenceId)
                .query(Boolean.class)
                .single();
    }
}
