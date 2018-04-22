const planner = new Vue({
    el: "#planner",
    data: {
        error: "",
        comparedRecipes: [{recipe:{name:""}, missingProducts:[], missingProductsNumber: 0}],
        recipe: {missingProducts:[]},
        dragging: false
    },
    methods: {
        showSortedRecipes(customerId){
            fetch("http://localhost:8080/customer/" + customerId + "/products/plan")
                .then(response => response.json())
                .then((data) => {
                    this.comparedRecipes = data;
                })
        },
    },
    beforeMount(){
        this.showSortedRecipes(1);
    },
    mounted() {
    },
    template: `
        <div>
            <div class="container">
                <draggable v-model="comparedRecipes" @start="dragging=true" @end="dragging=false">
                   <div  v-for="recipe in comparedRecipes" :key="recipe.recipe.id">
                   {{recipe.recipe.name}}
                   </div>
                </draggable>
            </div>
        </div>
    `,
});