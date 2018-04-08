const admin = new Vue({
    el: "#admin",
    data: {
        error:"",
        newProduct : {name: "", categoryId : "", baseUnit:"", baseAmount:"", baseExpirationDate:""}
    },
    methods: {
        addProduct() {
            if (this.newProduct.name!=="" && this.newProduct.categoryId!=="" && this.newProduct.baseUnit!==""
                && this.newProduct.baseAmount!=="" && this.newProduct.baseExpirationDate!=="")
            {
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
            else{
                this.error = "Fill all fields."
            }
        }
    },
    mounted() {
    },
    template: `
        <div>
        Name: <input v-model="newProduct.name" required/> <br/>
        Category Id: <input v-model="newProduct.categoryId" required/> <br/>
        Base Unit: <input v-model="newProduct.baseUnit" required/> <br/>
        Base Amount: <input v-model="newProduct.baseAmount" required/> <br/>
        Base Expiration Date (Days): <input v-model="newProduct.baseExpirationDate" required/> <br/>
        <button v-on:click="addProduct()" class="btn btn-default"> Add product</button>
        {{error}}
        </div>
    `,
});