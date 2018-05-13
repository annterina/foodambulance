const recipes = new Vue({
    el: "#recipes",
    data: {
        editFriend: null,
        recipeList: [],
        myRecipes: [],
        customerId: -1,
        comparedRecipes: [{recipe:{name:""}, missingProducts:[], missingProductsNumber: 0}],
        recipe: {missingProducts:[]}
    },
    methods: {
        showMyRecipes(){
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
        showSortedRecipes(){
            fetch("http://localhost:8080/customer/" + this.customerId + "/plan")
                .then(response => response.json())
                .then((data) => {
                    this.comparedRecipes = data;
                })
        },
        addRecipe(recipeId){
            fetch("http://localhost:8080/customer/" + this.customerId + "/recipes/" + recipeId, {
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
    beforeMount(){
        this.customerId = Cookies.get("customerId");
    },
    mounted() {
        this.showAllRecipes();
    },
    template: `
        <div>
        <div class="row">
            <b-card-group deck class="mb-3 col-8">
                <b-card v-for="recipe in recipeList" bg-variant="info" text-variant="white"
                        v-bind:header="recipe.name" class="text-center" footer-tag="footer">
                    <p class="card-text"> 
                        <ul>
                            <li v-for="ingredient in recipe.ingredients">{{ingredient.name}}: {{ingredient.amount}} {{ingredient.baseUnit}}
                            </li>
                        </ul>
                    </p> 
                    <em slot="footer">
                        <button id="addRecipeButton" v-on:click="addRecipe(recipe.id)" class="btn btn-info btn-lg"> 
                            +
                        </button>
                    </em>
                </b-card>
            </b-card-group>
            
            <div class="col-4 text-center">
                <button id="showMyRecipesButton" v-on:click="showMyRecipes()" class="btn btn-info btn-lg"> My recipes </button> 
                <br/>
                <ul class="list-group text-center">
                    <li class="list-group-item" v-for="recipe in myRecipes">
                      {{recipe.name}}
                      <li v-for="ingredient in recipe.ingredients">
                            {{ingredient.name}}: {{ingredient.amount}} {{ingredient.baseUnit}}
                      </li>          
                    </li>
                </ul>
            </div>
        </div>
        <br/>
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
                    <button v-on:click="showSortedRecipes()" class="btn btn-info"> What can I cook?</button>
          <br/>
        </div>
    `,
});