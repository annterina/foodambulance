package foodambulance.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import foodambulance.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/recipes/new",method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @CrossOrigin
    public void addRecipe(@RequestBody String recipeBody, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        boolean result = recipeService.addRecipe(recipeBody);
        if (result) response.setStatus(HttpServletResponse.SC_OK);
        else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getRecipes(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(recipeService.getRecipes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }
}
