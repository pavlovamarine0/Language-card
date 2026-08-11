package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SingleSentanceRepository {
    private final JdbcClient _jdbc;
    public SingleSentanceRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public int selectOne(SentanceModel sentanceModel) {
        var count = _jdbc.sql("SELECT COUNT(*) FROM SENTANCES WHERE ID = :id")
                .param("id", sentanceModel.getId())
                .query(Integer.class)
                .single();
        return count;
    }
}
