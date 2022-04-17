package com.wealthpilot.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wealthpilot.persistence.domain.LeagueEntity;
import com.wealthpilot.persistence.domain.TeamEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class TeamsRepository {

    private final String url;
    private final ObjectMapper objectMapper;

    public TeamsRepository(@Value("${app.db-url}") String url, ObjectMapper objectMapper) {
        this.url = url;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public List<@Valid TeamEntity> findAll() {
        return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream(url), LeagueEntity.class)
                .teams();
    }
}
