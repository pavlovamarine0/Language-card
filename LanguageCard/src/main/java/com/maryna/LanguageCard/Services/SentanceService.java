package com.maryna.LanguageCard.Services;

import com.maryna.LanguageCard.Models.CardModel;
import com.maryna.LanguageCard.Models.SentanceModel;
import com.maryna.LanguageCard.Models.ThemaModel;
import com.maryna.LanguageCard.Repositories.*;
import org.apache.coyote.BadRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SentanceService {
    private final JdbcClient _jdbc;
    private final SentancesListRepository _sentanceList;
    private final SentanceGetByIdRepository _sentancegetById;
    public final SentanceCreateRepository _sentanceCreate;
    public final SentancesCardsRepository _sentanceCard;
    public final CardExistsRepository _cardIdExists;
    public final SentanceDeleteRepository _sentanceDelete;
    public final SentanceUpdateRepository _sentanceUpdate;
    public final SingleSentanceRepository _singleSentance;

    public SentanceService(JdbcClient jdbcClient, SentancesListRepository sentanceList, SentanceGetByIdRepository
                                   sentanceId, SentanceCreateRepository sentanceCreate, SentancesCardsRepository
                                   sentanceCard, CardExistsRepository cardIdExists, SentanceDeleteRepository sentanceDelete,
                           SentanceUpdateRepository sentanceUpdate, SingleSentanceRepository singleSentance) {
        _jdbc = jdbcClient;
        _sentanceList = sentanceList;
        _sentancegetById = sentanceId;
        _sentanceCreate = sentanceCreate;
        _sentanceCard = sentanceCard;
        _cardIdExists = cardIdExists;
        _sentanceDelete = sentanceDelete;
        _sentanceUpdate = sentanceUpdate;
        _singleSentance = singleSentance;
    }

    public List<SentanceModel> getAll() {
        return _sentanceList.getAll();
    }

    public SentanceModel getById(int id) throws BadRequestException {
        var sentance = _sentancegetById.getById(id);
        if (sentance.isEmpty()) {
            throw new BadRequestException("There is no such a theme!");
        }
        return sentance.get();
    }

    public SentanceModel create(SentanceModel sentanceModel, int cardId) throws BadRequestException {
        if (!_cardIdExists.exists(cardId)) {
            throw new BadRequestException("There is no such a theme!");
        }
        sentanceModel = _sentanceCreate.create(sentanceModel);
        _sentanceCard.connect(sentanceModel, cardId);
        return sentanceModel;
    }

    public void delete(int id) {
        _sentanceDelete.delete(id);
    }

    public SentanceModel update(SentanceModel sentanceModel) throws BadRequestException {
        var count = _singleSentance.selectOne(sentanceModel);
        if (count == 0) {
            throw new BadRequestException("There is no such a card!");
        }
        var cardId = _sentanceUpdate.update(sentanceModel);
        return getById(cardId);
    }
}

