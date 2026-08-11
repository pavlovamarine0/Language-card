package com.maryna.LanguageCard.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ThemaRepository {
    private final JdbcClient _jdbcClient;
    public ThemaRepository(JdbcClient jdbc){
        _jdbcClient = jdbc;
    }

    public Boolean exists(int themaId){
        var count = _jdbcClient.sql("SELECT COUNT(*) FROM themas WHERE ID = :id")
                .param("id", themaId)
                .query(Integer.class)
                .single();
        return count == 1;
    }

}
