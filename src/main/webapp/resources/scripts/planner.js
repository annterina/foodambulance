const planner = new Vue({
    el: "#planner",
    data: {
        customerId : -1,
        error: "",
        comparedRecipes: [],
        plannedRecipes0: [],
        plannedRecipes1: [],
        plannedRecipes2: [],
        plannedRecipes3: [],
        plannedRecipes4: [],
        plannedRecipes5: [],
        plannedRecipes6: [],
        plannedRecipe: {date: "", customerId: -1, recipeId:-1},
        weekDays: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        renderedCards: {zero: true, one: true, two: true, three: true, four: true, five: true, six: true}
    },
    methods: {
        addComparedRecipes() {
            fetch("http://localhost:8080/customer/" + this.customerId + "/plan")
                .then(response => response.json())
                .then((data) => {
                    this.comparedRecipes = data;
                })
        },
        chooseRecipe: function (event) {
            this.plannedRecipe.recipeId = event.draggedContext.element.recipe.id;
        },
        chooseDate: function (event) {
            this.plannedRecipe.date = new Date().setDate(new Date().getDate() + parseInt(event.to.id));
            this.sendPlannedRecipe();
        },
        sendPlannedRecipe() {
            if (this.plannedRecipe.customerId > -1 && this.plannedRecipe.recipeId > -1) {
                fetch("http://localhost:8080/customer/" + this.customerId + "/plan", {
                    body: JSON.stringify(this.plannedRecipe),
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then(() => {
                        this.error = "Error during sending planned recipe."
                    })
            }
            else {
                this.error = "Fill all fields."
            }
            this.addComparedRecipes();
        }
    },
    beforeMount(){
        this.customerId = Cookies.get("customerId");
        this.plannedRecipe.customerId = this.customerId;
        this.addComparedRecipes();
    },
    mounted() {
    },
    template: `
    <div class="container">
        <div class="row">
            <div class="col-2">
                <div class="drag">
                    <h4>Suggested recipes</h4>
                    <draggable v-model="comparedRecipes" class="dragArea" :move="chooseRecipe"
                    :options="{group:{ name:'people',  pull:'clone', put:false }}">
                        <div v-for="element in comparedRecipes">{{element.recipe.name}}</div>
                    </draggable>
                </div>
            </div>
            <div class="col-10">
                <div>
                    <b-card-group columns>
                        <b-card v-if="renderedCards.zero" v-bind:header="this.weekDays[new Date().getDay() % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row justify-content-md-center">
                                <b-button class="close close-button" v-on:click="renderedCards.zero = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="0" v-model="plannedRecipes0" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes0">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.one" v-bind:header="this.weekDays[(new Date().getDay()+1) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.one = false"> &times; </b-button>
                            </div>                
                            <draggable v-bind:id="1" v-model="plannedRecipes1" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes1">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.two" v-bind:header="this.weekDays[(new Date().getDay()+2) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.two = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="2" v-model="plannedRecipes2" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes2">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.three" v-bind:header="this.weekDays[(new Date().getDay()+3) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.three = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="3" v-model="plannedRecipes3" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes3">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.four" v-bind:header="this.weekDays[(new Date().getDay()+4) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.four = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="4" v-model="plannedRecipes4" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes4">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.five" v-bind:header="this.weekDays[(new Date().getDay()+5) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.five = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="5" v-model="plannedRecipes5" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes5">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                      
                        <b-card v-if="renderedCards.six" v-bind:header="this.weekDays[(new Date().getDay()+6) % 7]" 
                                style="max-width: 25rem;" class="mb-2" border-variant="info"  
                                header-text-variant="white" header-bg-variant="info">
                            <div class="row">
                                <b-button class="close close-button" v-on:click="renderedCards.six = false"> &times; </b-button>
                            </div>
                            <draggable v-bind:id="6" v-model="plannedRecipes6" class="dragArea" :options="{group:'people'}"
                                    @add="chooseDate">
                                <div v-for="element in plannedRecipes6">{{element.recipe.name}}</div>
                            </draggable>
                        </b-card>
                    </b-card-group>
                    <img id="plannerFooter" src="images/food_icon.png">
                </div>
            </div>
        </div>
    </div>
    `,
});