const recipes = new Vue({
    el: "#recipes",
    data: {
        recipeList: [],
        myRecipes: [],
        customerId: -1,
        comparedRecipes: [],
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
        <br/>
        <div class="text-center">
            <button v-on:click="showSortedRecipes()" class="btn btn-info btn-lg">What can I cook?</button>
            <br/>
            <br/>
            <b-list-group class="col-8 offset-2">
                <b-list-group-item class="d-flex justify-content-between align-items-center" 
                    v-for="recipe in comparedRecipes">
                    {{recipe.recipe.name}}
                    <!--<li class="list-group-item" v-for="product in recipe.missingProducts">-->
                    <!--{{product.product.name}}-->
                    <!--</li>-->
                    <b-btn variant="danger" data-toggle="tooltip" data-placement="right" title="missing products">
                        {{recipe.missingProductsNumber}}
                    </b-btn>
                </b-list-group-item>
            </b-list-group>
            <br/>
            <br/>
        </div>
        </div>
    `,
});