package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.ThemaModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class ThemaService {
    private final JdbcClient _jdbc;
    public ThemaService(JdbcClient jdbc) {
        _jdbc = jdbc;
    }
    public List<ThemaModel> getAll() {
        return _jdbc.sql("SELECT * FROM THEMAS")
                .query(ThemaModel.class)
                .list();
    }

    public ThemaModel getById(int id)throws BadRequestException {
        var thema = _jdbc.sql("SELECT * FROM THEMAS WHERE ID = :id")
                .param("id", id)
                .query(ThemaModel.class)
                .optional();
        if(thema.isEmpty()){
            throw new BadRequestException("There is no such a theme!");
        }
        return thema.get();
    }

    public ThemaModel create(@RequestParam String name) {
        /// #1
        //int id = _jdbc.sql("INSERT INTO THEMAS(NAME) VALUES('"+name+"') returning id").query(int.class).single();
        //Thema thema = new Thema();
        //thema.setId(id);
        //thema.setName(name);
        //return thema;
        /// #2
        var keyHolder = new GeneratedKeyHolder();
        _jdbc.sql("INSERT INTO THEMAS(NAME) VALUES(:name) returning id").param("name",name).update(keyHolder);
        var id = ((Number)keyHolder.getKeys().get("id")).intValue();
        ThemaModel themaModel = new ThemaModel();
        themaModel.setId(id);
        themaModel.setName(name);
        return themaModel;
    }

    public ThemaModel update(@RequestBody ThemaModel themaModel)throws BadRequestException{
        var count = _jdbc.sql("SELECT COUNT(*) FROM themas WHERE ID = :id")
                .param("id", themaModel.getId())
                .query(Integer.class)
                .single();
        if(count == 0){
            throw new BadRequestException("There is no such a theme!");
        }
        _jdbc.sql("UPDATE THEMAS SET NAME = :name WHERE ID = :id")
                .param("name", themaModel.getName())
                .param("id", themaModel.getId())
                .update();

        return getById(themaModel.getId());
    }

    public void delete(int id){
        _jdbc.sql("DELETE FROM THEMAS WHERE ID = :id")
                .param("id", id)
                .update();
    }
}
