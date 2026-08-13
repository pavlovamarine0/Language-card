package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.ThemaModel;
import com.maryna.LanguageCard.Repositories.ThemaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class ThemaService {
    private final ThemaRepository _themaRepository;
    public ThemaService(ThemaRepository themaRepository) {
        _themaRepository = themaRepository;
    }
    public List<ThemaModel> getAll() {
        return _themaRepository.getAll();
    }

    public ThemaModel getById(int id)throws BadRequestException {
        var thema = _themaRepository.getById(id);
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
        var thema = _themaRepository.create(name);
        return thema;
    }

    public ThemaModel update(@RequestBody ThemaModel themaModel)throws BadRequestException{
        var count = _themaRepository.select(themaModel);
        if(count == 0){
            throw new BadRequestException("There is no such a theme!");
        }
        _themaRepository.update(themaModel);
        return getById(themaModel.getId());
    }

    public void delete(int id){
        _themaRepository.delete(id);
    }
}
