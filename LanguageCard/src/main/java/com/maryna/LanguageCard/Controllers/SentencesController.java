package com.maryna.LanguageCard.Controllers;

import com.maryna.LanguageCard.Models.GetSentencesRequestModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Services.SentenceService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentences")
public class SentencesController {
    private final SentenceService _Sentenceservice;
    public SentencesController(SentenceService Sentenceservice){
        _Sentenceservice = Sentenceservice;
    }
    @GetMapping()
    public List<SentanceModel> getSentences(@RequestParam GetSentencesRequestModel getSentencesRequestModel) {
        return _Sentenceservice.getAll(getSentencesRequestModel);
    }

    @GetMapping("/{id}")
    public SentanceModel getSentence(int id) throws BadRequestException {
        return _Sentenceservice.getById(id);
    }

    @PostMapping()
    public SentanceModel createSentence(@RequestBody SentanceModel sentanceModel)
            throws BadRequestException {
        return _Sentenceservice.create(sentanceModel);
    }

    @PutMapping()
    public SentanceModel updateSentence(@RequestBody SentanceModel sentanceModel) throws BadRequestException {
        return _Sentenceservice.update(sentanceModel);
    }

    @DeleteMapping("/{id}")
    public void deleteSentence(int id) {
        _Sentenceservice.delete(id);
    }
}
