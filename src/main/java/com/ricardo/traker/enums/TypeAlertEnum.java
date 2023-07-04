package com.ricardo.traker.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;
import java.util.stream.Stream;

public enum TypeAlertEnum {

    SPEED("SPEED"),

    ARRIVAL("ARRIVAL"),

    DISTANCE("DISTANCE");

    private String value;

    TypeAlertEnum (String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Optional<TypeAlertEnum> fromValue(String text) {
        return Stream.of(TypeAlertEnum.values()).filter(p -> p.value.equals(text)).findAny();
    }
}
