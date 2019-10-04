package com.ezgroceries.shoppinglist;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingListApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSearchCocktails() throws Exception {
        mockMvc.perform(get("/cocktails")
                            .param("search", "Russian"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Margerita")))
                .andExpect(jsonPath("$[1].name", is("Blue Margerita"))
        );
    }

    @Test
    public void testCreateNewShoppingList() throws Exception {
        mockMvc.perform(post("/shopping-lists")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"name\": \"Stephanie's birthday\" }")
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Stephanie's birthday")));
    }

    @Test
    public void testAddCocktail() throws Exception {
        mockMvc.perform(post("/shopping-lists/97c8e5bd-5353-426e-b57b-69eb2260ace3/cocktails")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("[{\"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"}, {\"cocktailId\": \"d615ec78-fe93-467b-8d26-5d26d8eab073\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cocktailId", is("23b3d85a-3928-41c0-a533-6538a71e17c4")));
    }

    @Test
    public void testGetShoppingList() throws Exception {
        mockMvc.perform(get("/shopping-lists/eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Stephanie's birthday")));
    }

    @Test
    public void testGetAllShoppingLists() throws Exception {
        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Stephanie's birthday")))
                .andExpect(jsonPath("$[1].name", is("My Birthday")));
    }

}
