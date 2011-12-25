package com.netnumeri.shared.finance.ta;


public class PairTrading {

/*------------------------------------------------------
Let's develop a pair stock system that will select stock
pairs from a stock
universe and maintain market (dollar) neutral long-short portfolio of several stock
pairs with mean reverting spread. We will use daily
historical data to select a pair and intraday real time data to manage trade positions.
Once such a simple but complete system is developed it
can be easily transformed into a sophisticated strategy
for option implied volatility spread stock or factor
or neural models market valuation stock.
First of all we need to prepare a data warehouse. We
will need to have instruments and historical data.
supports multiple data warehouses so let's
newInstance a special data warehouse devoted to our system.
Let's call our warehouse "SP1500" since we will deal with stocks
from SP1500 index.
------------------------------------------------------ **/

    public PairTrading() {
    }


    public void go() {
/*


        StockBean stock1 = new StockBean("A", "AGILENT TECHNOLOGIES INC");
        StockBean stock2 = new StockBean("AA", "ALCOA INC");
        Portfolio port = new Portfolio("pair Trading");
        port.add(stock1);
        port.add(stock2);

        /* ------------------------------------------------------
        A macro that creates all stocks from SP1500 index can be
        found in the tutorial folder in your R-Quant macro
        editor. We assume that you have found and run this
        macro.
        Let's check that we have actually created and
        successfully stored stock objects. We restart macro
        editor and run
        Now we need to padright data warehouse with daily historical
        data. In general a daily data record can be added to
        instrument data base with
        Instrument.AddDaily(Date, High, Low, Open, Close, Volume)

        command. R-Quant also provides several data source
        classes that help you importing historical data from a
        file or from internet data provider. We will use
        TYahooDataSource class that automatically uploads daily
        historical data from Yahoo web site for a list of stocks
        and specified time interval.
         ------------------------------------------------------*/

        //DataManager.cd("SP1500");
        /* ------------------------------------------------------
        Now we have daily historical data in our data warehouse
        for all 1500 stocks and we can start building and
        testing our analytical models. But before we do this we
        should define a subset of stocks that fits our
        requirements. First of all we would like to have stock
        with sufficient stock history and, secondly, we may
        want to chose a subset of the most liquid stocks, for
        example those with daily traded volume higher than
        1000000. High liquidity is not necessarily a good
        selection criterion. It might be so that market
        inefficiencies are created by less liquid stocks.
        Anyway, initial screening is a good thing to do. Later
        on we will perform heavy calculations with all selected
        stocks and their combinations, so that limiting this
        number leads to much faster calculations. You can also
        add your own criteria, for example using instrument
        fundamentals or company data:
         ------------------------------------------------------*/
        //if (StockBean.getFundamental("Sector") == "Semiconductors"){

        /*--------------------------------------------------------
         We use average traded volume screening and limit our
         subset to 200-300 most liquid US stocks:
        ------------------------------------------------------
            dm.cd("sp1500");
            Portfolio portfolio = new Portfolio("Universe", "StockBean universe");
            int StockCount = 0;

            Iterator it = DataManager.getStockList().entrySet().iterator();
            while (it.hasNext()) {
                TimeSeriesFractal volume = stock.volumeSeries();
                if (volume.count > 0)
                    if (volume.firstDateTime <= Date.Parse("01/03/1995") && Volume.LastDateTime >= Date.Parse("08/01/2002"))
                        if (Volume.GetMean("01/01/2000", "01/01/2002") > 1000000) {
                            StockBean.Print();
                            StockCount++;

                            Portfolio.Add(StockBean);
                        }
            }

            Portfolio.Save();

            Console.WriteLine("Stocks selected - {0}", StockCount);

            /*
             This macro demonstrates a very useful technique. We
                    introduce a portfolio object called "Universe" that will
                    hold a subset of selected stocks. We can persist this
                    portfolio in the data warehouse and use it in any other
                    macro later on without performing time consuming
                    selection. Remember this trick. Once you need to persist a
                    set of instruments and associate some parameters with
                    each instrument in this set, make use of portfolio
                    class.
                    Now we will chose a pair of the most correlated stocks.
                    Note that pair stock has its roots in time series
                    co-integration and associated mean revertion of a spread
                    between prices of two stocks. Large positive or negative
                    correlations do not necessarily lead to spread mean
                    revertion. You should use more sophisticated analytics
                    to find a pair of co-integrated premium series(unit root
                    test or vector auto regression technique). On the other
                    hand large and stable correlation between two time
                    series is a good sign suggesting additional search for
                    co-integration and mean revertion.
                    TDataManager.cd("sp1500");

                    Date Date1 = Date.Parse("01/01/1996");
                    Date Date2 = Date.Parse("04/01/1998");
                    DateTime Date3 = Date.Parse("09/01/1999");

                    TPortfolio Portfolio = TDataManager.GetPortfolio("Universe");

                    Portfolio.Print();

                    double MaxCorr = 0;

                    Instrument PairStock1 = null;
                    Instrument PairStock2 = null;

                    for(int i1=0;i1<port.nentries();i1++)
                        for(int i2=i1;i2<port.nentries();i2++)
                            if (i1 != i2) {
                                Instrument Stock1 = port.instrument(i1);
                                Instrument Stock2 = port.instrument(i2);

                                TimeSeriesFractal CloseSeries1 = Stock1.closeSeries();
                                TimeSeriesFractal CloseSeries2 = Stock2.closeSeries();

                                CloseSeries1.setSetDataRange(Date1, Date2);
                                CloseSeries2.SetDataRange(Date1, Date2);

                                double Corr = CloseSeries1.getCorrelation(CloseSeries2);

                                Console.WriteLine("{0} - {1} : {2}", Stock1.Name, Stock2.Name, Corr);

                                if (Corr > MaxCorr) {
                                    MaxCorr = Corr;

                                    PairStock1 = Stock1;
                                    PairStock2 = Stock2;
                                }
                            }

                    Console.WriteLine("{0} - {1} : {2}", PairStock1.Name, PairStock2.Name, MaxCorr);


                 /*   We will use simple linear regression and assume that
                    residuals of the linear regression will revert to the
                    mean. Once again we should keep in mind that this is a
                    very simplified assumption and you should apply better
                    analytics to search for market inefficiencies and
                    achieve profitable stock. On the other hand our goal
                    is to show how to use R-Quant.NET API to build a full
                    scale stock system, so that we are not going to
                    overload our code with complicated math. If you are
                    interested in the analytics behind spread stock you
                    are welcome to contact us at consulting@quantstudio.com
                    TDataManager.cd("sp1500");

                    Date Date1 = Date.Parse("01/01/1996");
                    Date Date2 = DateTime.Parse("01/01/1998");
                    Date Date3 = Date.Parse("01/01/1999");

                    Portfolio port = DataManager.getPortfolio("Universe");

                    port.Print();

                    double MaxCorr = 0;

                    Instrument PairStock1 = null;
                    Instrument PairStock2 = null;

                    for(int i1=0;i1<port.Count;i1++)
                        for(int i2=i1;i2<port.Count;i2++)
                            if (i1 != i2) {
                                Instrument Stock1 = port.instrument(i1);
                                Instrument Stock2 = port.instrument(i2);

                                TimeSeriesFractal CloseSeries1 = Stock1.closeSeries();
                                TimeSeriesFractal CloseSeries2 = Stock2.closeSeries();

                                CloseSeries1.setOperationwindows(Date1, Date2);
                                CloseSeries2.setOperationwindows(Date1, Date2);

                                double Corr = CloseSeries1.getCorrelation(CloseSeries2);

                                Console.WriteLine("{0} - {1} : {2}", Stock1.Name, Stock2.Name, Corr);

                                if (Corr > MaxCorr) {
                                    MaxCorr = Corr;

                                    PairStock1 = Stock1;
                                    PairStock2 = Stock2;
                                }
                            }

                    Console.WriteLine("{0} - {1} : {2}", PairStock1.Name, PairStock2.Name, MaxCorr);

                    TimeSeriesFractal Series1 = PairStock1.closeSeries();
                    TimeSeriesFractal Series2 = PairStock2.closeSeries();

                    Regression Regression = new Regression(Series1, Series2);

                    Regression.Calculate();

                    Regression.Print();

                    double alpha = Regression.getParam(0);
                    double beta  = Regression.getParam(1);

                    Console.WriteLine();
                    Console.WriteLine("alpha = {0:F2}, beta = {1:F2}", alpha, beta);

                    TTimeSeries TestSeries1 = Series1.Clone();
                    TTimeSeries TestSeries2 = Series2.Clone();

                    TestSeries1.SetDataRange(Date2, Date3);
                    TestSeries2.SetDataRange(Date2, Date3);

                    TRegression TestRegression = new TRegression(TestSeries1, TestSeries2);
                    TestRegression.SetParam(0, alpha);
                    TestRegression.SetParam(1, beta );

                    TCanvas Canvas = TCanvasManager.getCanvas("ResidualCanvas");
                    if (Canvas == null)
                        Canvas = new TCanvas("ResidualCanvas", "Residual canvas", 800, 400);

                    Canvas.Clear();

                    Canvas.Divide(2, 2);

                    Canvas.cd(1);

                    Series1.Draw();

                    Canvas.cd(2);

                    Series2.Draw();

                    TPad Pad = Canvas.cd(3);

                    Regression.getResidualSeries().GraphColor = Color.Blue;
                    Regression.Draw("R");

                    TestRegression.getResidualSeries().GraphColor = Color.Red;
                    TestRegression.Draw("R");

                    Pad.SetRange(Date1, Date3, -5, 5);

                    Canvas.cd(4);

                    Regression.Draw("PL");

                    Canvas.UpdateChart();

                    Portfolio = new Portfolio("Portfolio", "Pair stock portfolio");

                    double UpperLevel =  1.0;
                    double LowerLevel = -1.0;

                    double Close1 = 0;
                    double Close2 = 0;

                    double Spread     = 0;
                    double PrevSpread = 0;

                    if (SpreadSeries.IsEmpty(Date2))
                        PrevSpread = SpreadSeries.getPrevData(Date2);
                    else
                        PrevSpread = SpreadSeries.getCausale_cambio_data(Date2);

                    Console.WriteLine("Spread : {0}", PrevSpread);

                    Transaction Transaction1;
                    Transaction Transaction2;

                    for(Date TradingDate=Date2;TradingDate<=Date3;TradingDate=TradingDate.AddDays(1))
                        if (!TestSpreadSeries.IsEmpty(TradingDate)) {
                            Close1 = TestSeries1.getCausale_cambio_data(TradingDate);
                            Close2 = TestSeries2.getCausale_cambio_data(TradingDate);

                            Spread = TestSpreadSeries.getCausale_cambio_data(TradingDate);

                            if (PrevSpread > UpperLevel && Spread < UpperLevel) {
                                Console.WriteLine("Open upper on {0}", TradingDate.ToShortDateString());

                                Transaction1 = new Transaction(PairStock1, ETransactionType.BUY, 100, EDataOption.CLOSE, TradingDate);
                                Transaction2 = new Transaction(PairStock2, ETransactionType.SELLSHORT, Math.Round(Close1/Close2 * 100), EDataOption.CLOSE, TradingDate);

                                port.add(Transaction1);
                                port.add(Transaction2);
                            }

                            if (PrevSpread > LowerLevel && Spread < LowerLevel) {
                                Console.WriteLine("Close lower on {0}", TradingDate.ToShortDateString());

                                port.ClosePosition(PairStock1, EDataOption.CLOSE, TradingDate);
                                port.ClosePosition(PairStock2, EDataOption.CLOSE, TradingDate);
                            }

                            if (PrevSpread < LowerLevel && Spread > LowerLevel) {
                                Console.WriteLine("Open lower on {0}", TradingDate.ToShortDateString());

                                Transaction1 = new Transaction(PairStock1, ETransactionType.SELLSHORT, 100, EDataOption.CLOSE, TradingDate);
                                Transaction2 = new Transaction(PairStock2, ETransactionType.BUY, Math.Round(Close1/Close2 * 100), EDataOption.CLOSE, TradingDate);

                                port.add(Transaction1);
                                port.add(Transaction2);
                            }

                            if (PrevSpread < UpperLevel && Spread > UpperLevel) {
                                Console.WriteLine("Close upper on {0}", TradingDate.ToShortDateString());

                                port.ClosePosition(PairStock1, EDataOption.CLOSE, TradingDate);
                                port.ClosePosition(PairStock2, EDataOption.CLOSE, TradingDate);
                            }

                            PrevSpread = Spread;
                        }

                    TradingSystem tester = new tTester(port);

                    TradingSystem.Draw();

        //}
*/
    }


    public static void main(String[] argv) {

        PairTrading pt = new PairTrading();
        // pt.init();
        pt.go();

    }
}

