package com.Microblogs.utility;


import com.Microblogs.exception.RequestValidationException;

import java.util.UUID;

public class UUIDHelper {

    private UUIDHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static UUID parseUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new RequestValidationException("Invalid UUID format");
        }
    }
}
