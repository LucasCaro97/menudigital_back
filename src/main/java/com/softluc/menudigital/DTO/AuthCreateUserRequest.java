package com.softluc.menudigital.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String commerceName,
                                    @NotBlank Long plan,
                                    @NotBlank Long phone,
                                    @NotBlank String password,
                                    @Valid AuthCreateRoleRequest roleRequest,
                                    @Valid Long provincia,
                                    @Valid Long ciudad,
                                    @Valid String direccion,
                                    MultipartFile logo) {
}
