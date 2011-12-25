package com.netnumeri.shared.finance.ta;


import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Daily;
import com.netnumeri.shared.finance.finpojo.asset.Stock;

public class Candlestick {
    private static final double onePercent = 0.01;
    private static final double fivePercent = 0.05;
    private static final double zeroZero5 = 0.005;
    private static final double zeroZero1 = 0.001;

    public static Pattern patternCheck(Stock stock, TDay date) {
        if (isAbandoned_Baby_Bullish(stock, date)) {
            return Pattern.Abandoned_Baby_Bullish;

        } else if (isAbandoned_Baby_Bearish(stock, date)) {
            return Pattern.Abandoned_Baby_Bearish;

        } else if (isDark_Cloud_Cover(stock, date)) {
            return Pattern.Dark_Cloud_Cover;

        } else if (isDragonfly_Doji_Bullish(stock, date)) {
            return Pattern.Dragonfly_Doji_Bullish;

        } else if (isDragonfly_Doji_Bearish(stock, date)) {
            return Pattern.Dragonfly_Doji_Bearish;

        } else if (isDoji(stock, date)) {
            return Pattern.Doji;

        } else if (isUpside_Tasuki_Gap(stock, date)) {
            return Pattern.Upside_Tasuki_Gap;

        } else if (isDownside_Tasuki_Gap(stock, date)) {
            return Pattern.Downside_Tasuki_Gap;

        } else if (isEngulfing_Pattern_Bullish(stock, date)) {
            return Pattern.Engulfing_Pattern_Bullish;

        } else if (isEngulfing_Pattern_Bearish(stock, date)) {
            return Pattern.Engulfing_Pattern_Bearish;

        } else if (isEvening_Doji_Star(stock, date)) {
            return Pattern.Evening_Doji_Star;

        } else if (isEvening_Star(stock, date)) {
            return Pattern.Evening_Star; // Evening Star Candlestick  A bearish reversal pattern that continues an uptrend with a long white body day followed by a gapped up small body day, then a down close with the close below the midpoint of the first day.

        } else if (isFalling_Three_Methods(stock, date)) {
            return Pattern.Falling_Three_Methods;

        } else if (isGravestone_Doji(stock, date)) {
            return Pattern.Gravestone_Doji; // Gravestone Doji Candlestick  A doji line that develops when the Doji is at, or very near, the low of the day.
        } else if (isHammer(stock, date)) {
            return Pattern.Hammer; // Hammer Candlestick  Hammer candlesticks form when a security moves significantly lower after the open, but rallies to close well above the intraday low. The resulting candlestick looks like a square lollipop with a long stick. If this candlestick forms during an advance, then it is called a Hanging Man.
        } else if (isHanging_Man(stock, date)) {
            return Pattern.Hanging_Man; // Hanging Man Candlestick  Hanging Man candlesticks form when a security moves significantly lower after the open, but rallies to close well above the intraday low. The resulting candlestick looks like a square lollipop with a long stick. If this candlestick forms during a decline, then it is called a Hammer.
        } else if (isHarami(stock, date)) {
            return Pattern.Harami;
        } else if (isHarami_Cross(stock, date)) {
            return Pattern.Harami_Cross; // Harami Cross Candlestick  A two day pattern similar to the Harami. The difference is that the last day is a Doji.
        } else if (isInverted_Hammer(stock, date)) {
            return Pattern.Inverted_Hammer; // Inverted Hammer Candlestick  A one day bullish reversal pattern. In a downtrend, the open is lower, then it trades higher, but closes near its open, therefore looking like an inverted lollipop.
        } else if (isLong_Day(stock, date)) {
            return Pattern.Long_Day; // Long TDay Candlestick  A long day represents a large price move from open to close, where the length of the candle body is long.
        } else if (isLong_Legged_Doji(stock, date)) {
            return Pattern.Long_Legged_Doji; // Long-legged Doji Candlestick  This candlestick has long upper and lower shadows with the Doji in the middle of the day's trading range, clearly reflecting the indecision of traders.
        } else if (isLong_Shadows(stock, date)) {
            return Pattern.Long_Shadows; // Long Shadows Long Shadows Candlestick  Candlesticks with a long upper shadow and short lower shadow indicate that buyers dominated during the session and bid prices higher. Conversely, candlesticks with long lower shadows and short upper shadows indicate that sellers dominated during the session and drove prices lower.
        } else if (isMarubozo(stock, date)) {
            return Pattern.Marubozo; // Marubozu Candlestick  A candlestick with no shadow extending from the body at either the open, the close or at both. The name means close-cropped or close-cut in Japanese, though other interpretations refer to it as Bald or Shaven Head.
        } else if (isMorning_Doji_Star(stock, date)) {
            return Pattern.Morning_Doji_Star; // Morning Doji Star Candlestick  A three day bullish reversal pattern that is very similar to the Morning Star. The first day is in a downtrend with a long black body. The next day opens lower with a Doji that has a small trading range. The last day closes above the midpoint of the first day.
        } else if (isMorning_Star(stock, date)) {
            return Pattern.Morning_Star; // Morning Star Candlestick  A three day bullish reversal pattern consisting of three candlesticks - a long-bodied black candle extending the current downtrend, a short middle candle that gapped down on the open, and a long-bodied white candle that gapped up on the open and closed above the midpoint of the body of the first day.
        } else if (isPiercing_Line(stock, date)) {
            return Pattern.Piercing_Line; // Piercing Line Candlestick  A bullish two day reversal pattern. The first day, in a downtrend, is a long black day. The next day opens at a new low, then closes above the midpoint of the body of the first day.
        } else if (isRising_Three_Methods(stock, date)) {
            return Pattern.Rising_Three_Methods; // Rising Three Methods Candlestick  A bullish continuation pattern in which a long white body is followed by three small body days, each fully contained within the range of the high and low of the first day. The fifth day closes at a new high.
        } else if (isShooting_Star(stock, date)) {
            return Pattern.Shooting_Star; // Shooting Star Candlestick  A single day pattern that can appear in an uptrend. It opens higher, trades much higher, then closes near its open. It looks just like the Inverted Hammer except that it is bearish.
        } else if (isShort_Day(stock, date)) {
            return Pattern.Short_Day; // Short TDay Candlestick  A short day represents a small price move from open to close, where the length of the candle body is short.
        } else if (isSpinning_Top(stock, date)) {
            return Pattern.Spinning_Top; // Spinning Tops Candlestick  Candlestick lines that have small bodies with upper and lower shadows that exceed the length of the body. Spinning tops signal indecision.
        } else if (isStars(stock, date)) {
            return Pattern.Stars; // Stars Candlestick  A candlestick that gaps away from the previous candlestick is said to be in star position. Depending on the previous candlestick, the star position candlestick gaps up or down and appears isolated from previous price action.
        } else if (isStick_Sandwich(stock, date)) {
            return Pattern.Stick_Sandwich; // Stick Sandwich Candlestick  A bullish reversal pattern with two black bodies surrounding a white body. The closing prices of the two black bodies must be equal. A support prices is apparent and the opportunity for prices to reverse is quite good.
        } else if (isThree_Black_Crows(stock, date)) {
            return Pattern.Three_Black_Crows; // Three Black Crows Candlestick  A bearish reversal pattern consisting of three consecutive long black bodies where each day closes at or near its low and opens within the body of the previous day.
        } else if (isThree_White_Soldiers(stock, date)) {
            return Pattern.Three_White_Soldiers; // Three White Soldiers Candlestick  A bullish reversal pattern consisting of three consecutive long white bodies. Each should open within the previous body and the close should be near the high of the day.
        } else if (isUpside_Gap_Two_Crows(stock, date)) {
            return Pattern.Upside_Gap_Two_Crows; // Upside Gap Two Crows Candlestick  A three day bearish pattern that only happens in an uptrend. The first day is a long white body followed by a gapped open with the small black body remaining gapped above the first day. The third day is also a black day whose body is larger than the second day and engulfs it. The close of the last day is still above the first long white day.
        }
        return Pattern.NONE;
    }

    // Upside Gap Two Crows: Upside Gap Two Crows Candlestick
    // A three day bearish pattern that only happens in an uptrend.
    // The first day is a long white body followed by a gapped open with the small black body remaining gapped above the first day.
    // The third day is also a black day whose body is larger than the second day and engulfs it.
    // The close of the last day is still above the first long white day.
    public static boolean isUpside_Gap_Two_Crows(Stock stock, TDay date) {
        int i = stock.getIndex(date);
        Daily daily = stock.getDaily(i);
        Daily yesterdayDaily = stock.getDaily(i - 1);
        Daily dayBeforeYesterdayDaily = stock.getDaily(i - 2);
        if (!isLongWhiteBody(dayBeforeYesterdayDaily)) return false;
        if (!isGapUp(dayBeforeYesterdayDaily, yesterdayDaily)) return false;
        if (!isCrown(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        if (!isBodyLargerAndEngulfing(daily, yesterdayDaily)) return false;
        if (!(daily.getCloseprice() > dayBeforeYesterdayDaily.getHigh())) return false;
        return true;
    }

    public static boolean isBodyLargerAndEngulfing(Daily daily, Daily yesterdayDaily) {
        if ((opensNewHigh(daily, yesterdayDaily) &&
                daily.getCloseprice() < yesterdayDaily.getLow()))
            return true;
        return false;
    }

    public static boolean isBlack(Daily daily) {
        if (daily.getCloseprice() < daily.getOpenprice()) return true;
        return false;
    }

    public static boolean isWhite(Daily daily) {
        if (daily.getCloseprice() >= daily.getOpenprice()) return true;
        return false;
    }

    // 100, 0.01
    public static double percent(double val, double cents) {
        return val * cents;
    }

    public static boolean isCrown(Daily daily) {
        if (daily.getOpenprice() > daily.getLow() &&
                daily.getCloseprice() < daily.getHigh() &&
                (daily.getCloseprice() - daily.getOpenprice()) <
                        percent(daily.getHigh() - daily.getLow(), 0.05))
            return true;
        return false;
    }

    public static boolean isGapUp(Daily dayBeforeYesterdayDaily, Daily yesterdayDaily) {
        if (dayBeforeYesterdayDaily.getHigh() < yesterdayDaily.getLow())
            return true;
        return false;
    }

    public static boolean isGapDown(Daily yesterady, Daily today) {
        if (yesterady.getLow() > today.getHigh())
            return true;
        return false;
    }

    public static boolean isLongWhiteBody(Daily daily) {
        if (daily.getOpenprice() < daily.getCloseprice() &&
                Math.abs(daily.getHigh() - daily.getLow()) > threePercentFromOpen(daily))
            return true;
        return false;
    }

    public static double threePercentFromOpen(Daily daily) {
        return percent(daily.getOpenprice(), 0.03);
    }

    public static boolean isLongBlackBody(Daily daily) {
        if (daily.getOpenprice() > daily.getCloseprice() &&
                Math.abs(daily.getHigh() - daily.getLow()) > threePercentFromOpen(daily))
            return true;
        return false;
    }


    // Three Black Crows: Three Black Crows Candlestick
    // A bearish reversal pattern consisting of three consecutive long black
    // bodies where each day closes at or near its low and opens within the body
    // of the previous day.
    public static boolean isThree_Black_Crows(Stock stock, TDay date) {
        int i = stock.getIndex(date);
        Daily daily = stock.getDaily(i);
        Daily yesterdayDaily = stock.getDaily(i - 1);
        Daily dayBeforeYesterdayDaily = stock.getDaily(i - 2);
        if (!closesAtHisLows(dayBeforeYesterdayDaily)) return false;
        if (!closesAtHisLows(yesterdayDaily)) return false;
        if (!closesAtHisLows(daily)) return false;
        if (!(dayBeforeYesterdayDaily.getCloseprice() > yesterdayDaily.getOpenprice())) return false;
        if (!(yesterdayDaily.getCloseprice() > daily.getOpenprice())) return false;
        return true;
    }

    public static boolean closesAtHisLows(Daily daily) {
        double d = daily.getCloseprice() - daily.getLow();
        double spread = daily.getHigh() - daily.getLow();
        double percent = percent(spread, 0.05);
        if (!(d < percent)) return false;
        return true;
    }

    // Stick Sandwich: Stick Sandwich Candlestick
    // A bullish reversal pattern with two black bodies surrounding a white body.
    // The closing prices of the two black bodies must be equal.
    // A support prices is apparent and the opportunity for prices to reverse is quite good.
    public static boolean isStick_Sandwich(Stock stock, TDay date) {
        int i = stock.getIndex(date);
        Daily daily = stock.getDaily(i);
        Daily yesterdayDaily = stock.getDaily(i - 1);
        Daily dayBeforeYesterdayDaily = stock.getDaily(i - 2);
        if (!isBlack(dayBeforeYesterdayDaily)) return false;
        if (!isWhite(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        if (!(dayBeforeYesterdayDaily.getCloseprice() - daily.getCloseprice() < percent(daily.getCloseprice(), 0.02)))
            return false;
        return true;
    }

    //  Short TDay: Short TDay Candlestick
    //  A short day represents a small price move from open to close, where the length of the candle body is short.
    public static boolean isShort_Day(Stock stock, TDay date) {
        int i = stock.getIndex(date);
        Daily daily = stock.getDaily(i);
        if (!closeNextToOpen(daily)) return false;
        return true;
    }

    public static boolean closeNextToOpen(Daily daily) {
        if (Math.abs(daily.getCloseprice() - daily.getOpenprice()) < percent(daily.getCloseprice(), 0.02)) return true;
        return false;
    }

    // Shooting Star: Shooting Star Candlestick
    // A single day pattern that can appear in an uptrend.
    // It opens higher, trades much higher, then closes near its open.
    // It looks just like the Inverted Hammer except that it is bearish.
    public static boolean isShooting_Star(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        if (!tradesLargeBlackOrWhite(daily)) return false;
        return true;
    }

    public static boolean tradesLargeBlackOrWhite(Daily daily) {
        if (!((Math.abs(daily.getHigh() - daily.getLow()) > percent(daily.getCloseprice(), 0.04)))) return false;
        return true;
    }

    // Rising Three Methods: Rising Three Methods Candlestick  A bullish continuation pattern in which
    // a long white body is followed by three small body days, each fully contained
    // within the range of the high and low of the first day. The fifth day closes at a new high.
    public static boolean isRising_Three_Methods(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Marubozo: Marubozu Candlestick  A candlestick with no shadow extending from the body at either the open,
    // the close or at both. The name means close-cropped or close-cut in Japanese, though other interpretations
    // refer to it as Bald or Shaven Head.
    public static boolean isMarubozo(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    //  Long Shadows: Long Shadows Long Shadows Candlestick  Candlesticks with a long upper shadow and short lower shadow
    // indicate that buyers dominated during the session and bid prices higher. Conversely, candlesticks with
    // long lower shadows and short upper shadows indicate that sellers dominated during the session and drove prices lower.
    public static boolean isLong_Shadows(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Harami Candlestick  A two day pattern that has a small body day completely contained within the range
    // of the previous body, and is the opposite color.
    public static boolean isHarami(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Hanging Man: Hanging Man Candlestick  Hanging Man candlesticks form when a security moves significantly lower after the open,
    // but rallies to close well above the intraday low. The resulting candlestick looks like a square lollipop with a long stick.
    // If this candlestick forms during a decline, then it is called a Hammer.
    public static boolean isHanging_Man(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    //  * Gravestone Doji: Gravestone Doji Candlestick  A doji line that develops when the Doji is at,
    // or very near, the low of the day.
    public static boolean isGravestone_Doji(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        if (!isDoji(daily)) return false;
        if (daily.getOpenprice() < percent(daily.getOpenprice(), zeroZero1)) return false;
        return true;
    }

    // Falling Three Methods: A bearish continuation pattern.
    // A long black body is followed by three small body days, each fully contained within
    // the range of the high and low of the first day. The fifth day closes at a new low.
    public static boolean isFalling_Three_Methods(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterday = days.getDayBeforeYesterdayDaily();
        Daily dayBeforeBeforeYesterday = days.getDayBeforeBeforeYesterdayDaily();
        Daily dayBeforeBeforeBeforeYesterday = days.getDayBeforeBeforeBeforeYesterdayDaily();
        if (!isLongBlackBody(dayBeforeBeforeBeforeYesterday)) return false;
        if (!isSmallDayNoDirection(dayBeforeBeforeYesterday) &&
                dayBeforeBeforeYesterday.getHigh() < dayBeforeBeforeBeforeYesterday.getHigh() &&
                dayBeforeBeforeYesterday.getLow() > dayBeforeBeforeBeforeYesterday.getLow()) return false;
        if (!isSmallDayNoDirection(dayBeforeYesterday) &&
                dayBeforeYesterday.getHigh() < dayBeforeBeforeBeforeYesterday.getHigh() &&
                dayBeforeYesterday.getLow() > dayBeforeBeforeBeforeYesterday.getLow()) return false;
        if (!isSmallDayNoDirection(yesterdayDaily) &&
                yesterdayDaily.getHigh() < dayBeforeBeforeBeforeYesterday.getHigh() &&
                yesterdayDaily.getLow() > dayBeforeBeforeBeforeYesterday.getLow()) return false;
        if (!(daily.getCloseprice() < dayBeforeBeforeBeforeYesterday.getLow())) return false;
        return true;
    }

    // Evening Star: Evening Star Candlestick  A bearish reversal pattern that continues an uptrend
    // with a long white body day followed by a gapped up small body day, then a down close with the close
    // below the midpoint of the first day.
    public static boolean isEvening_Star(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterday = days.getDayBeforeYesterdayDaily();
        if (!isLongWhiteBody(dayBeforeYesterday)) return false;
        if (!isGapDown(dayBeforeYesterday, yesterdayDaily)) return false;
        if (!isSmallDayNoDirection(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        double range = dayBeforeYesterday.getHigh() - dayBeforeYesterday.getLow();
        double midpoint = dayBeforeYesterday.getLow() + (range / 2);
        if (!(daily.getCloseprice() < midpoint)) return false;
        return true;
    }

    private static boolean isSmallDayNoDirection(Daily d) {
        double absolute = Math.abs(d.getLow() - d.getHigh());
        double p = percent(d.getOpenprice(), zeroZero5);
        if (absolute < p) return true;
        return false;
    }

    // Evening Doji Star: Evening Doji Star Candlestick
    // A three day bearish reversal pattern similar to the Evening Star.
    // The uptrend continues with a large white body. The next day opens higher,
    // trades in a small range, then closes at its open (Doji). The next day closes below the midpoint of the body of the first day.
    public static boolean isEvening_Doji_Star(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterday = days.getDayBeforeYesterdayDaily();
        if (!isLongWhiteBody(dayBeforeYesterday)) return false;
        if (!isDoji(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        double range = dayBeforeYesterday.getHigh() - dayBeforeYesterday.getLow();
        double midpoint = dayBeforeYesterday.getLow() + (range / 2);
        if (!(daily.getCloseprice() < midpoint)) return false;
        return true;
    }

    // Engulfing Pattern: Engulfing Pattern Candlestick  A reversal pattern that can be bearish or bullish,
    // depending upon whether it appears at the end of an uptrend (bearish engulfing pattern) or a downtrend
    // (bullish engulfing pattern). The first day is characterized by a small body, followed by a day whose
    // body completely engulfs the previous day's body.
    public static boolean isEngulfing_Pattern_Bullish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        if (!isBlack(yesterdayDaily)) return false;
        if (!isWhite(daily)) return false;
        if (!((daily.getOpenprice() < yesterdayDaily.getLow()) && (daily.getCloseprice() > yesterdayDaily.getHigh())))
            return false;
        return true;
    }

    public static boolean isEngulfing_Pattern_Bearish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        if (!isWhite(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        if (!((daily.getOpenprice() > yesterdayDaily.getHigh()) && (daily.getCloseprice() < yesterdayDaily.getLow())))
            return false;
        return true;
    }

    // Dragonfly Doji: Dragonfly Doji Candlestick  A Doji where the open and close price are at the high of the day.
    // Like other Doji days, this one normally appears at market turning points.
    public static boolean isDragonfly_Doji_Bearish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        if (!isDoji(daily)) return false;
        if (!(Math.abs(daily.getOpenprice() - daily.getHigh()) <= percent(daily.getOpenprice(), zeroZero5)))
            return false;
        return true;
    }

    public static boolean isDragonfly_Doji_Bullish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        if (!isDoji(daily)) return false;
        if (!(Math.abs(daily.getOpenprice() - daily.getLow()) <= percent(daily.getOpenprice(), zeroZero5)))
            return false;
        return true;
    }

    // Downside Tasuki Gap: Downside Tasuki Gap Candlestick  A continuation pattern with a long,
    // black body followed by another black body that has gapped below the first one.
    // The third day is white and opens within the body of the second day,
    // then closes in the gap between the first two days, but does not close the gap.
    public static boolean isDownside_Tasuki_Gap(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterday = days.getDayBeforeYesterdayDaily();
        if (!isLongBlackBody(dayBeforeYesterday)) return false;
        if (!isBlack(yesterdayDaily)) return false;
        if (!isWhite(daily)) return false;
        if (!isGapDown(dayBeforeYesterday, yesterdayDaily)) return false;
        if (!(yesterdayDaily.getCloseprice() < daily.getOpenprice())) return false;
        if (!(daily.getOpenprice() < yesterdayDaily.getOpenprice())) return false;
        if (!(daily.getCloseprice() < dayBeforeYesterday.getLow())) return false;
        if (!(daily.getCloseprice() > yesterdayDaily.getHigh())) return false;
        return true;
    }

    // Doji: Doji Candlestick  Doji form when a security's open and close are virtually equal.
    // The length of the upper and lower shadows can vary, and the resulting candlestick looks like,
    // either, a cross, inverted cross, or plus sign. Doji convey a sense of indecision or tug-of-war
    // between buyers and sellers. Prices move above and below the opening level during the session,
    // but close at or near the opening level.
    public static boolean isDoji(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        return isDoji(daily);
    }

    public static boolean isDoji(Daily daily) {
        if (Math.abs(daily.getOpenprice() - daily.getCloseprice()) <= percent(daily.getOpenprice(), zeroZero5))
            return true;
        return false;
    }

    // Piercing Line: Piercing Line Candlestick  A bullish two day reversal pattern.
    // The first day, in a downtrend, is a long black day. The next day opens at a new low,
    // then closes above the midpoint of the body of the first day.
    public static boolean isPiercing_Line(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Harami Cross: Harami Cross Candlestick  A two day pattern similar to the Harami. The difference is that the last day is a Doji.
    public static boolean isHarami_Cross(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Morning Doji Star: Morning Doji Star Candlestick  A three day bullish reversal pattern that is very similar to the Morning Star.
    // The first day is in a downtrend with a long black body. The next day opens lower with a Doji that has a small trading range.
    // The last day closes above the midpoint of the first day.
    public static boolean isMorning_Doji_Star(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Dark Cloud Cover: Dark Cloud Cover Candlestick
    // A bearish reversal pattern that continues the uptrend with a long white body.
    // The next day opens at a new high then closes below the midpoint of the body of the first day.
    public static boolean isDark_Cloud_Cover(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        if (!isLongWhiteBody(yesterdayDaily)) return false;
        if (!isLongBlackBody(daily)) return false;
        if (!opensNewHigh(daily, yesterdayDaily)) return false;
        if (!closesBelowMidpointDayBodyBefore(daily, yesterdayDaily)) return false;
        if (!(daily.getCloseprice() > yesterdayDaily.getOpenprice())) return false;
        return true;
    }

    private static boolean closesBelowMidpointDayBodyBefore(Daily daily, Daily yesterdayDaily) {
        return daily.getCloseprice() < (yesterdayDaily.getOpenprice() + (yesterdayDaily.getCloseprice() - yesterdayDaily.getOpenprice() / 2));
    }

    private static boolean opensNewHigh(Daily daily, Daily yesterdayDaily) {
        return daily.getOpenprice() > yesterdayDaily.getHigh();
    }

    // Inverted Hammer: Inverted Hammer Candlestick  A one day bullish reversal pattern.
    // In a downtrend, the open is lower, then it trades higher, but closes near its open, therefore looking like an inverted lollipop.
    public static boolean isInverted_Hammer(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily dayBeforeYesterdayDaily = days.getDayBeforeYesterdayDaily();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();

        return true;
    }

    // Morning Star: Morning Star Candlestick  A three day bullish reversal pattern consisting of
    // three candlesticks - a long-bodied black candle extending the current downtrend, a short middle candle
    // that gapped down on the open, and a long-bodied white candle that gapped up on the open and closed
    // above the midpoint of the body of the first day.
    public static boolean isMorning_Star(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    //  Long-Legged Doji: Long-legged Doji Candlestick  This candlestick has long upper and lower shadows
    // with the Doji in the middle of the day's trading range, clearly reflecting the indecision of traders.
    public static boolean isLong_Legged_Doji(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Long TDay: Long TDay Candlestick  A long day represents a large price move from open to close,
    // where the length of the candle body is long.
    public static boolean isLong_Day(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Hammer: Hammer Candlestick  Hammer candlesticks form when a security moves significantly lower after the open,
    // but rallies to close well above the intraday low. The resulting candlestick looks like a square lollipop with a long stick. If this candlestick forms during an advance, then it is called a Hanging Man.
    public static boolean isHammer(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Spinning Top: Spinning Tops Candlestick  Candlestick lines that have small bodies with upper and lower shadows
    // that exceed the length of the body. Spinning tops signal indecision.
    public static boolean isSpinning_Top(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Stars: Stars Candlestick  A candlestick that gaps away from the previous candlestick is said to be in
    // star position. Depending on the previous candlestick, the star position candlestick gaps up or down and appears
    // isolated from previous price action.
    public static boolean isStars(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Three White Soldiers: Three White Soldiers Candlestick  A bullish reversal pattern consisting of
    // three consecutive long white bodies. Each should open within the previous body and the close should be near the high of the day.
    public static boolean isThree_White_Soldiers(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        return true;
    }

    // Upside Tasuki Gap: Upside Tasuki Gap Candlestick
    // A continuation pattern with a long white body followed by another white body that has gapped above the first one.
    // The third day is black and opens within the body of the second day, then closes in the gap between the first two days,
    // but does not close the gap.
    public static boolean isUpside_Tasuki_Gap(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterday = days.getDayBeforeYesterdayDaily();
        if (!isLongWhiteBody(dayBeforeYesterday)) return false;
        if (!isWhite(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        if (!isGapUp(dayBeforeYesterday, yesterdayDaily)) return false;

        if (!(yesterdayDaily.getOpenprice() < daily.getOpenprice())) return false;
        if (!(daily.getOpenprice() < yesterdayDaily.getCloseprice())) return false;

        if (!(daily.getCloseprice() < yesterdayDaily.getLow())) return false;
        if (!(daily.getCloseprice() > dayBeforeYesterday.getHigh())) return false;
        return true;
    }

    // Abandoned Baby Candlestick
    // A rare reversal pattern characterized by a gap followed by a Doji,
    // which is then followed by another gap in the opposite direction.
    // The shadows on the Doji must completely gap below or above the shadows of the first and third day.
    public static boolean isAbandoned_Baby_Bullish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily dayBeforeYesterdayDaily = days.getDayBeforeYesterdayDaily();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();

        if (!isBlack(dayBeforeYesterdayDaily)) return false;
        if (!isWhite(daily)) return false;
        if (!isDoji(yesterdayDaily)) return false;
        if (!isGapDown(dayBeforeYesterdayDaily, yesterdayDaily)) return false;
        if (!isGapUp(yesterdayDaily, daily)) return false;
        return true;
    }

    // Abandoned Baby Candlestick
    // A rare reversal pattern characterized by a gap followed by a Doji,
    // which is then followed by another gap in the opposite direction.
    // The shadows on the Doji must completely gap below or above the shadows of the first and third day.
    public static boolean isAbandoned_Baby_Bearish(Stock stock, TDay date) {
        Days days = new Days(stock, date).invoke();
        Daily daily = days.getDaily();
        Daily yesterdayDaily = days.getYesterdayDaily();
        Daily dayBeforeYesterdayDaily = days.getDayBeforeYesterdayDaily();

        if (!isWhite(dayBeforeYesterdayDaily)) return false;
        if (!isDoji(yesterdayDaily)) return false;
        if (!isBlack(daily)) return false;
        if (!isGapUp(dayBeforeYesterdayDaily, yesterdayDaily)) return false;
        if (!isGapDown(yesterdayDaily, daily)) return false;
        return true;
    }

    public static class Days {
        public Stock stock;
        public TDay date;
        public Daily daily;
        public Daily yesterdayDaily;
        public Daily dayBeforeYesterdayDaily;
        public Daily dayBeforeBeforeYesterdayDaily;
        public Daily dayBeforeBeforeBeforeYesterdayDaily;

        public Days(Stock stock, TDay date) {
            this.stock = stock;
            this.date = date;
        }

        public Daily getDaily() {
            return daily;
        }

        public Daily getYesterdayDaily() {
            return yesterdayDaily;
        }

        public Daily getDayBeforeYesterdayDaily() {
            return dayBeforeYesterdayDaily;
        }

        public Daily getDayBeforeBeforeYesterdayDaily() {
            return dayBeforeBeforeYesterdayDaily;
        }

        public Daily getDayBeforeBeforeBeforeYesterdayDaily() {
            return dayBeforeBeforeBeforeYesterdayDaily;
        }

        public Days invoke() {
            int i = stock.getIndex(date);
            daily = stock.getDaily(i);
            try {
                yesterdayDaily = stock.getDaily(i - 1);
            } catch (Exception e) {

            }
            try {
                dayBeforeYesterdayDaily = stock.getDaily(i - 2);
            } catch (Exception e) {
            }
            try {
                dayBeforeBeforeYesterdayDaily = stock.getDaily(i - 3);
            } catch (Exception e) {
            }
            try {
                dayBeforeBeforeBeforeYesterdayDaily = stock.getDaily(i - 4);
            } catch (Exception e) {

            }
            return this;
        }
    }
}