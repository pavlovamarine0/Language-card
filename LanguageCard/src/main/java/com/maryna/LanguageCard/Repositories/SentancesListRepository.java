package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.SentanceModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentancesListRepository {
    public final JdbcClient _jdbc;
    public SentancesListRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public List<SentanceModel> getAll() {
        return _jdbc.sql("SELECT * FROM SENTANCES")
                .query(SentanceModel.class)
                .list();
    }
}
