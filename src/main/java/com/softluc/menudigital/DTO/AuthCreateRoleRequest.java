package com.softluc.menudigital.DTO;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(@Size(max = 1, message = "El usuario no puede tener mas de 1 rol") List<String> roleListName) {
}
