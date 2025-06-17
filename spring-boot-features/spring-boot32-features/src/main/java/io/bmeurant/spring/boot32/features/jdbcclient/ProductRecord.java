package io.bmeurant.spring.boot32.features.jdbcclient;

import java.math.BigDecimal;

public record ProductRecord(Long id, String name, BigDecimal price) {
}
