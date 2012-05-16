package com.netnumeri.server.utils;

public enum StrategyEnum {

    BearCallSpread (new OptionStrategy("Bear Call Spread")),
    BearPutSpread(new OptionStrategy(" Bear Put Spread")),
    BoxSpreadLongBox(new OptionStrategy("")),
    BullCallSpread(new OptionStrategy("")),
    BullPutSpread(new OptionStrategy("")),
    ButterflySpread(new OptionStrategy("")),
    BuyingIndexCalls(new OptionStrategy("")),
    BuyingIndexPuts(new OptionStrategy("")),
    CalendarStraddle(new OptionStrategy("")),
    CallBackSpread(new OptionStrategy("")),
    CondorOptions(new OptionStrategy("")),
    Conversion(new OptionStrategy("")),
    CostlessCollar(new OptionStrategy("")),
    CoveredCalls(new OptionStrategy("")),
    CoveredCombination(new OptionStrategy("")),
    CoveredPut(new OptionStrategy("")),
    CoveredStraddle(new OptionStrategy("")),
    InTheMoneyCoveredCall(new OptionStrategy("")),
    InTheMoneyNakedCall(new OptionStrategy("")),
    IronButterfly(new OptionStrategy("")),
    IronCondors(new OptionStrategy("")),
    LongCall(new OptionStrategy("")),
    LongCallLadder(new OptionStrategy("")),
    LongCallSyntheticStraddle(new OptionStrategy("")),
    LongGuts(new OptionStrategy("")),
    LongPut(new OptionStrategy("")),
    PutButterfly(new OptionStrategy("")),
    LongPutLadder(new OptionStrategy("")),
    LongPutSyntheticStraddle(new OptionStrategy("")),
    MarriedPut(new OptionStrategy("")),
    NeutralCalendarSpread(new OptionStrategy("")),
    LongStraddle(new OptionStrategy("")),
    LongStrangle(new OptionStrategy("")),
    OutOfTheMoneyNakedCall(new OptionStrategy("")),
    PortfolioHedgingSingIndexOptions(new OptionStrategy("")),
    ProtectiveCall(new OptionStrategy("")),
    ProtectivePut(new OptionStrategy("")),
    PutBackSpread(new OptionStrategy("")),
    PutRatioSpread(new OptionStrategy("")),
    RatioCallWrite(new OptionStrategy("")),
    RatioPutWrite(new OptionStrategy("")),
    RatioSpread(new OptionStrategy("")),
    Reversal(new OptionStrategy("")),
    ReverseIronButterfly(new OptionStrategy("")),
    ReverseIronCondor(new OptionStrategy("")),
    SellingIndexCalls(new OptionStrategy("")),
    SellingIndexPuts(new OptionStrategy("")),
    ShortBox(new OptionStrategy("")),
    ShortButterfly(new OptionStrategy("")),
    ShortCallLadder(new OptionStrategy("")),
    ShortCallSyntheticStraddle(new OptionStrategy("")),
    ShortCondor(new OptionStrategy("")),
    ShortGuts(new OptionStrategy("")),
    ShortPutButterfly(new OptionStrategy("")),
    ShortPutLadder(new OptionStrategy("")),
    ShortPutSyntheticStraddle(new OptionStrategy("")),
    ShortStraddleSellStraddle(new OptionStrategy("")),
    ShortStrangleSellStrangle(new OptionStrategy("")),
    StockRepairStrategy(new OptionStrategy("")),
    Strap(new OptionStrategy("")),
    Strip(new OptionStrategy("")),
    SyntheticLongCall(new OptionStrategy("")),
    SyntheticLongPut(new OptionStrategy("")),
    SyntheticLongStock(new OptionStrategy("")),
    SyntheticLongStockSplitStrikes(new OptionStrategy("")),
    SyntheticShortCall(new OptionStrategy("")),
    SyntheticShortPut(new OptionStrategy("")),
    SyntheticShortStock(new OptionStrategy("")),
    SyntheticShortStockSplitStrikes(new OptionStrategy("")),
    TheCollarStrategy(new OptionStrategy("")),
    UncoveredPutWrite(new OptionStrategy("")),
    VariableRatioWrite(new OptionStrategy(""))

    private OptionStrategy strategy;

    private StrategyEnum(OptionStrategy strategy) {
        this.strategy = strategy;
    }

    public String getDisplayString() {
        return name();
    }

}
