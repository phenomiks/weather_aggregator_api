package ru.geekbrains.api.loader_api.service;

import java.util.Optional;

interface Loader {
    Optional<String> getByCityName(String cityName);
}
