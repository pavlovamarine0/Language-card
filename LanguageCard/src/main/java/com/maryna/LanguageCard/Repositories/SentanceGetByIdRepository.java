package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Models.ThemaModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SentanceGetByIdRepository {
    public final JdbcClient _jdbc;
    public SentanceGetByIdRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public Optional<SentanceModel> getById(int id){
        return _jdbc.sql("SELECT * FROM SENTANCES WHERE ID = :id")
                .param("id", id)
                .query(SentanceModel.class)
                .optional();
    }
}
