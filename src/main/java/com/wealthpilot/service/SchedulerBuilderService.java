package com.wealthpilot.service;

import com.wealthpilot.model.Game;
import com.wealthpilot.model.ScheduledGame;
import com.wealthpilot.persistence.domain.TeamEntity;
import com.wealthpilot.persistence.repository.TeamsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Validated
public class SchedulerBuilderService {
    private static final int WEEK_SIZE = 7;

    private final TeamsRepository teamsRepository;
    private final LocalDate startDate;
    private final int breakDurationWeeks;
    private final String dummyTeamName;

    public SchedulerBuilderService(TeamsRepository teamsRepository,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Value("${app.start-date}") LocalDate startDate,
                                   @Value("${app.break-duration-weeks}") int breakDurationWeeks,
                                   @Value("${app.dummy-team-name:DUMMY}") String dummyTeamName
    ) {
        this.teamsRepository = teamsRepository;
        this.startDate = startDate;
        this.breakDurationWeeks = breakDurationWeeks;
        this.dummyTeamName = dummyTeamName;
    }

    public List<ScheduledGame> build() {

        List<@Valid TeamEntity> teams = new LinkedList<>(teamsRepository.findAll());
        List<ScheduledGame> scheduledGames = new ArrayList<>();

        TeamEntity firstElement = teams.size() % 2 == 0 ? teams.get(0) : new TeamEntity(dummyTeamName, StringUtils.EMPTY);
        List<TeamEntity> tail = teams.size() % 2 == 0 ? teams.subList(1, teams.size()) :
                teams.subList(0, teams.size());
        int rounds = teams.size() % 2 == 0 ? teams.size() - 1 : teams.size();
        int half = tail.size() / 2;

        for (int r = 0; r < rounds; r++) {
            scheduledGames.add(new ScheduledGame(
                    startDate.plusDays((long) r * WEEK_SIZE),
                    new Game(firstElement, tail.get(tail.size() - 1))));
            for (int i = 0; i < half; i++) {
                scheduledGames.add(new ScheduledGame(
                        startDate.plusDays((long) r * WEEK_SIZE),
                        new Game(tail.get(i), tail.get(tail.size() - i - 2))));
            }
            tail.add(0, tail.remove(tail.size() - 1));
        }

        LocalDate secondRoundStartDate = startDate
                .plusDays((long) (rounds - 1) * WEEK_SIZE)
                .plusDays((long) breakDurationWeeks * WEEK_SIZE);

        return scheduledGames.stream()
                .filter(scheduledGame -> !dummyTeamName.equals(scheduledGame.game().home().name()))
                .filter(scheduledGame -> !dummyTeamName.equals(scheduledGame.game().away().name()))
                .flatMap(scheduledGame ->
                        Stream.of(scheduledGame,
                                new ScheduledGame(secondRoundStartDate
                                        .plusDays(startDate.until(scheduledGame.localDate()).getDays()),
                                        new Game(scheduledGame.game().away(), scheduledGame.game().home())))
                )
                .sorted(Comparator.comparing(ScheduledGame::localDate))
                .collect(Collectors.toList());
    }
}
