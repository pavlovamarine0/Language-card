package com.maryna.LanguageCard.Controllers;

import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Services.SentanceService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentances")
public class SentancesController {
    private final SentanceService _sentanceService;
    public SentancesController(SentanceService sentanceService){
        _sentanceService = sentanceService;
    }
    @GetMapping()
    public List<SentanceModel> getSentances() {
        return _sentanceService.getAll();
    }

    @GetMapping("/{id}")
    public SentanceModel getSentance(int id) throws BadRequestException {
        return _sentanceService.getById(id);
    }

    @PostMapping()
    public SentanceModel createSentance(@RequestBody SentanceModel sentanceModel, @RequestParam int cardId)
            throws BadRequestException {
        return _sentanceService.create(sentanceModel, cardId);
    }

    @PutMapping()
    public SentanceModel updateSentance(@RequestBody SentanceModel sentanceModel) throws BadRequestException {
        return _sentanceService.update(sentanceModel);
    }

    @DeleteMapping("/{id}")
    public void deleteSentance(int id) {
        _sentanceService.delete(id);
    }
}
