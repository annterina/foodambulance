package foodambulance.service;

import foodambulance.dao.*;
import foodambulance.model.*;
import foodambulance.prioritizer.ComparedRecipe;
import foodambulance.prioritizer.RecipeComparator;
import foodambulance.prioritizer.RecipePrioritizer;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    CustomerDAO customerDAO = mock(CustomerDAOImpl.class);
    ProductDAO productDAO = mock(ProductDAOImpl.class);
    RecipeDAO recipeDAO = mock(RecipeDAOImpl.class);

    @Test
    public void getPossibleRecipesOfCustomerOfIdTest(){
        //given

        CustomerService customerService = new CustomerServiceImpl(customerDAO, productDAO, recipeDAO);

        Customer mockedCustomer = mock(Customer.class);
        Recipe mockedRecipe = mock(Recipe.class);
        Product mockedProduct = mock(Product.class);
        CustomerProduct mockedCustomerProduct = mock(CustomerProduct.class);

        RecipeIngredient mockedIngredient = mock(RecipeIngredient.class);

        when(mockedIngredient.getProduct()).thenReturn(mockedProduct);
        when(mockedIngredient.getAmount()).thenReturn(2.0f);

        when(mockedRecipe.getIngredients()).thenReturn(new HashSet<>(Arrays.asList(mockedIngredient)));

        when(mockedCustomer.getRecipes()).thenReturn(new HashSet<>(Arrays.asList(mockedRecipe)));
        when(customerDAO.getCustomerOfId(1L)).thenReturn(mockedCustomer);

        when(mockedCustomer.getCustomerProducts()).thenReturn(new HashSet<>(Arrays.asList(mockedCustomerProduct)));

        when(mockedCustomerProduct.getProduct()).thenReturn(mockedProduct);
        when(mockedCustomerProduct.getAmount()).thenReturn(2.2f);

        when(mockedProduct.getId()).thenReturn(1L);

        when(mockedCustomerProduct.getNewestBuyDate()).thenReturn(LocalDateTime.now());

        //when
        final List<ComparedRecipe> possibleRecipesOfCustomerOfId = customerService.getPossibleRecipesOfCustomerOfId(1L);

        //then
        assertNotEquals(possibleRecipesOfCustomerOfId.size(), 0);
        assertEquals(possibleRecipesOfCustomerOfId.get(0).getMissingProductsNumber().intValue(), 0);

    }

}