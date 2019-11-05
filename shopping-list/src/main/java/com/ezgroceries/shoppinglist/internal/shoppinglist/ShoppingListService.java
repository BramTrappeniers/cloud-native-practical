package com.ezgroceries.shoppinglist.internal.shoppinglist;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public ShoppingListResource create(ShoppingListResource shoppingListResource) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setName(shoppingListResource.getName());
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        shoppingListResource.setShoppingListId(shoppingListEntity.getId());
        return shoppingListResource;
    }

    public void addCocktailsToShoppingList(UUID shoppingListId, List<CocktailResource> cocktails) {
        List<CocktailEntity> cocktailEntityList = cocktailRepository.findByIdDrinkIn(cocktails.stream().map(CocktailResource::getCocktailId).collect(
                Collectors.toList()));
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(shoppingListId).orElse(null);
        if(shoppingListEntity != null) {
            shoppingListEntity.getCocktails().addAll(cocktailEntityList);
            shoppingListRepository.save(shoppingListEntity);
        }
    }

    public ShoppingListResource findShoppingListById(UUID shoppinglistId) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(shoppinglistId).orElse(null);
        if(shoppingListEntity != null) {
            List<CocktailEntity> cocktailEntityList = shoppingListEntity.getCocktails();
            List<String> cocktailIngredients = new ArrayList<>();
            for(CocktailEntity cocktailEntity : cocktailEntityList) {
                for(String ingredient : cocktailEntity.getIngredients()) {
                    cocktailIngredients.add(ingredient);
                }
            }
            return new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(), cocktailIngredients);
        } else {
            return null;
        }
//        return shoppingListRepository.findById(shoppinglistId)
//                .map(shoppingListEntity -> new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(),
//                        shoppingListEntity.getCocktails().stream()
//                                .map(cocktailEntity -> cocktailEntity.getIngredients().stream()).collect(Collectors.toList())))
//                .orElse(null);
    }

    public List<ShoppingListResource> findAllShoppingLists() {
        List<ShoppingListEntity> shoppingListEntityList = (List<ShoppingListEntity>) shoppingListRepository.findAll();
        if(shoppingListEntityList != null) {
            List<ShoppingListResource> shoppingListResourceList = new ArrayList<>();
            for(ShoppingListEntity shoppingListEntity : shoppingListEntityList) {
                List<CocktailEntity> cocktailEntityList = shoppingListEntity.getCocktails();
                List<String> cocktailIngredients = new ArrayList<>();
                for(CocktailEntity cocktailEntity : cocktailEntityList) {
                    for(String ingredient : cocktailEntity.getIngredients()) {
                        cocktailIngredients.add(ingredient);
                    }
                }
                shoppingListResourceList.add(new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(), cocktailIngredients));
            }
            return  shoppingListResourceList;
        } else {
            return null;
        }
//        return shoppingListEntityList.stream()
//                .map(shoppingListEntity -> new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName(),
//                    shoppingListEntity.getCocktails().stream()
//                            .map(cocktailEntity -> cocktailEntity.getIngredients().stream()).collect(Collectors.toList())))
//                .collect(Collectors.toList());
    }
}
