package com.devskiller.service;

import com.devskiller.dao.ItemRepository;
import com.devskiller.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<String> getTitlesWithAverageRatingLowerThan(Double rating) {
        List<Item> items = itemRepository.findItemsWithAverageRatingLowerThan(rating);
        return items.stream()
                .map(Item::getTitle)
                .collect(Collectors.toList());
    }

}