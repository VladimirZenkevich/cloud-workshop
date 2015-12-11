package com.altoros.catalog.controllers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by uladzimir.ziankevich on 12/11/2015.
 */
@Controller
@RequestMapping(value = "/catalog")
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/stock";

        List<LinkedHashMap<String, Object>> items = restTemplate.getForObject(url, List.class);
        model.addAttribute("stockItems", convertToDTOs(items));

        return "home";
    }

    private List<StockItemDTO> convertToDTOs(List<LinkedHashMap<String, Object>> list) {

        ArrayList<StockItemDTO> dtos = Lists.newArrayList();

        for (LinkedHashMap<String, Object> entry : list) {
            dtos.add(convertToDTO(entry));
        }

        return dtos;
    }

    private StockItemDTO convertToDTO(LinkedHashMap<String, Object> properties) {
        StockItemDTO dto = new StockItemDTO();

        dto.setId(new Long(properties.get("id").toString()));
        dto.setTitle(properties.get("title").toString());

        return dto;
    }

}
