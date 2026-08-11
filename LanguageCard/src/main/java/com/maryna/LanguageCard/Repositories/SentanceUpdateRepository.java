package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SentanceUpdateRepository {
    private final JdbcClient _jdbc;
    public SentanceUpdateRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public int update(SentanceModel sentanceModel){
        _jdbc.sql("UPDATE SENTANCES SET TEXT = :text, TRANSLATE = :translate WHERE ID = :id")
                .param("text",sentanceModel.getText())
                .param("translate", sentanceModel.getTranslate())
                .param("id", sentanceModel.getId())
                .update();

        return sentanceModel.getId();
    }
}
