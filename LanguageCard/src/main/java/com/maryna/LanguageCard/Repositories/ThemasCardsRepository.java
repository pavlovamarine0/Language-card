package com.maryna.LanguageCard.Repositories;


import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.ThemaModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ThemasCardsRepository {
    private final JdbcClient _jdbc;
    public ThemasCardsRepository(JdbcClient jdbcClient){
        _jdbc = jdbcClient;
    }
    public void connect(CardModel cardModel, int themaId){
        _jdbc.sql("INSERT INTO THEMAS_CARDS(THEMA_ID, CARD_ID) VALUES(:themaId, :id)")
                .param("themaId",themaId)
                .param("id",cardModel.getId())
                .update();
    }
}
