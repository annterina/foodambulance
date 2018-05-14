const app = new Vue({
    el: "#app",
    data: {
        editFriend: null,
        products: [],
        myProducts: [],
        customerId: -1
    },
    methods: {
        showMyProducts(id){
            fetch("http://localhost:8080/customer/" + this.customerId + "/products")
                .then(response => response.json())
                .then((data) => {
                    this.myProducts = data;
                })
        },
        showAvailableProducts(){
            fetch("http://localhost:8080/products")
                .then(response => response.json())
                .then((data) => {
                    this.products = data;
                })
        },
        addProduct(product){
            console.log(product.id);
            console.log(product.amount);
            var customerProduct = {productId : null, amount : null};
            customerProduct.productId = product.id;
            customerProduct.amount = product.amount;
            console.log(JSON.stringify(customerProduct));
            console.log("hi");
            fetch("http://localhost:8080/customer/"+this.customerId+"/products/add", {
                body: JSON.stringify(customerProduct),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(() => {
                })
        }
    },
    mounted() {
        this.customerId = Cookies.get("customerId");
        this.showAvailableProducts()
    },
    template: `
    <div>
    <br/>
        <div class="container">
            <h4>All products</h4>
            <div class="list-inline">
                <div class="list-inline-item col-md-4" v-for="(product, i) in products">
                    <h5>{{product.name}}</h5>
                Amount to add: <input v-model = "product.amount"/> {{product.baseAmount}} {{product.baseUnit}}
                <br/>
                <button v-on:click="addProduct(product)" class="btn btn-info ">Add to fridge</button>
                </div>
            </div>
        </div>
        <br>
        <div class="container">
            <h4>My products</h4>
            <div class="list-inline">
                <div class="list-inline-item col-md-4" v-for="product, i in myProducts">
                    <h5>{{product.product.name}}</h5> Amount: {{product.amount}} {{product.product.baseUnit}}
                </div>
            </div>
            <br>
            <button v-on:click="showMyProducts(1)" class="btn btn-info"> Show My Products</button>
            <br/>
        </div>
    </div>
    `,
});