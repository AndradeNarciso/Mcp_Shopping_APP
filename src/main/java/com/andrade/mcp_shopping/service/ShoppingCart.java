package com.andrade.mcp_shopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.andrade.mcp_shopping.dtos.ShoppingItem;

@Service
public class ShoppingCart {

    private final Map<String, ShoppingItem> ShoppingList = new ConcurrentHashMap<>();

    @Tool(name = "addItem", description = "add an item to the shopping list or update its quantity. Specify the item name and quantity.")
    public String additem(String name, Integer quantity) {
        if (name == null || name.trim().isEmpty() || quantity <= 0) {
            return "Name or quantity invalid";
        }

        ShoppingList.compute(name.toLowerCase(), (key, item) -> {
            if (item != null) {
                return new ShoppingItem(item.name(), item.quantity() + quantity);
            }

            return new ShoppingItem(key, quantity);
        });
        return quantity + " unit of " + name + " added";
    }

    @Tool(name = "getItem", description = "Get all items currently in the shopping list. Return a list of items their name ond quantities.")
    public List<ShoppingItem> getItems() {

        return new ArrayList<>(ShoppingList.values());
    }

    @Tool(name = "removeItem", description = "Remove a specific quantity of  an item from the shopping list, Specify item name and quantity to remove. If quantity is not specified or is greater  than item quantity the item is removed")
    public String removeItem(String name, Integer quantity) {
        if (name == null || name.trim().isEmpty()) {
            return "Item: " + name + " was not found at Shopping list";
        }

        ShoppingItem item = ShoppingList.get(name.toLowerCase());

        if (quantity <= 0 || quantity > item.quantity()) {
            return "Invalid quantity";
        }

        ShoppingList.compute(name.toLowerCase(), (key, currentItem) -> {
            if (currentItem.quantity() - quantity != 0) {
                return new ShoppingItem(currentItem.name(), currentItem.quantity() - quantity);
            }

            return null;
        });

        return "Item: " + name + " removed successfully";
    }
}
