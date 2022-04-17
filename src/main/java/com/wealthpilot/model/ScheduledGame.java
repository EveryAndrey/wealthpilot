package com.wealthpilot.model;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record ScheduledGame(@NotNull LocalDate localDate, @NotNull Game game) {
}
