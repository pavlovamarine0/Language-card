package com.maryna.LanguageCard.Repositories;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class DeleteCardRepository {
    private final JdbcClient _jdbc;
    public DeleteCardRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROM CARDS WHERE ID = :id")
                .param("id", id)
                .update();
    }
}
