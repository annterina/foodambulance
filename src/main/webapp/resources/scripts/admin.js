const admin = new Vue({
    el: "#admin",
    data: {
        error:"",
        newProduct : {name: "", categoryId : "", baseUnit:"", baseAmount:"", baseExpirationDate:""},
        products : null,
        recipeIngredients : [],
        recipeName : ""
    },
    methods: {
        addProduct() {
            if (this.newProduct.name !== "" && this.newProduct.categoryId !== "" && this.newProduct.baseUnit !== ""
                && this.newProduct.baseAmount !== "" && this.newProduct.baseExpirationDate !== "") {
                fetch("http://localhost:8080/products/new", {
                    body: JSON.stringify(this.newProduct),
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then(() => {
                        this.error = ""

                    })
            }
            else {
                this.error = "Fill all fields."
            }
        },
        addRecipe(){
            if(this.recipeName !== ""){
                var recipe = {};
                recipe.name = this.recipeName;
                recipe.ingredientIds = [];
                recipe.ingredientAmounts = [];
                for (var i = 0; i < this.recipeIngredients.length; i++){
                    if (this.recipeIngredients[i].amount!==0) {
                        recipe.ingredientIds.push(this.recipeIngredients[i].product.id);
                        recipe.ingredientAmounts.push(this.recipeIngredients[i].amount);
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
            <div>
            <h2>Adding new product: </h2> <br/>
            Name: <input v-model="newProduct.name" required/> <br/>
            Category Id: <input v-model="newProduct.categoryId" required/> <br/>
            Base Unit: <input v-model="newProduct.baseUnit" required/> <br/>
            Base Amount: <input v-model="newProduct.baseAmount" required/> <br/>
            Base Expiration Date (Days): <input v-model="newProduct.baseExpirationDate" required/> <br/>
            <button v-on:click="addProduct()" class="btn btn-default"> Add product</button>
            {{error}}
            <br/>
            </div>
            
            <div>
            <h2>Adding new recipe</h2>
            <input v-model = "recipeName"/>
            <li v-for="(product, i) in products">
                  Name : {{product.name}}
                  Unit : {{product.baseUnit}}
                  Base Amount : {{product.baseAmount}}
                  Category id : {{product.categoryId}}
                  Amount in recipe: <input v-model = "recipeIngredients[i].amount"/>
            </li>
            <button v-on:click="addRecipe()" class="btn btn-default"> Add recipe</button>
            </div>
     </div>
    `,
});