package io.bmeurant.bookordermanager.application.dto;

import java.math.BigDecimal;

public record BookResponse(
    String isbn,
    String title,
    String author,
    BigDecimal price
) {
}
