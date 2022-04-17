package com.wealthpilot.model;

import com.wealthpilot.persistence.domain.TeamEntity;

import javax.validation.constraints.NotBlank;

public record Game(@NotBlank TeamEntity home, @NotBlank TeamEntity away) {
}
