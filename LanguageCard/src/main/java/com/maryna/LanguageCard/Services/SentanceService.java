package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Repositories.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentanceService {
    public final SentanceRepository _sentanceRepository;
    public final CardRepository _cardRepository;
    public SentanceService(SentanceRepository sentanceCreate,  CardRepository cardRepository) {
        _sentanceRepository = sentanceCreate;
        _cardRepository = cardRepository;
    }

    public List<SentanceModel> getAll() {
        return _sentanceRepository.getAll();
    }

    public SentanceModel getById(int id) throws BadRequestException {
        var sentance = _sentanceRepository.getById(id);
        if (sentance.isEmpty()) {
            throw new BadRequestException("There is no such a theme!");
        }
        return sentance.get();
    }

    public SentanceModel create(SentanceModel sentanceModel, int cardId) throws BadRequestException {
        if (!_cardRepository.exists(cardId)) {
            throw new BadRequestException("There is no such a theme!");
        }
        sentanceModel = _sentanceRepository.create(sentanceModel);
        _sentanceRepository.connect(sentanceModel, cardId);
        return sentanceModel;
    }

    public void delete(int id) {
        _sentanceRepository.delete(id);
    }

    public SentanceModel update(SentanceModel sentanceModel) throws BadRequestException {
        var count = _sentanceRepository.selectOne(sentanceModel);
        if (count == 0) {
            throw new BadRequestException("There is no such a card!");
        }
        var cardId = _sentanceRepository.update(sentanceModel);
        return getById(cardId);
    }
}

