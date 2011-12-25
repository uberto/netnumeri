package com.netnumeri.shared.finance.beans;

public interface FinConstants {
    double ts_empty = Double.NaN;

    int FUNDAMENTAL = 0;
    int QUANTITATIVE = 1;
    int TECHNICAL = 2;
    int SHORT = 3;
    int LONG = 4;

    // ETradeTransaction
    int SELLSHORT = 5;
    int SELL = 6;
    int HOLD = 7;
    int BUY = 8;
    int BUYSHORT = 9;

    // EOrderOption
    int MARKET = 10;
    int LIMIT = 11;
    int STOP = 12;
    int STOPLIMIT = 13;
    int MARKETONOPEN = 14;
    int MARKETONCLOSE = 15;

    int kNewOrder = 16;
    int kTransmitted = 17;
    int kAcknowledged = 18;
    int kAccepted = 19;
    int kPartiallyFilled = 20;
    int kFilled = 21;
    int kCancelled = 22;
    int kRejected = 23;

    int kDayOrder = 24;
    int kEXT = 25;                // extended hours
    int kGTT = 26;                // good till time
    int kGTS = 27;                // good till second
    int kGTC = 28;                // good till cancelled
    int kGTX = 29;                // good till crossing session
    int kFOK = 30;                 // padright or kill

    // EInvestOption
    int kInvestOnDate = 31;
    int kInvestOnBuy = 32;

    // ETickBase
    static final long MILLISECONDS_DAY = 0x5265c00L;
    static final long SECONDS_DAY = 60 * 60 * 24;
    int SECOND = 1;
    int MINUTE = 60;
    int HOUR = 60 * 60;
    int DAY = 60 * 60 * 24;
    int WEEK = 60 * 60 * 24 * 7;
    int MONTH = 60 * 60 * 24 * 7 * 4;
    int YEAR = 60 * 60 * 24 * 7 * 4 * 12;

    int ALL = 35;
    int OBJECT = 36;
    int DATA = 37;
    int DAILY = 38;
    int TICK = 39;
    int TRADE = 40;
    int QUOTE = 41;
    int BAR = 42;

    // EDataOption
    int PRICE = 43;
    int TRADEPRICE = 44;
    int MEDIANPRICE = 45;
    int TYPICALPRICE = 46;
    int WEIGHTEDPRICE = 47;
    int AVERAGEPRICE = 48;
    int LOGAVERAGEPRICE = 348;
    int RETURN = 49;
    int LOGRETURN = 50;
    int VOLUMERETURN = 51;
    int VOLUMERETURNLOG = 52;
    int HIGH = 53;
    int LOW = 54;
    int OPEN = 55;
    int CLOSE = 56;
    int VOLUME = 57;
    //    int kBid = 58;
    //    int kAsk = 59;
    int SPREAD = 60;
    int SIZE = 61;
    int BIDSIZE = 62;
    int ASKSIZE = 63;
    int TRADESIZE = 64;

    int kDailyExact = 65;
    int kDailyPercent = 66;
    int kAnnualExact = 67;
    int kAnnualPercent = 68;

    int kVObject = 69;
    int kInstrument = 70;
    int kAsset = 71;
    int kStock = 72;
    int kBond = 73;
    int kFund = 74;
    int kIndex = 75;
    int kFixedIncome = 76;
    int kBill = 77;
    int kNote = 78;
    int kPortfolio = 79;
    int kDerivative = 80;
    int kFutures = 81;
    int kOption = 82;
    int kCurrency = 83;
    int kRate = 84;
    int kCompany = 85;
    int kCalendar = 86;
    int kExchange = 87;
    int kBankAccount = 287;

    // EBondType
    int kConvertibleBond = 88;
    int kPutableBond = 89;
    int kCallableBond = 90;
    int kZeroCouponBond = 91;


    // EOptionType
    int kCall = 92;
    int kPut = 93;
    int kEuropean = 94;
    int kEuropeanPut = 95;
    int kAmerican = 96;
    int kAmericanPut = 97;
    int kBermudian = 98;
    int kDigital = 99;

    // EOptionPosition
    int kInTheMoney = 100;
    int kAtTheMoney = 101;
    int kOutOfTheMoney = 102;

    // EOptionPrice
    int kBinomial = 103;
    int kMonteCarlo = 104;
    int kMonteCarlo1 = 105;
    int kMonteCarlo2 = 106;
    int kBlackScholes = 107;

    // EPortfolioOption
    int kFixedWeight = 108;
    int kFixedAmount = 109;
    int kFixedWealth = 110;

    // ESortOption
    int kUnsorted = 111;
    int kSortByName = 112;
    int kSortByAmount = 113;
    int kSortByReturn = 114;
    int kSortByVolatility = 115;
    int kSortByWeight = 116;
    int kSortByPosition = 117;
    int kSortByInstrument = 118;

    // ETickerOption
    int kDefault = 119;
    int kBloomberg = 120;
    int kReuters = 121;
    int kBridge = 122;
    int kNYSE = 123;
    int kISIN = 124;
    int kCUSIP = 125;
    int kSEDOL = 126;
    int kSICOVAM = 127;
    int kYahoo = 128;
    int kTlx = 129;

    // EMonitorOption
    int kPlayBack = 130;
    int kMaxSpeed = 131;
    int kRealTime = 132;
    int kMonitorId = 133;
    int kMonitorRequestId = 134;
    int kHeaderRequestId = 135;

    // ESpreadOption
    int kBidAsk = 136;
    int kAskAsk = 137;
    int kBidBid = 138;

    int kCompact = 140;
    int kLarge = 141;

    int kGetFirstIndex = 142;

    int kGetLastIndex = 143;
    int kGetFirstDailyDate = 144;
    int kGetLastDailyDate = 145;
    int kGetIndexByDate = 146;
    int kGetDailyByDate = 147;
    int kGetDailyByIndex = 148;
    int kGetSeries = 149;
    int kGetHighSeries = 150;
    int kGetLowSeries = 151;
    int kGetOpenSeries = 152;
    int kGetCloseSeries = 153;
    int kGetVolumeSeries = 154;
    int kGetDailySeries = 155;
    int kGetQuoteSeries = 156;
    int kGetTradeSeries = 157;
    int kGetDailyArray = 158;
    int kGetQuoteArray = 159;
    int kGetTradeArray = 160;

    int VALID = 161;
    int NOTAVAILABLE = 162;
    int INTERPOLATED = 163;

    int um_Exact = 164;
    int um_OnLoad = 165;
    int um_Minute = 166;
    int um_Hour = 167;
    int um_Day = 168;

    int kPercentDividend = 169;
    int kCashDividend = 170;
    int kContinuousDividend = 171;
    int kGrowthRateDividend = 172;

    int kInt = 173;
    int kFloat = 174;

    int kOptimizer = 175;
    int kCoordinateDescent = 176;
    int kGeneticAlgorithm = 177;
    int kSimulatedAnnealing = 178;

    int kEfficientFrontier = 180;
    int kPerceptron = 181;

    int kQuiet = 182;
    int kVerbose = 183;
    int kDebug = 184;
    int kOverwrite = 185;

    int kPercent = 186;
    int kFixed = 187;
    int kMixed = 188;
    int kComplete = 189;

    int kObjectiveCalls = 190;
    int kErrorTolerance = 191;
    int kStopTemperature = 192;
    int kAcceptedUphills = 193;
    int kAcceptedDownhills = 194;

    int kNormalGraph = 195;
    int kTitleGraph = 196;
    int kIndicatorGraph = 197;
    int kTrendGraph = 198;
    int kVolumeGraph = 199;
    int kOscillatorGraph = 200;
    int kSignalGraph = 201;
    int kPortfolioGraph = 202;

    int kNoRange = 195;
    int kDateRange = 196;
    int kDayRange = 197;

    int Jan = 1;
    int Feb = 2;
    int Mar = 3;
    int Apr = 4;
    int May = 5;
    int Jun = 6;
    int Jul = 7;
    int Aug = 8;
    int Sep = 9;
    int Oct = 10;
    int Nov = 11;
    int Dec = 12;

    int Mon = 1;
    int Tue = 2;
    int Wed = 3;
    int Thu = 4;
    int Fri = 5;
    int Sat = 6;
    int Sun = 7;
    int reformYear = 1582;
    int reformDayNumber = 577737;
    int reformmonth = Oct;
    int beginDSTDay = Sun;
    int beginDSTmonth = Apr;
    int endDSTDay = Sun;
    int endDSTmonth = Oct;

    int PAYOFF_EUROPEAN_CALL = 0;
    int PAYOFF_EUROPEAN_PUT = 1;
    int PAYOFF_ARITHMETRIC_AVERAGE = 2;
    int PAYOFF_GEOMETRIC_AVERAGE = 3;
    int PAYOFF_MAX = 4;
    int PAYOFF_MIN = 5;

    int PRECEDING = 0;
    int FOLLOWING = 1;
    int MODPRECEDING = 2;
    int MODFOLLOWING = 3;
    int NOROLL = 4;


    int TREND = 29;
    int kEnvelope = 30;
    int OSCILLATOR = 31;
    int kVolumeIndicator = 32;

    int BID = 0;
    int ASK = 1;
    int MID = 2;

    int ACT_365 = 0;
    int ACT_360 = 1;
    int E30_360 = 2;
    int B30_365 = 3;
    int B30_360 = 4;
    int ACT_ACT = 5;
    int P30_360 = 6;

    int DOWN_AND_OUT = 0;
    int DOWN_AND_IN = 1;
    int UP_AND_OUT = 2;
    int UP_AND_IN = 3;
    int ARREARS_PAY_MODE = 0;
    int ADVANCE_PAY_MODE = 1;
    int ADV_DISC_PAY_MODE = 2;

    int FIXED = 0;
    int FLOAT = 1;
    int REVERSE_FLOAT = 2;

    int GENERATE_FROM_START = 0;
    int GENERATE_FROM_END = 1;
    int GENERATE_FRN = 2;


    // options
    int nUndType_Stock = 1;
    int nUndType_Forex = 2;

    int nOptType_None = 0;
    int nOptType_Call = 1;

    int nOptType_Put = 2;
    int nOptType_Fut = 3;
    int nOptType_Und = 4;

    int nOptStyle_None = 0;
    int nOptStyle_Europ = 1;
    int nOptStyle_Amer = 2;

    String szOptType_Call = "Call";
    String szOptType_Put = "Put";
    String szOptType_Fut = "Fut";
    String szOptType_Und = "Und";
    String szOptType_None = "None";
    String szOptStyle_Europ = "European";
    String szOptStyle_Amer = "American";

    int nGraphType_Price = 1;
    int nGraphType_PL = 2;
    int nGraphType_Delta = 3;
    int nGraphType_Gamma = 4;
    int nGraphType_Theta = 5;
    int nGraphType_Vega = 6;
    int nGraphType_Rho = 7;

    double ACCURACY = 1.0e-6;
    double PI = 3.141592653589793238462643;
    double ERROR = -1e30;

    int BETA_TURNING_POINT = 2;
    int GAMMA_TURNING_POINT = 7;
    double GEOMETRIC_TURNING_POINT = 0.38;

    int YCASK = 0;
    int YCMID = 1;
    int YCBID = 2;

    int numDR = 4;

    int MONTHS = 12;
    int MILLISECONDS = 1000;
    int SECONDS = 60;
    int MINUTES = 60;
    int HOURS = 24;

    long MILLS_DAY = 0x5265c00;

    int NO_MOVE = 0;
    int PARALLEL = 1;
    int PROPORTIONAL = 2;
    int TWIST = 3;
    int BRIDGE = 4;
    int INVERSE = 5;

    int MonthOffset[] = {
            1, 32, 60, 91, 121, 152, 182, 213, 244, 274,
            305, 335
    };

    int MonthDays[] = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31
    };

    int FR_DAILY = 0;
    int FR_WEEKLY = 1;
    int FR_2WEEK = 2;
    int FR_MONTHLY = 3;
    int FR_2MONTHS = 4;
    int FR_QUART = 5;
    int FR_4MONTHS = 6;
    int FR_SEMI = 7;
    int FR_9MONTHS = 8;
    int FR_ANNUAL = 9;
    int Frequences[] = {
            1, 7, 14, 1, 2, 3, 4, 6, 9, 12
    };

    String DayNames[] = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    String MonthNames[] = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"
    };

    String DayNamesShort[] = {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    };

    String MonthNamesShort[] = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec"
    };

    // different year lengths per different conventions
    int[] YearLengths = {365, 360, 360, 365, 360, 365, 360};

    String _ALL = "TUTTI";

    String EUROPEAN_CALL = "European Call";
    String EUROPEAN_PUT = "European Put";
    String AMERICAN_CALL_BS = "American Call (BS)";
    String AMERICAN_PUT_BS = "American Put (BS)";
    String AMERICAN_CALL_BAW = "American Call (BAW)";
    String AMERICAN_PUT_BAW = "American Put (BAW)";
    String Down_Knock_in_call = "Down Knock-in call";
    String Up_Knock_in_call = "Up Knock-in call";
    String Down_Knock_in_put = "Down Knock-in put";
    String Up_Knock_in_put = "Up Knock-in put";
    String Down_Knock_out_call = "Down Knock-out call";
    String Up_Knock_out_call = "Up Knock-out call";
    String Down_Knock_out_put = "Down Knock-out put";
    String Up_Knock_out_put = "Up Knock-out put";

    int BlackScholes = 0;
    int Binomial = 1;
    int Montecarlo1 = 2;
    int Montecarlo2 = 3;


    int kSMA = 0;
    int kWMA = 1;
    int kEMA = 2;
    int kPCU = 3;
    int kPCL = 4;
    int kBBU = 5;
    int kBBL = 6;
    int kVHF = 7;
    int kSMV = 8;
    int kSMD = 9;
    int kSMC = 10;
    int kMOM = 11;
    int kTO = 12;
    int kMACD = 13;
    int kROC = 14;
    int kRSI = 15;
    int kCCI = 16;
    int kKRI = 17;
    int kOSC = 18;
    int kPCR = 19;
    int kFI = 20;
    int kD = 21;
    int kK = 22;
    int kVA = 23;
    int kVAI = 24;
    int kCHO = 25;
    int kTR = 26;      // TR   True Range
    int kATR = 27;     // ATR  Average True Range
    int kUserDefined = 28;
    int kMFI = 29;
    int kAD = 30;
    int kADCHO = 31;
    int kADCHOMF = 32;

    int kAroonIndicator = 33;
    int kAroonIndicatorDown = 34;
    int kAroonIndicatorUp = 35;
    int kTrueRange = 36;
    int kDStochastic = 37;
    int kKStochastic = 38;
    int kDStochasticSmoothed = 39;
    int kCHOVOLATILITY = 40;
    int kMACDSignal = 41;
}