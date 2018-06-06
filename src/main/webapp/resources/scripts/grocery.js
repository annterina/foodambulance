const grocery = new Vue({
    el: "#grocery",
    data: {
        customerId: -1,
        grocery : null,
        groceryList: [],
    },
    methods: {
        showGroceryList(){
            fetch("http://localhost:8080/customer/" + this.customerId + "/grocery")
                .then(response => response.json())
                .then((data) => {
                    this.groceryList = data;
                })
        },
        printPage(){
            window.print()
        }
    },
    beforeMount(){
        this.customerId = Cookies.get("customerId");
    },
    mounted() {
        this.showGroceryList();
    },
    template: `
<div>
        <div class="text-center">
            <br>
            <h4>Your groceries</h4>
            <br/>
            <ul class="list-group col-6 offset-3">
                <li class="list-group-item" v-for="grocery in groceryList">
                    {{grocery.name}} : {{grocery.amount}} {{grocery.baseUnit}}
                </li>
            </ul>
        </div>
        <br/>
        <br/>
        <div class="text-center">
            <button v-on:click="printPage()" class="btn btn-info btn-lg">Print</button>
        </div>
</div>
    `,
});