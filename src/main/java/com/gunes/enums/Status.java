package com.gunes.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Status {

    ACTIVE,

    PASSIVE;

    public static boolean isValidStatus(final String status) {
        return Arrays.stream(Status.values())
                .map(Status::name)
                .collect(Collectors.toSet())
                .contains(status);
    }

}
