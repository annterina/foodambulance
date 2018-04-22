import draggable from 'vuedraggable';

const planner = new Vue({
    el: "#planner",
    data: {
        error: "",
        comparedRecipes: [{recipe:{name:""}, missingProducts:[], missingProductsNumber: 0}],
        recipe: {missingProducts:[]},
        components: {
            draggable
        }
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
                <draggable v-model="comparedRecipes">
                    <ul>
                        <li v-for="recipe in comparedRecipes">
                           {{recipe.recipe.name}} : Missing {{recipe.missingProductsNumber}} products:
                           <li v-for="product in recipe.missingProducts">{{product.product.name}}</li>
                        </li>
                    </ul>
                </draggable>
            </div>
        </div>
    `,
});