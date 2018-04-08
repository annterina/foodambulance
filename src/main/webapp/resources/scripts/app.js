const app = new Vue({
    el: "#app",
    data: {
        editFriend: null,
        products: [],
        myProducts: [],
        customerId: 1,
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
        this.showAvailableProducts()
    },
    template: `
        <div>
        <h2>All products</h2>
        <li v-for="(product, i) in products">
              Name : {{product.name}}
              Unit : {{product.baseUnit}}
              Base Amount : {{product.baseAmount}}
              Category id : {{product.categoryId}}
              <button v-on:click="addProduct(product)" class="btn btn-default"> Add this product to your fridge</button>
              Amount you want to add: <input v-model = "product.amount"/>
        </li>
        
        <button v-on:click="showMyProducts(1)" class="btn btn-default"> Show My Products</button>
          <li v-for="product, i in myProducts">
              {{product.product.name}}
          </li>
        </div>
    `,
});