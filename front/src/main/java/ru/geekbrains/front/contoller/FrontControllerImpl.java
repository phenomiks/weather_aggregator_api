package ru.geekbrains.front.contoller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.geekbrains.front.contoller.interfaces.FrontController;
import ru.geekbrains.front.service.interfaces.DispatcherService;
import ru.geekbrains.front.utils.OpenWeatherUtils;
import ru.geekbrains.front.utils.YandexWeatherUtils;

@Controller
public class FrontControllerImpl implements FrontController {
    private final DispatcherService dispatcherService;

    @Autowired
    public FrontControllerImpl(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @Override
    public String getIndexPage(Model model) {
        return "index";
    }

    @Override
    public String getApiPage() {
        return "api";
    }

    @Override
    public String requestWeather(String cityParam, Model model) {
        String city = cityParam.substring(cityParam.indexOf("=") + 1);
        if (city.isBlank()) {
            return "redirect:/";
        }

        ObjectNode response = dispatcherService.getWeather(city);
        if (response == null) {
            return "redirect:/";
        }

        model.addAttribute("openweather", OpenWeatherUtils.fillWeatherEntity(response));
        model.addAttribute("yandexweather", YandexWeatherUtils.fillWeatherEntity(response));

        return "index";
    }
}
