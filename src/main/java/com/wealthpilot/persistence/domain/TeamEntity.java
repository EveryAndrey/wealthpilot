package com.wealthpilot.persistence.domain;

import javax.validation.constraints.NotBlank;

public record TeamEntity(@NotBlank String name, String foundingDate) {
}
