package com.zrmiller.slimtrade;

import java.io.Serializable;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	MessageType msgType;
	String playerName;
	String itemName;
	Double itemCount;
	CurrencyType priceType;
	Double currencyCount;
	
	public TradeOffer(MessageType msgType, String playerName, String itemName, Double itemCount, CurrencyType priceType, Double currencyCount){
		this.msgType = msgType;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceType = priceType;
		this.currencyCount = currencyCount;
	}
	
	
}