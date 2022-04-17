package com.wealthpilot.persistence.repository;

import com.wealthpilot.configuration.MapperConfig;
import com.wealthpilot.persistence.domain.TeamEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamsRepositoryTest {

    @Test
    void findAll() {
        MapperConfig mapperConfig = new MapperConfig();
        TeamsRepository teamsRepository = new TeamsRepository("db.json", mapperConfig.objectMapper());

        List<TeamEntity> all = teamsRepository.findAll();
        assertEquals(6, all.size());
        assertEquals("Volksbank Kickers", all.get(0).name());
    }

}