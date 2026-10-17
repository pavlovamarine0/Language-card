package com.maryna.LanguageCard.Repositories;

import com.maryna.LanguageCard.Models.ThemaModel;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemaRepository {
    private final JdbcClient _jdbc;
    public ThemaRepository(JdbcClient jdbc){
        _jdbc = jdbc;
    }

    public Boolean exists(int themaId){
        var count = _jdbc.sql("SELECT COUNT(*) FROM themes WHERE ID = :id")
                .param("id", themaId)
                .query(Integer.class)
                .single();
        return count == 1;
    }
    public List<ThemaModel> getAll() {
        return _jdbc.sql("SELECT * FROM THEMES")
                .query(ThemaModel.class)
                .list();
    }
    public Optional<ThemaModel> getById(int id) {
        return _jdbc.sql("SELECT * FROM THEMES WHERE ID = :id")
                .param("id", id)
                .query(ThemaModel.class)
                .optional();
    }
    public ThemaModel create(String name) {
        var keyHolder = new GeneratedKeyHolder();
        _jdbc.sql("INSERT INTO THEMES(NAME) VALUES(:name) returning id")
                .param("name",name)
                .update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        ThemaModel themaModel = new ThemaModel();
        themaModel.setId(id);
        themaModel.setName(name);
        return themaModel;
    }
    public ThemaModel update(ThemaModel themaModel){
        _jdbc.sql("UPDATE THEMES SET NAME = :name WHERE ID = :id")
                .param("name", themaModel.getName())
                .param("id", themaModel.getId())
                .update();
        return themaModel;
    }
    public int select(ThemaModel themaModel){
        var count = _jdbc.sql("SELECT COUNT(*) FROM themes WHERE ID = :id")
                .param("id", themaModel.getId())
                .query(Integer.class)
                .single();
        return count;
    }
    public void delete(int id){
        _jdbc.sql("DELETE FROM THEMES WHERE ID = :id")
                .param("id", id)
                .update();
    }

}
