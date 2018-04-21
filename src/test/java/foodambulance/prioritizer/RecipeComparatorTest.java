package foodambulance.prioritizer;

import foodambulance.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RecipeComparatorTest {
    @Test
    public void compareTwoRecipes(){
        //given
        ComparedRecipe recipe1 = mock(ComparedRecipe.class);
        ComparedRecipe recipe2 = mock(ComparedRecipe.class);
        when(recipe1.getMissingProductsNumber()).thenReturn(1);
        when(recipe2.getMissingProductsNumber()).thenReturn(0);
        RecipeComparator recipeComparator = new RecipeComparator();

        //when
        int comparition = recipeComparator.compare(recipe1, recipe2);
        int backwardComparition = recipeComparator.compare(recipe2, recipe1);


        //then
        assertEquals(comparition, 1);
        assertEquals(backwardComparition, -1);
    }
}
