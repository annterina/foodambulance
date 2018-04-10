const addRecipe = new Vue({
    el: "#addRecipe",
    data: {
        products : null,
        recipeIngredients : [],
        recipeName : ""
    },
    methods: {
        addRecipe(){
            if(this.recipeName !== ""){
                var recipe = {};
                recipe.name = this.recipeName;
                recipe.recipeIngredientsIds = [];
                recipe.recipeIngredientsAmount = [];
                for (var i = 0; i < this.recipeIngredients.length; i++){
                    if (this.recipeIngredients[i].amount!==0) {
                        recipe.recipeIngredientsIds.push(this.recipeIngredients[i].product.id);
                        recipe.recipeIngredientsAmount.push(this.recipeIngredients[i].amount);
                    }
                }
                console.log(recipe);
                fetch("http://localhost:8080/recipes/new", {
                    body: JSON.stringify(recipe),
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then(() => {
                    })
            }
        }
    },
    mounted() {
        fetch("http://localhost:8080/products")
            .then(response => response.json())
            .then((data) => {
                this.products = data;
                for (var i = 0; i < data.length; i++){
                    var recipeIngredient = {};
                    recipeIngredient.product = data[i];
                    recipeIngredient.amount = 0;
                    this.recipeIngredients.push(recipeIngredient);
                }
            })
    },
    template: `
    <div>
            <div class="container">
            <h2>Adding new recipe</h2>
                <div class="row">
                     <div class="span6 offset3">
                        Name :<input v-model = "recipeName"/>
                        <h3>Choose products:</h3>
                        <li v-for="(product, i) in products">
                              {{product.name}} : <input v-model = "recipeIngredients[i].amount"/> {{product.baseUnit}}
                        </li>
                        <button v-on:click="addRecipe()" class="btn btn-default"> Add recipe</button>
                    </div>
                 </div>
            </div>
    </div>
    `,
});