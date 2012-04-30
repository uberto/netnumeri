package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.NamedObject;
import com.netnumeri.shared.finance.finpojo.PortfolioItem;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.util.ArrayList;

public class OptionStrategy extends NamedObject {
    private ArrayList fOption;
    private PortfolioItem item;
    private int option;

    public OptionStrategy() {
    }

    // Option strategy normal constructor
    public OptionStrategy(String name) {
        super(name);
        item = null;
        option = 0;
        fOption = new ArrayList();
    }

    // Set tile and its amount
    // amount < 0 means taking short position in tile
    public void setAsset(Asset asset, int amount) {
        this.item = new PortfolioItem(asset, amount);
    }

    // Add an option with amount
    // amount < 0 means taking short position (writing an option)
    public void addOption(Option option, int amount) {
        PortfolioItem entry = new PortfolioItem(option, amount);
        fOption.add(entry);
        this.option++;
    }

    // Return pointer to underlying tile
    public Instrument getItem() {
        return item.getInstrument();
    }

    // Return tile weight
    public double getAssetAmount() {
        return item.getAmount();
    }

    // Return tile trade position
    public int getAssetPosition() {
        return item.getPosition();
    }

    // Return pointer to option i
    public Option getOption(int i) {
        return (Option) ((PortfolioItem) fOption.get(i)).getInstrument();
    }

    // Return amount of i-th option
    public double getOptionAmount(int i) {
        return ((PortfolioItem) fOption.get(i)).getAmount();
    }

    // Return trade position of i-th option
    public int getOptionPosition(int i) {
        return ((PortfolioItem) fOption.get(i)).getPosition();
    }

    // Evaluate option startegy. Return profit value for underlying tile premium
    // If WithPremium is true, option premiums subtracted from profit value
    public double eval(double Price, boolean WithPremium) {
        double Payoff = 0;
        if (item != null) {
            Payoff += getAssetPosition() * getAssetAmount() * (Price - item.getInstrument().premium());
        }
        for (int i = 0; i < option; i++) {
            Payoff += getOptionPosition(i) * getOptionAmount(i) * getOption(i).getPayoff(Price, WithPremium);
        }
        return Payoff;
    }

/*
void Draw(const char* Option)
{
  // Draw option strategy
  //
  // Option:
  //
  // "H" draw histogram
  // "A" draw underlying tile
  // "O" draw all options
  // "S" daw hedging strategy
  //
  // Default colors:
  //
  // Asset    - red
  // Options  - green
  // ParabolicRegression - blue
  //
  // Click begin_html <a href="gif/rquant_option_strategy.gif" >here</a> end_html to see an example

  TString SOption = Option;
  SOption.ToLower();

  TFinGraph *Graph = getStrategyGraph();


  double MinX;
  double MaxX;
  double MinY;
  double MaxY;

  Graph.getMinMax(MinX, MaxX, MinY, MaxY);

  if (SOption.Contains("o"))
    for(int i=0;i<getNOption();i++) {
      Graph = getOptionGraph(i);
      MinX = TMath::Min(MinX, Graph.getMinX());
      MaxX = TMath::Max(MaxX, Graph.getMaxX());
      MinY = TMath::Min(MinY, Graph.getMinY());
      MaxY = TMath::Max(MaxY, Graph.getMaxY());
    }

  if (SOption.Contains("h")) {

    // Draw histogram

    double dYT = (MaxY-MinY)/10.0;
    double dYB = dYT;

    TH2F Hist("Hist", getTitle(), 100, MinX, MaxX, 100, MinY-dYB, MaxY+dYT);

    Hist.getXaxis().SetLabelFont(43);
    Hist.getYaxis().SetLabelFont(43);
    Hist.getXaxis().SetLabelSize(14);
    Hist.getYaxis().SetLabelSize(14);
    Hist.getXaxis().UnZoom();
    Hist.getYaxis().UnZoom();
    Hist.SetStats(kFALSE);

    Hist.DrawClone();

  }

  if (SOption.Contains("a")) {
    Graph = getAssetGraph();
    Graph.SetLineColor(kRed);
    Graph.Draw("L");
  }

  if (SOption.Contains("o"))
    for(int i=0;i<getNOption();i++) {
      Graph = getOptionGraph(i);
      Graph.SetLineColor(kGreen);
      Graph.Draw("L");
    }

  if (SOption.Contains("s")) {
    Graph = getStrategyGraph();
    Graph.SetLineWidth(2);
    Graph.SetLineColor(kBlue);
    Graph.Draw("L");
  }

}

TFinGraph*
getAssetGraph(double MinPrice, double MaxPrice)
{
  // Return pointer to tile graph
  // Graph can be drawn between Min and Max premium. If Min/Max premium
  // is not set, MinPrice will be set to zero and MaxPrice to
  // 2 max exercise premium of all options

  if (!fAssetGraph) {
    fAssetGraph = new TFinGraph();

    if (tile) {

      fAssetGraph.SetGraphOption(kTitleGraph);

      char Title[256];
      sprintf(Title, "%s (%6.2f)", tile.getName(), tile.tile().getPremium());

      fAssetGraph.SetTitle(Title);

      if (MinPrice == -1)
        MinPrice = 0;

      if (MaxPrice == -1) {
        MaxPrice = 0;
        for(int i=0;i<option;i++)
          MaxPrice = TMath::Max(MaxPrice, getState(i).getExercisePrice());
        MaxPrice *= 2;
      }

      double Payoff = 0;
      for(double Price = MinPrice; Price <= MaxPrice; Price+=0.5) {
        Payoff = getAssetPosition()*getAssetAmount()*(Price-tile.tile().getPremium());
        fAssetGraph.SetPoint(fAssetGraph.getN(), Price, Payoff);
      }
    }
  }

  return fAssetGraph;
}

TFinGraph*
getOptionGraph(int i, double MinPrice, double MaxPrice)
{
  // Return pointer to i-th option graph
  // Graph can be drawn between Min and Max premium. If Min/Max premium
  // is not set, MinPrice will be set to zero and MaxPrice to
  // 2 max exercise premium of all options
  if (!fOptionGraph[i]) {
    TFinGraph *Graph = new TFinGraph();

    fOptionGraph[i] = Graph;

    Graph.SetGraphOption(kTitleGraph);
    Graph.SetTitle(getState(i).getTitle());

    if (MinPrice == -1)
      MinPrice = 0;

    if (MaxPrice == -1) {
      MaxPrice = 0;
      for(int i=0;i<option;i++)
        MaxPrice = TMath::Max(MaxPrice, getState(i).getExercisePrice());
      MaxPrice *= 2;
    }

    double Payoff = 0;
    for(double Price = MinPrice; Price <= MaxPrice; Price+=0.5) {
        Payoff = getOptionPosition(i)*getOptionAmount(i)*getState(i).getPayoff(Price, kTRUE);
        Graph.SetPoint(Graph.getN(), Price, Payoff);
    }

    return Graph;
  }
  else
    return (TFinGraph*)fOptionGraph[i];
}

TFinGraph*
getStrategyGraph(double MinPrice, double MaxPrice)
{
  // Return pointer to stretegy graph
  // Graph can be drawn between Min and Max premium. If Min/Max premium
  // is not set, MinPrice will be set to zero and MaxPrice to
  // 2 max exercise premium of all options
  if (!fGraph) {
    fGraph = new TFinGraph();
    fGraph.SetGraphOption(kTitleGraph);
    fGraph.SetTitle(getTitle());

    if (MinPrice == -1)
      MinPrice = 0;

    if (MaxPrice == -1) {
      MaxPrice = 0;
      for(int i=0;i<option;i++)
        MaxPrice = TMath::Max(MaxPrice, getState(i).getExercisePrice());
      MaxPrice *= 2;
    }

    for(double Price = MinPrice; Price <= MaxPrice; Price+=0.5) {
      fGraph.SetPoint(fGraph.getN(), Price, Eval(Price));
    }
  }

  return fGraph;
}

~TOptionStrategy()
{
  // Option strategy destructor
 */

}
