package com.panoramichotel.api.model;

public enum Suites {

    PRESIDENTIAL_SUITE("presidential"),
    DELUXE("deluxe");

    String type;

    Suites(String type) {
        this.type = type;
    }
}
