package foodambulance.prioritizer;

import java.util.Comparator;

public class RecipeComparator implements Comparator<ComparedRecipe> {

    @Override
    public int compare(ComparedRecipe comparedRecipe1, ComparedRecipe comparedRecipe2) {
        if (comparedRecipe1.getMissingProductsNumber() > comparedRecipe2.getMissingProductsNumber()) return 1;
        else if (comparedRecipe1.getMissingProductsNumber() < comparedRecipe2.getMissingProductsNumber()) return -1;
        else {
            if (comparedRecipe2.getNewestBuyDate().isBefore(comparedRecipe1.getNewestBuyDate())) return 1;
            else return -1;
        }
    }
}
