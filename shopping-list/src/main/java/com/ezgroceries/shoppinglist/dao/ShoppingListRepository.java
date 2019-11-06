package com.ezgroceries.shoppinglist.dao;

import com.ezgroceries.shoppinglist.model.ShoppingListEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingListRepository extends CrudRepository<ShoppingListEntity, UUID> {

}
