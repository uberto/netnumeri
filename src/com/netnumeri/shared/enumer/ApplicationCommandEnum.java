package com.netnumeri.shared.enumer;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum ApplicationCommandEnum implements IsSerializable {
    CommandLogin,
    CommandGetBusinessAccount, CommandSaveBusinessAccount,

    CommandGetTradingAgents,
    CommandDeleteTradingAgents,
    CommandGetTradingAgent,
    CommandSaveTradingAgent,

    CommandDeletePortfolios,
    CommandGetPortfolio,
    CommandGetPortfolios,
    CommandSavePortfolio,
    CommandDeleteStocks,
    CommandGetStock,
    CommandSaveStock,
    CommandGetStocks
}
