package uk.tw.energy.domain;

import org.assertj.core.data.Percentage;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TariffTest {

    @Test
    public void shouldReturnTheBasePriceGivenAnOrdinaryDateTime() throws Exception {
        LocalDateTime normalDateTime = of(2017, Month.AUGUST, 31, 12, 0, 0);
        Tariff.PeakTimeMultiplier peakTimeMultiplier = new Tariff.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        Tariff tariff = new Tariff("", BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = tariff.getPrice(normalDateTime);

        assertThat(price).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnAnExceptionTariffPriceGivenExceptionalDateTime() throws Exception {
        LocalDateTime exceptionalDateTime = of(2017, Month.AUGUST, 30, 23, 0, 0);
        Tariff.PeakTimeMultiplier peakTimeMultiplier = new Tariff.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        Tariff tariff = new Tariff("", BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = tariff.getPrice(exceptionalDateTime);

        assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }

    @Test
    public void shouldReceiveMultipleExceptionalDateTimes() throws Exception {
        LocalDateTime exceptionalDateTime = of(2017, Month.AUGUST, 30, 23, 0, 0);
        Tariff.PeakTimeMultiplier peakTimeMultiplier = new Tariff.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        Tariff.PeakTimeMultiplier otherPeakTimeMultiplier = new Tariff.PeakTimeMultiplier(DayOfWeek.TUESDAY, BigDecimal.TEN);
        List<Tariff.PeakTimeMultiplier> peakTimeMultipliers = Arrays.asList(peakTimeMultiplier, otherPeakTimeMultiplier);
        Tariff tariff = new Tariff("", BigDecimal.ONE, peakTimeMultipliers);

        BigDecimal price = tariff.getPrice(exceptionalDateTime);

        assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }

}