package com.igrejacristahangar.cantina.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorMessageDTO(
        @Schema(example = "id")
        String field,

        @Schema(example = "id não existe no banco")
        String message
) {}

