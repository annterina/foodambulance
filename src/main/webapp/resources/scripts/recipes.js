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
        <br>
        <h4>All recipes</h4>
        <ul class="list-group">
        <li class="list-group-item" v-for="recipe in recipeList">
              {{recipe.name}}
              <!--<li v-for="ingredient in recipe.ingredients">-->
                    <!--{{ingredient.name}} - {{ingredient.amount}} {{ingredient.baseUnit}}-->
              <!--</li>-->
              <button v-on:click="addRecipe(recipe.id)" class="btn btn-info"> Add this recipe to your account</button>
        </li>
        </ul>
        <br>
        <button v-on:click="showAllRecipes()" class="btn btn-info"> Show All Recipes </button> <br/>
        <br>
        <h4>My recipes</h4>
          <ul class="list-group">
          <li class="list-group-item" v-for="recipe in myRecipes">
              {{recipe.name}}
              <!--<li v-for="ingredient in recipe.ingredients">-->
                    <!--{{ingredient.name}} - {{ingredient.amount}} {{ingredient.baseUnit}}-->
              <!--</li>          -->
           </li>
           </ul>
          <br/>
          <button v-on:click="showMyRecipes(1)" class="btn btn-info"> Show My Recipes </button> <br/>
          <br>
          <h4>Planner</h4>
          <ul class="list-group">
          <li class="list-group-item" v-for="recipe in comparedRecipes">
              {{recipe.recipe.name}} : Missing {{recipe.missingProductsNumber}} products
               <!--<li class="list-group-item" v-for="product in recipe.missingProducts">-->
               <!--{{product.product.name}}-->
               <!--</li>-->
          </li>
          </ul>
          <br/>
                    <button v-on:click="showSortedRecipes(1)" class="btn btn-info"> What can I cook?</button>
          <br/>
        </div>
    `,
});