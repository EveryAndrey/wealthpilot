package com.wealthpilot.service;

import com.wealthpilot.model.Game;
import com.wealthpilot.model.ScheduledGame;
import com.wealthpilot.persistence.domain.TeamEntity;
import com.wealthpilot.persistence.repository.TeamsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

class SchedulerBuilderServiceTest {
    private static final LocalDate START_DATE = LocalDate.of(2022, 4, 16);

    @Test
    void buildOddSchedule() {

        TeamEntity a = new TeamEntity("A", null);
        TeamEntity b = new TeamEntity("B", null);
        TeamEntity c = new TeamEntity("C", null);

        TeamsRepository teamsRepository = Mockito.mock(TeamsRepository.class);
        when(teamsRepository.findAll()).thenReturn(List.of(a, b, c));

        List<ScheduledGame> expected =
                List.of(
                        new ScheduledGame(START_DATE, new Game(a, b)),
                        new ScheduledGame(START_DATE.plusDays(7), new Game(c, a)),
                        new ScheduledGame(START_DATE.plusDays(14), new Game(b, c)),
                        new ScheduledGame(START_DATE.plusDays(28), new Game(b, a)),
                        new ScheduledGame(START_DATE.plusDays(35), new Game(a, c)),
                        new ScheduledGame(START_DATE.plusDays(42), new Game(c, b))
                );

        SchedulerBuilderService service = new SchedulerBuilderService(teamsRepository, START_DATE, 2, "DUMMY");
        List<ScheduledGame> actual = service.build();
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void buildEvenSchedule() {
        TeamEntity a = new TeamEntity("A", null);
        TeamEntity b = new TeamEntity("B", null);
        TeamEntity c = new TeamEntity("C", null);
        TeamEntity d = new TeamEntity("D", null);

        TeamsRepository teamsRepository = Mockito.mock(TeamsRepository.class);
        when(teamsRepository.findAll()).thenReturn(List.of(a, b, c, d));

        List<ScheduledGame> expected =
                List.of(
                        new ScheduledGame(START_DATE, new Game(a, d)),
                        new ScheduledGame(START_DATE, new Game(b, c)),
                        new ScheduledGame(START_DATE.plusDays(7), new Game(a, c)),
                        new ScheduledGame(START_DATE.plusDays(7), new Game(d, b)),
                        new ScheduledGame(START_DATE.plusDays(14), new Game(a, b)),
                        new ScheduledGame(START_DATE.plusDays(14), new Game(c, d)),
                        new ScheduledGame(START_DATE.plusDays(28), new Game(d, a)),
                        new ScheduledGame(START_DATE.plusDays(28), new Game(c, b)),
                        new ScheduledGame(START_DATE.plusDays(35), new Game(c, a)),
                        new ScheduledGame(START_DATE.plusDays(35), new Game(b, d)),
                        new ScheduledGame(START_DATE.plusDays(42), new Game(b, a)),
                        new ScheduledGame(START_DATE.plusDays(42), new Game(d, c))
                );

        SchedulerBuilderService service = new SchedulerBuilderService(teamsRepository, START_DATE, 2, "DUMMY");
        List<ScheduledGame> actual = service.build();
        Assertions.assertIterableEquals(expected, actual);

    }

}