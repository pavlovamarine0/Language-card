package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.GetSentencesRequestModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Repositories.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentanceService {
    private final SentanceRepository _sentanceRepository;
    private final CardRepository _cardRepository;
    private final CardSentanceRepository _cardSentanceRepository;
    public SentanceService(SentanceRepository sentenceCreate,  CardRepository cardRepository, CardSentanceRepository
                           cardSentanceRepository) {
        _sentanceRepository = sentenceCreate;
        _cardRepository = cardRepository;
        _cardSentanceRepository = cardSentanceRepository;
    }

    public List<SentanceModel> getAll(GetSentencesRequestModel request) {
        if(request.cardId() != 0){
            return _sentanceRepository.getAllByCardId(request.cardId());
        }
        if(!request.keyWord().isBlank()) {
            return _sentanceRepository.getAllByKeyWord(request.keyWord());
        }
        return List.of();
    }

    public SentanceModel getById(int id) throws BadRequestException {
        var sentence = _sentanceRepository.getById(id);
        if (sentence.isEmpty()) {
            throw new BadRequestException("There is no such a sentence!");
        }
        return sentence.get();
    }


    @Transactional()
    public SentanceModel create(SentanceModel sentanceModel, int cardId) throws BadRequestException {
        if (!_cardRepository.exists(cardId)) {
            throw new BadRequestException("There is no such a card!");
        }
        sentanceModel = _sentanceRepository.create(sentanceModel);
        return sentanceModel;
    }

    public void delete(int id) {
        _sentanceRepository.delete(id);
    }

    public SentanceModel update(SentanceModel sentanceModel) throws BadRequestException {
        if (!_sentanceRepository.exists(sentanceModel.getId())) {
            throw new BadRequestException("There is no such a sentence!");
        }
        var sentenceId = _sentanceRepository.update(sentanceModel);
        return getById(sentenceId);
    }
}

