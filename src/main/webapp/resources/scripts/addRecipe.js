const addRecipe = new Vue({
    el: "#addRecipe",
    data: {
        products : null,
        recipeIngredients : [],
        recipeName : "",
        recipeDescription : ""
    },
    methods: {
        addRecipe() {
            if(this.recipeName !== ""){
                var recipe = {};
                recipe.name = this.recipeName;
                recipe.description = "description";
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
            setTimeout(this.reload, true, 200);
        },
        isNumber() {
            evt = window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46 && charCode !== 45) {
                evt.preventDefault();
            } else {
                return true;
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
    <div id="newRecipe" class="text-center">
        <h4>New recipe</h4>
        <div class="row col-6 offset-3 text-center" >
            <b-form-input type="text" v-model="recipeName" required placeholder="Recipe name">
            </b-form-input>

            <b-card-group columns class="col-10 offset-1">
                <b-card v-for="(product, i) in products" bg-variant="info" text-variant="white"
                        v-bind:header="product.name" class="text-center">
                    <p class="card-text">
                        <input class="form-control input-sm" type="text" v-model="recipeIngredients[i].amount"
                            placeholder="Enter amount" v-on:keypress="isNumber()">
                        </input>
                        {{product.baseUnit}}
                    </p> 
                </b-card>
                <button v-on:click="addRecipe()" class="btn btn-info btn-lg">Add recipe</button>
            </b-card-group>

        </div>
    </div>
    `,
});