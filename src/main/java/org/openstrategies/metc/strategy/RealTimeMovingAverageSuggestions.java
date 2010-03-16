package org.openstrategies.metc.strategy;

import java.util.*;
import org.marketcetera.marketdata.*;
import org.marketcetera.trade.*;
import org.openstrategies.metc.strategy.base.*;
import org.openstrategies.metc.strategy.util.*;

/**
 * Follow the 2, 4, and 8 moving averages.  Suggest positive strength on 2 and 4 cross above the 8
 * and positive weakness on 2 below 8.  Suggest negative strength on 2 and 4 cross below 8 and neg-
 * ative weakness on 2 above 8. 
 * 
 * Does not issue trades, just trade suggestions.
 * 
 * Improvements to this simple proof-of-concept strategy and associated tools are strongly encourag-
 * ed.  Check-out, copy the class, enhance, and commit: http://github.com/sfrancolla/openstrategies
 *
 * @author sfrancolla@gmail.com
 */
public class RealTimeMovingAverageSuggestions extends OpenStrategy {

   public static final String[] SYMBOLS = {"zzz"}; //only 1 symbol works in this proof-of-concept.
   public static final String[] CEP_QUERY = EPL.MA_2_4_8;

   public static final int MIN_ITERATIONS = 8;
   private int iteration = 0;

   private boolean isBuy = false;
   private boolean isSellShort = false;

   @Override
   public void onStart() {
      requestProcessedMarketData(MarketDataRequest.newRequest().withSymbols(SYMBOLS).fromProvider(EPL.MARKET_DATA_PROVIDER).withContent(MarketDataRequest.Content.TOP_OF_BOOK), CEP_QUERY, EPL.CEP_PROVIDER);
   }

   @Override
   public void onOther(Object inEvent) {
      super.onOther(inEvent);
      if (inEvent instanceof Map) {
         Map map = (Map)inEvent;
         Data currData = new Data(map);
         if (currData.ma4.compareTo(currData.ma8) > 0 && !isBuy) {
            isBuy = true;
            Trade.forStrategy(this).ifTrue(iteration >= MIN_ITERATIONS).comment("Ma-4 up-cross of ma-8.").suggest(currData.symbol, Side.Buy, currData.price);
            warn("Buy - Ma-4 up-cross of ma-8.");
         } else if (currData.ma2.compareTo(currData.ma4) < 0 && isBuy) {
            isBuy = false;
            Trade.forStrategy(this).ifTrue(iteration >= MIN_ITERATIONS).comment("Ma-2 down-cross of ma-4.").suggest(currData.symbol, Side.Sell, currData.price);
            warn("Sell - Ma-2 down-cross of ma-4.");
         }
         if (currData.ma4.compareTo(currData.ma8) < 0 && !isSellShort) {
            isSellShort = true;
            Trade.forStrategy(this).ifTrue(iteration >= MIN_ITERATIONS).comment("Ma-4 down-cross of ma-8.").suggest(currData.symbol, Side.SellShort, currData.price);
            warn("SellShort - Ma-4 down-cross of ma-8.");
         } else if (currData.ma2.compareTo(currData.ma4) > 0 && isSellShort) {
            isSellShort = false;
            Trade.forStrategy(this).ifTrue(iteration >= MIN_ITERATIONS).comment("Ma-2 up-cross of ma-4.").suggest(currData.symbol, Side.Buy, Direction.Short, currData.price);
            warn("BuyToCover - Ma-2 up-cross of ma-4.");
         }
         if (iteration < MIN_ITERATIONS) {
            iteration++;
         }
      }
   }

   /**
    * Delegate to translate the map.
    */
   private static class Data {
      String symbol;
      Double price;
      Double ma2;
      Double ma4;
      Double ma8;

      public Data(Map map) {
         symbol = (String)map.get("symbol");
         price = (Double)map.get("price");
         ma2 = (Double)map.get("ma2");
         ma4 = (Double)map.get("ma4");
         ma8 = (Double)map.get("ma8");
      }
   }

}