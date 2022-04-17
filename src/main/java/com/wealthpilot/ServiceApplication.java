package com.wealthpilot;

import com.wealthpilot.model.ScheduledGame;
import com.wealthpilot.service.SchedulerBuilderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ServiceApplication implements CommandLineRunner {

    private final SchedulerBuilderService service;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<ScheduledGame> scheduledGames = service.build();
        log.info("Date Team1 Team2");

        scheduledGames.forEach(item -> log.info("{}: {} vs {}",
                item.localDate(), item.game().home().name(), item.game().away().name()));

    }
}
