package au.net.woodberry.ta.toolbox.indicators.trend;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

class EMAIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> indicator;

    private final int timeFrame;

    /**
     * A custom EMA indicator used for Trend-based indicators
     *
     * @param indicator A simple indicator, normally a closing price indicator
     * @param timeFrame An applied timeFrame to perform the EMA calculations
     */
    EMAIndicator(Indicator<? extends TADecimal> indicator, int timeFrame) {
        this.indicator = indicator;
        this.timeFrame = timeFrame;
    }

    private TADecimal multiplier() {
        return TADecimal.TWO.dividedBy(TADecimal.valueOf(timeFrame + 1));
    }

    @Override
    protected TADecimal calculate(int index) {
        if(index == 0) {
            return indicator.getValue(0);
        }
        TADecimal emaPrev = getValue(index - 1);
        return indicator.getValue(index).minus(emaPrev).multipliedBy(multiplier()).plus(emaPrev);
    }
}