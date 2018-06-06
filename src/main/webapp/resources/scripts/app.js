const app = new Vue({
    el: "#app",
    data: {
        products: [],
        myProducts: [],
        customerId: -1
    },
    methods: {
        showMyProducts() {
            fetch("http://localhost:8080/customer/" + this.customerId + "/products")
                .then(response => response.json())
                .then((data) => {
                    this.myProducts = data;
                })
            this.myProducts.forEach(this.roundAmount);
        },
        showAvailableProducts() {
            fetch("http://localhost:8080/products")
                .then(response => response.json())
                .then((data) => {
                    this.products = data;
                })
        },
        addProduct(product) {
            console.log(product.id);
            console.log(product.amount);
            var customerProduct = {productId : null, amount : null};
            customerProduct.productId = product.id;
            customerProduct.amount = product.amount * product.baseAmount;
            fetch("http://localhost:8080/customer/"+this.customerId + "/products/add", {
                body: JSON.stringify(customerProduct),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(() => {
                });
            setTimeout(this.showMyProducts, 100);
        },
        isNumber() {
            evt = window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46 && charCode !== 45) {
                evt.preventDefault();
            } else {
                return true;
            }
        },
        roundAmount(product) {
            product.amount = Number(product.amount.toFixed(1));
        }
    },
    mounted() {
        this.customerId = Cookies.get("customerId");
        this.showAvailableProducts()
    },
    template: `
     <div>
        <div class="row">
            <b-card-group columns class="col-7">
                <b-card v-for="product in products" bg-variant="info" text-variant="white"
                        v-bind:header="product.name" class="text-center" footer-tag="footer">
                    <p class="card-text">
                        <input class="form-control input-sm" v-model="product.amount" placeholder="Enter amount"
                            v-on:keypress="isNumber()">
                        </input>
                        {{product.baseAmount}} {{product.baseUnit}}
                    </p> 
                    <em slot="footer">
                        <button id="addProductButton" v-on:click="addProduct(product)" class="btn btn-info"> 
                            Add to my fridge
                        </button>
                    </em>
                </b-card>
            </b-card-group>
            
            <div class="col-4 text-center">
                <button id="showMyProductsButton" ref="showMyProducts" v-on:click="showMyProducts()" class="btn btn-info btn-lg"> My fridge </button> 
                <br/>
                <br/>
                <ul class="list-group text-center">
                    <li class="list-group-item" v-for="product in myProducts">
                      {{product.product.name}}
                      <b-badge variant="info" pill>{{Number(product.amount.toFixed(2))}} {{product.product.baseUnit}}</b-badge>         
                    </li>
                </ul>
            </div>
        </div>
        </div>
    `,
});