const recipes = new Vue({
    el: "#recipes",
    data: {
        editFriend: null,
        recipeList: [],
        myRecipes: [],
        customerId: 1,
    },
    methods: {
        showMyRecipes(id){
            fetch("http://localhost:8080/customer/{id}/recipes" + this.customerId)
                .then(response => response.json())
                .then((data) => {
                    this.myRecipes = data;
                })
        },
        showAllRecipes(){
            fetch("http://localhost:8080/recipes")
                .then(response => response.json())
                .then((data) => {
                    this.recipeList = data;
                })
        },
        addRecipe(recipeId){
            fetch("http://localhost:8080/customer/"+this.customerId+"/recipes/" + recipeId, {
                body: "",
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(() => {
                })
        }
    },
    mounted() {
        this.showAllRecipes()
    },
    template: `
        <div>
        <h2>All recipes</h2>
        <li v-for="recipe in recipeList">
              Name : {{recipe.name}}
              <!--<li v-for="ingredient in recipe.ingredients">-->
                    <!--{{ingredient.name}} - {{ingredient.amount}} {{ingredient.baseUnit}}-->
              <!--</li>-->
              <button v-on:click="addRecipe(recipe.id)" class="btn btn-default"> Add this recipe to your account</button>
        </li>
        
          <button v-on:click="showMyRecipes(1)" class="btn btn-default"> Show My Recipes </button>
          <li v-for="recipe in myRecipes">
              Name : {{recipe.name}}
              <!--<li v-for="ingredient in recipe.ingredients">-->
                    <!--{{ingredient.name}} - {{ingredient.amount}} {{ingredient.baseUnit}}-->
              <!--</li>          -->
           </li>
        </div>
    `,
});