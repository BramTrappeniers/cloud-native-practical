package com.ezgroceries.shoppinglist.internal.shoppinglist;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingListController {
    List<UUID> DUMMY_COCKTAILS = Arrays.asList(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"),
            UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"));

    @GetMapping(value = "/shopping-lists")
    public List<ShoppingListResource> get() {
        return getDummyShoppingLists();
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ShoppingListResource findByName(@PathVariable String shoppingListId) {
        return getDummyShoppingList();
    }

    @PostMapping(value = "/shopping-lists")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ShoppingListResource createList(@RequestBody ShoppingListResource newShoppingList) {
        return new ShoppingListResource(UUID.randomUUID(), newShoppingList.getName(), Collections.EMPTY_LIST);
    }

    @PostMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public List<CocktailResource> addCocktails(@PathVariable UUID shoppingListId, @RequestBody List<CocktailResource> cocktails) {
        return cocktails;
    }

    private List<ShoppingListResource> getDummyShoppingLists() {
        ShoppingListResource shoppingList1 = new ShoppingListResource(UUID.randomUUID(), "Stephanie's birthday", DUMMY_COCKTAILS);

        ShoppingListResource shoppingList2 = new ShoppingListResource(UUID.randomUUID(), "My Birthday", DUMMY_COCKTAILS);

        return Arrays.asList(shoppingList1, shoppingList2);
    }

    private ShoppingListResource getDummyShoppingList() {
        return new ShoppingListResource(UUID.randomUUID(), "Stephanie's birthday", DUMMY_COCKTAILS);
    }
}
