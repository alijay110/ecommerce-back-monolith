package pl.cba.gibcode.alabackend.transaction.mapper;

import pl.cba.gibcode.alabackend.card.mapper.CardMapper;
import pl.cba.gibcode.alabackend.card.model.Card;
import pl.cba.gibcode.alabackend.transaction.model.SummaryDto;
import pl.cba.gibcode.alabackend.transaction.model.Transaction;

public class SummaryMapper {

	public static SummaryDto mapWithoutCardNumber(Transaction transaction){
		return new SummaryDto("", transaction.getId(), transaction.getStatus(), CardMapper.map(transaction.getCard()));
	}

	public static SummaryDto mapWithCardNumber(Transaction transaction){
		return new SummaryDto(transaction.getCard().getCardNumber(), transaction.getId(), transaction.getStatus(), CardMapper.map(transaction.getCard()));
	}
}
