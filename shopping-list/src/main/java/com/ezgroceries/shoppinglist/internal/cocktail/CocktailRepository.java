package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {

    List<CocktailEntity> findByIdDrinkIn(List<String> ids);
}
