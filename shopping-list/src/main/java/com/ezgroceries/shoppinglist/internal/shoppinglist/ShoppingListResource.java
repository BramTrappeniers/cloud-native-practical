package com.ezgroceries.shoppinglist.internal.shoppinglist;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import java.util.List;
import java.util.UUID;

public class ShoppingListResource {
    private UUID shoppingListId;
    private String name;
    private List<String> cocktailResourceList;

    public ShoppingListResource(UUID shoppingListId, String name, List<String> cocktailResourceList) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktailResourceList = cocktailResourceList;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCocktailResourceList() {
        return cocktailResourceList;
    }

    public void setCocktailResourceList(List<String> cocktailResourceList) {
        this.cocktailResourceList = cocktailResourceList;
    }
}
