package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.ThemaModel;
import com.maryna.LanguageCard.Repositories.ThemaCardRepository;
import com.maryna.LanguageCard.Repositories.ThemaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThemaService {
    private final ThemaRepository _themaRepository;
    private  final ThemaCardRepository _themaCardRepository;
    public ThemaService(ThemaRepository themaRepository, ThemaCardRepository themaCardRepository) {
        _themaRepository = themaRepository;
        _themaCardRepository = themaCardRepository;
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

    public ThemaModel create(String name) {
        var thema = _themaRepository.create(name);
        return thema;
    }

    public ThemaModel update(ThemaModel themaModel)throws BadRequestException{
        var count = _themaRepository.select(themaModel);
        if(count == 0){
            throw new BadRequestException("There is no such a theme!");
        }
        _themaRepository.update(themaModel);
        return getById(themaModel.getId());
    }

    public void delete(int id)throws BadRequestException{
        if(_themaCardRepository.exists(id)){
            throw new BadRequestException("This theme has cards!");
        }
        _themaRepository.delete(id);
    }
}
