package com.maryna.LanguageCard.Controllers;

import com.maryna.LanguageCard.Models.ThemaModel;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themas")
public class ThemasController {
    private final JdbcClient _jdbc;

    public ThemasController(JdbcClient jdbc) {
        _jdbc = jdbc;
    }

    @GetMapping()
    public List<ThemaModel> getThemas() {
        return _jdbc.sql("SELECT * FROM THEMAS")
                .query(ThemaModel.class)
                .list();
    }
    @GetMapping("/{id}")
    public ThemaModel getThema(int id)throws BadRequestException{
        var thema = _jdbc.sql("SELECT * FROM THEMAS WHERE ID = :id")
                .param("id", id)
                .query(ThemaModel.class)
                .optional();
        if(thema.isEmpty()){
            throw new BadRequestException("There is no such a theme!");
        }
        return thema.get();
    }

    @PostMapping()
    public ThemaModel createThema(@RequestParam String name) {
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

    @PutMapping()
    public ThemaModel updateThema(@RequestBody ThemaModel themaModel)throws BadRequestException{
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

        return getThema(themaModel.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteThema(int id){
        _jdbc.sql("DELETE FROM THEMAS WHERE ID = :id")
                .param("id", id)
                .update();
    }
}
