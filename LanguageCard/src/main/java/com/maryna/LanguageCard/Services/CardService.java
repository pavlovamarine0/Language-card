package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Repositories.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;
@Service
public class CardService {
    private final CardRepository _cardRepository;
    private final ThemaRepository _themaRepository;
    private final ThemaCardRepository _themaCardRepository;
    private final CardSentanceRepository _cardSentanceRepository;
    private final SentanceRepository _sentenceRepository;
    public CardService(CardRepository cardRepository, ThemaRepository themaRepository, ThemaCardRepository themaCard,
                       CardSentanceRepository cardSentanceRepository, SentanceRepository sentenceRepository) {
        _cardRepository = cardRepository;
        _themaRepository = themaRepository;
        _themaCardRepository = themaCard;
        _cardSentanceRepository = cardSentanceRepository;
        _sentenceRepository = sentenceRepository;
    }

    public List<CardModel> getAll(int themaId) {
        return _cardRepository.getAll(themaId);
    }
    public  List<CardModel> findThemaIdsIsNull(){
        return  _cardRepository.findThemaIdsIsNull();
    }

    public CardModel getById(int id)throws BadRequestException {
        var card = _cardRepository.getById(id);
        if(card.isEmpty()){
            throw new BadRequestException("There is no such a card!");
        }
        var cardModel = card.get();
        cardModel.setThemaIds(new LinkedList<>(_themaCardRepository.getThemaIds(id)));
        cardModel.setSentenceIds(_cardSentanceRepository.getSentenceIds(id));
        return cardModel;
    }

    @Transactional()
    public CardModel create(CardModel cardModel)throws BadRequestException {
        for (var themaId : cardModel.getThemaIds()) {
            if (!_themaRepository.exists(themaId)) {
                throw new BadRequestException("There is no such a theme!");
            }
        }
        cardModel = _cardRepository.create(cardModel);
        for (var themaId : cardModel.getThemaIds()) {
            _themaCardRepository.bind(cardModel.getId(), themaId);
        }
        if (cardModel.getSentences() != null) {
            for (var sentense : cardModel.getSentences()) {
                var savedSentences = _sentenceRepository.create(sentense);
                _cardSentanceRepository.bind(savedSentences.getId(), cardModel.getId());
            }
        }
        return cardModel;
    }
    @Transactional()
    public CardModel update(CardModel cardModel)throws BadRequestException{
        for (var themaId : cardModel.getThemaIds()) {
            if (!_themaRepository.exists(themaId)) {
                throw new BadRequestException("There is no such a theme!");
            }
        }
        if(!_cardRepository.exists(cardModel.getId())){
            throw new BadRequestException("There is no such a card!");
        }
        var cardId = _cardRepository.update(cardModel);
        _themaCardRepository.unbind(cardId);
        _cardSentanceRepository.unbind(cardId);
        for(var themaId : cardModel.getThemaIds()){
            _themaCardRepository.bind(cardId, themaId);
        }
        for(var sentenceId : cardModel.getSentenceIds()){
            _cardSentanceRepository.bind(sentenceId, cardId);
        }
        return getById(cardId);
    }

    public void delete(int id)throws BadRequestException{
        if(_cardSentanceRepository.existsByCard(id)){
            throw new BadRequestException("This card has saved sentences!");
        }
        _cardRepository.delete(id);
    }
}



