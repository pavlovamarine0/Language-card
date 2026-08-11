package com.maryna.LanguageCard.Controllers;

import com.maryna.LanguageCard.Models.ThemaModel;
import com.maryna.LanguageCard.Services.ThemaService;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themas")
public class ThemasController {
    private final ThemaService _themaService;

    public ThemasController(ThemaService themaService) {
        _themaService = themaService;
    }

    @GetMapping()
    public List<ThemaModel> getThemas() {
        return _themaService.getAll();
    }

    @GetMapping("/{id}")
    public ThemaModel getThema(int id) throws BadRequestException {
        return _themaService.getById(id);
    }

    @PostMapping()
    public ThemaModel createThema(@RequestParam String name) {
        return _themaService.create(name);
    }

    @PutMapping()
    public ThemaModel updateThema(@RequestBody ThemaModel themaModel) throws BadRequestException {
        return _themaService.update(themaModel);
    }

    @DeleteMapping("/{id}")
    public void deleteThema(int id) {
        _themaService.delete(id);
    }
}
