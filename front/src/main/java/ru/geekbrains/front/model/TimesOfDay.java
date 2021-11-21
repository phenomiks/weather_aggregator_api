package ru.geekbrains.front.model;

public enum TimesOfDay {
    MORNING("Morning"),
    DAY("Day"),
    EVENING("Evening"),
    NIGHT("Night");

    private final String alias;

    TimesOfDay(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
