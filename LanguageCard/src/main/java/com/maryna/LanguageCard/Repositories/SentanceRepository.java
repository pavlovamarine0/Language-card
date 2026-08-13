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
        _jdbc.sql("INSERT INTO SENTANCES(TEXT, TRANSLATE) VALUES(:text, :translate) returning id")
                .param("text", sentanceModel.getText())
                .param("translate",sentanceModel.getTranslate())
                .update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        sentanceModel.setId(id);
        return sentanceModel;
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROM SENTANCES WHERE ID = :id")
                .param("id", id)
                .update();
    }
    public Optional<SentanceModel> getById(int id){
        return _jdbc.sql("SELECT * FROM SENTANCES WHERE ID = :id")
                .param("id", id)
                .query(SentanceModel.class)
                .optional();
    }
    public void connect(SentanceModel sentanceModel, int cardId){
        _jdbc.sql("INSERT INTO CARDS_SENTANCE(SENTANCE_ID, CARD_ID) VALUES(:id, :cardId)")
                .param("cardId",cardId)
                .param("id",sentanceModel.getId())
                .update();
    }
    public List<SentanceModel> getAll() {
        return _jdbc.sql("SELECT * FROM SENTANCES")
                .query(SentanceModel.class)
                .list();
    }
    public int update(SentanceModel sentanceModel){
        _jdbc.sql("UPDATE SENTANCES SET TEXT = :text, TRANSLATE = :translate WHERE ID = :id")
                .param("text",sentanceModel.getText())
                .param("translate", sentanceModel.getTranslate())
                .param("id", sentanceModel.getId())
                .update();

        return sentanceModel.getId();
    }
    public int selectOne(SentanceModel sentanceModel) {
        var count = _jdbc.sql("SELECT COUNT(*) FROM SENTANCES WHERE ID = :id")
                .param("id", sentanceModel.getId())
                .query(Integer.class)
                .single();
        return count;
    }
}
