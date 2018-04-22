const recipes = new Vue({
    el: "#recipes",
    data: {
        editFriend: null,
        recipeList: [],
        myRecipes: [],
        customerId: 1,
        comparedRecipes: [{recipe:{name:""}, missingProducts:[], missingProductsNumber: 0}],
        recipe: {missingProducts:[]}
    },
    methods: {
        showMyRecipes(id){
            fetch("http://localhost:8080/customer/" + this.customerId + "/recipes")
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
        showSortedRecipes(customerId){
            fetch("http://localhost:8080/customer/" + this.customerId + "/plan")
                .then(response => response.json())
                .then((data) => {
                    this.comparedRecipes = data;
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
        <br>
        <button v-on:click="showAllRecipes()" class="btn btn-default"> Show All Recipes </button> <br/>
        <br>
        <h2>My recipes</h2>
          <button v-on:click="showMyRecipes(1)" class="btn btn-default"> Show My Recipes </button> <br/>
          <li v-for="recipe in myRecipes">
              Name : {{recipe.name}}
              <!--<li v-for="ingredient in recipe.ingredients">-->
                    <!--{{ingredient.name}} - {{ingredient.amount}} {{ingredient.baseUnit}}-->
              <!--</li>          -->
           </li>
          <br/>
          <h2>Planner</h2>
          <button v-on:click="showSortedRecipes(1)" class="btn btn-default"> What can I cook?</button>
          <li v-for="recipe in comparedRecipes">
              {{recipe.recipe.name}} : Missing {{recipe.missingProductsNumber}} products:
               <li v-for="product in recipe.missingProducts">
               {{product.product.name}}
               </li>
          </li>
        </div>
    `,
});