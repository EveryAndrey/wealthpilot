package com.wealthpilot.persistence.domain;


import java.util.List;

public record LeagueEntity(String league, String country, List<TeamEntity> teams) {
}
