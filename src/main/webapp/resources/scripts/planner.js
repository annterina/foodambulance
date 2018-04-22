const planner = new Vue({
    el: "#planner",
    data: {
        error: "",
        comparedRecipes: [],
        plannedRecipes: [],
    },
    methods: {
        addComparedRecipes() {
            this.comparedRecipes = [{recipe:{name:"Naleśniki"}},
                {recipe:{name:"Jajecznica"}}];
        }
    },
    beforeMount(){
        this.addComparedRecipes();
    },
    mounted() {
    },
    template: `
        <div>
          <div class="drag">
            <h2>List 1 Draggable</h2>
            <draggable v-model="comparedRecipes" class="dragArea" :options="{group:'people'}">
              <div v-for="element in comparedRecipes">{{element.recipe.name}}</div>
            </draggable>
          </div>
          <div>
            <b-card-group deck>
              <b-card title="Monday" style="max-width: 20rem;" class="mb-2">
                <draggable v-model="plannedRecipes" class="dragArea" :options="{group:'people'}">
                  <div v-for="element in plannedRecipes">{{element.recipe.name}}</div>
                </draggable>
              </b-card>
              <b-card title="Tuesday" style="max-width: 20rem;" class="mb-2">
                <draggable v-model="plannedRecipes" class="dragArea" :options="{group:'people'}">
                  <div v-for="element in plannedRecipes">{{element.recipe.name}}</div>
                </draggable>
              </b-card>
              <b-card title="Wednesday" style="max-width: 20rem;" class="mb-2">
                <draggable v-model="plannedRecipes" class="dragArea" :options="{group:'people'}">
                  <div v-for="element in plannedRecipes">{{element.recipe.name}}</div>
                </draggable>
              </b-card>
            </div>
          </b-card-group>
        </div>
    `,
});