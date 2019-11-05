package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.Repository;

public interface CocktailRepository extends Repository<CocktailEntity, UUID> {

    <S  extends CocktailEntity> S save(S entity);

    List<CocktailEntity> findByIdDrinkIn(List<String> ids);
}
