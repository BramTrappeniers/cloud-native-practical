package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.dto.CocktailResource;
import com.ezgroceries.shoppinglist.dao.external.CocktailDBResponse;
import com.ezgroceries.shoppinglist.dao.external.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.model.CocktailEntity;
import com.ezgroceries.shoppinglist.dao.CocktailRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CocktailService {

    private CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public List<CocktailResource> mergeCocktails(List<DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getIdDrink(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), getIngredients(drinkResource))).collect(Collectors.toList());
    }

    private List<String> getIngredients(DrinkResource drink) {
        return Arrays.asList(drink.getStrIngredient1(), drink.getStrIngredient1(), drink.getStrIngredient1(), drink.getStrIngredient1(),
                drink.getStrIngredient1(), drink.getStrIngredient2(), drink.getStrIngredient3(), drink.getStrIngredient4(),
                drink.getStrIngredient5(), drink.getStrIngredient6(), drink.getStrIngredient7(), drink.getStrIngredient8(),
                drink.getStrIngredient9(), drink.getStrIngredient10(), drink.getStrIngredient11(), drink.getStrIngredient12(),
                drink.getStrIngredient13(), drink.getStrIngredient14(), drink.getStrIngredient15());
    }

}