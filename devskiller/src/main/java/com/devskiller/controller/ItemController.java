package com.devskiller.controller;

import com.devskiller.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/titles", produces = "application/json; charset=UTF-8")
    public List<String> getTitles(Double rating) {
        return itemService.getTitlesWithAverageRatingLowerThan(rating);
    }
}
