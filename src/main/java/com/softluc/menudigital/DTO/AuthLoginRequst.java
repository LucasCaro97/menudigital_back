package com.softluc.menudigital.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequst(@NotBlank String username,
                              @NotBlank String password) {
}
