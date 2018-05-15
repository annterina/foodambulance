const addRecipe = new Vue({
    el: "#addRecipe",
    data: {
        products : null,
        recipeIngredients : [],
        recipeName : "",
        recipeDescription : ""
    },
    methods: {
        addRecipe(){
            if(this.recipeName !== ""){
                var recipe = {};
                recipe.name = this.recipeName;
                recipe.description = this.recipeDescription;
                console.log(Cookies.get("customerId"));
                if (Cookies.get("customerId")!=="-1"){
                    console.log("Setting customer id");
                    recipe.customerId = Cookies.get("customerId");
                }
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
            <br>
            <h4>Adding new recipe</h4>
                <div class="row" >
                     <div class="span6 offset3" >
                        Name :<input v-model = "recipeName"/>
                        <h5>Choose products:</h5>
                        <ul class="list-group">
                            <li class="list-group-item" v-for="(product, i) in products">
                                  {{product.name}} : <input v-model = "recipeIngredients[i].amount"/> {{product.baseUnit}}
                            </li>
                        </ul>
                        Description : <textarea v-model = "recipeDescription"></textarea>
                        <button v-on:click="addRecipe()" class="btn btn-info"> Add recipe</button>
                    </div>
                 </div>
            </div>
    </div>
    `,
});