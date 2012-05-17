package com.netnumeri.server.utils;

public enum StrategyEnum {

    BearCallSpread ,
    BearPutSpread,
    BoxSpreadLongBox,
    BullCallSpread,
    BullPutSpread,
    ButterflySpread,
    BuyingIndexCalls,
    BuyingIndexPuts,
    CalendarStraddle,
    CallBackSpread,
    CondorOptions,
    Conversion,
    CostlessCollar,
    CoveredCalls,
    CoveredCombination,
    CoveredPut,
    CoveredStraddle,
    InTheMoneyCoveredCall,
    InTheMoneyNakedCall,
    IronButterfly,
    IronCondors,
    LongCall,
    LongCallLadder,
    LongCallSyntheticStraddle,
    LongGuts,
    LongPut,
    PutButterfly,
    LongPutLadder,
    LongPutSyntheticStraddle,
    MarriedPut,
    NeutralCalendarSpread,
    LongStraddle,
    LongStrangle,
    OutOfTheMoneyNakedCall,
    PortfolioHedgingSingIndexOptions,
    ProtectiveCall,
    ProtectivePut,
    PutBackSpread,
    PutRatioSpread,
    RatioCallWrite,
    RatioPutWrite,
    RatioSpread,
    Reversal,
    ReverseIronButterfly,
    ReverseIronCondor,
    SellingIndexCalls,
    SellingIndexPuts,
    ShortBox,
    ShortButterfly,
    ShortCallLadder,
    ShortCallSyntheticStraddle,
    ShortCondor,
    ShortGuts,
    ShortPutButterfly,
    ShortPutLadder,
    ShortPutSyntheticStraddle,
    ShortStraddleSellStraddle,
    ShortStrangleSellStrangle,
    StockRepairStrategy,
    Strap,
    Strip,
    SyntheticLongCall,
    SyntheticLongPut,
    SyntheticLongStock,
    SyntheticLongStockSplitStrikes,
    SyntheticShortCall,
    SyntheticShortPut,
    SyntheticShortStock,
    SyntheticShortStockSplitStrikes,
    TheCollarStrategy,
    UncoveredPutWrite,
    VariableRatioWrite;

    public String getDisplayString() {
        return name();
    }

}
