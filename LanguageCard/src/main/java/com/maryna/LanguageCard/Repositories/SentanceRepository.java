package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SentanceCreateRepository {
    private final JdbcClient _jdbc;
    public SentanceCreateRepository(JdbcClient jdbcClient){
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
}
