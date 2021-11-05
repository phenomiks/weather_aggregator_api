package ru.geekbrains.front.service.interfaces;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface DispatcherService {
    ObjectNode getWeather(String city);
}
