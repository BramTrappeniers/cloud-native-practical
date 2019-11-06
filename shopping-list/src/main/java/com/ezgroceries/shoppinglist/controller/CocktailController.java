package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.dto.CocktailResource;
import com.ezgroceries.shoppinglist.dao.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.dao.external.CocktailDBResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cocktails")
public class CocktailController {

    @Autowired
    private CocktailDBClient cocktailDBClient;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CocktailResource> get(@RequestParam String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        return cocktailDBResponse.getDrinks().stream().map(drink -> {
            return new CocktailResource(drink.getIdDrink(), drink.getStrDrink(), drink.getStrGlass(),
                    drink.getStrInstructions(), drink.getStrDrinkThumb(),
                    Arrays.asList(drink.getStrIngredient1(), drink.getStrIngredient1(), drink.getStrIngredient1(), drink.getStrIngredient1(),
                            drink.getStrIngredient1(), drink.getStrIngredient2(), drink.getStrIngredient3(), drink.getStrIngredient4(),
                            drink.getStrIngredient5(), drink.getStrIngredient6(), drink.getStrIngredient7(), drink.getStrIngredient8(),
                            drink.getStrIngredient9(), drink.getStrIngredient10(), drink.getStrIngredient11(), drink.getStrIngredient12(),
                            drink.getStrIngredient13(), drink.getStrIngredient14(), drink.getStrIngredient15()));
        }).collect(Collectors.toList());
    }
}
