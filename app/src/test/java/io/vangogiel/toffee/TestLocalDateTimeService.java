package io.vangogiel.toffee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

/** Test class for {@link LocalDateTimeService}. */
public class TestLocalDateTimeService {

  private final LocalDateTimeService localDateTimeService = new LocalDateTimeService();

  @Test
  public void testDefaultConstructor() {
    assertDoesNotThrow((ThrowingSupplier<LocalDateTimeService>) LocalDateTimeService::new);
  }

  @Test
  public void testConstructorWithClock() {
    assertDoesNotThrow(() -> new LocalDateTimeService(Clock.systemDefaultZone()));
  }

  @Test
  public void testTimeNow() {
    assertNotNull(localDateTimeService.timeNow());
    assertEquals(LocalTime.now().getHour(), localDateTimeService.timeNow().getHour());
    assertEquals(LocalTime.now().getMinute(), localDateTimeService.timeNow().getMinute());
    assertEquals(LocalTime.now().getSecond(), localDateTimeService.timeNow().getSecond());
  }

  @Test
  public void testDateNow() {
    assertNotNull(localDateTimeService.dateNow());
    assertEquals(LocalDate.now().getDayOfWeek(), localDateTimeService.dateNow().getDayOfWeek());
    assertEquals(LocalDate.now().getDayOfMonth(), localDateTimeService.dateNow().getDayOfMonth());
    assertEquals(LocalDate.now().getMonth(), localDateTimeService.dateNow().getMonth());
    assertEquals(LocalDate.now().getYear(), localDateTimeService.dateNow().getYear());
  }
}
