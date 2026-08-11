package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.ThemaModel;
import com.maryna.LanguageCard.Repositories.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class CardService {
    private final JdbcClient _jdbc;
    private final CardRepository _cardRepository;
    private final ThemaRepository _themaRepository;
    private final DeleteCardRepository _deleteCard;
    private final ListCardRepository _listCard;
    private final GetCardbyIDRepository _getCardById;
    private final ThemasCardsRepository _themasCards;
    private  final SingleCardRepository _singleCard;
    private  final UpdateCardRepository _updateCard;
    public CardService(JdbcClient jdbc, CardRepository cardRepository, ThemaRepository themaRepository,
                       DeleteCardRepository deleteCard, ListCardRepository listCard, GetCardbyIDRepository getCardById,
                       ThemasCardsRepository themasCards, SingleCardRepository singleCard, UpdateCardRepository updateCard) {
        _jdbc = jdbc;
        _cardRepository = cardRepository;
        _themaRepository = themaRepository;
        _deleteCard = deleteCard;
        _listCard = listCard;
        _getCardById = getCardById;
        _themasCards = themasCards;
        _singleCard = singleCard;
        _updateCard = updateCard;
    }

    public List<CardModel> getAll() {
        return _listCard.getAll();
    }

    public CardModel getById(int id)throws BadRequestException {
        var card = _getCardById.getById(id);
        if(card.isEmpty()){
            throw new BadRequestException("There is no such a theme!");
        }
        return card.get();
    }

    @Transactional()
    public CardModel create(CardModel cardModel, int themaId)throws BadRequestException {
        if(!_themaRepository.exists(themaId)){
            throw new BadRequestException("There is no such a theme!");
        }
       cardModel = _cardRepository.create(cardModel);
        _themasCards.connect(cardModel, themaId);
        return cardModel;
    }

    public CardModel update(CardModel cardModel)throws BadRequestException{
        var count = _singleCard.selectOne(cardModel);
        if(count == 0){
            throw new BadRequestException("There is no such a card!");
        }
        var cardId = _updateCard.update(cardModel);
        return getById(cardId);
    }

    public void delete(int id){
        _deleteCard.delete(id);
    }
}



