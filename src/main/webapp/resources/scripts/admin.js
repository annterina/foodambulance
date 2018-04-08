const admin = new Vue({
    el: "#admin",
    data: {
        error:"",
        newProduct : null
    },
    methods: {
        addProduct() {
            if (this.newProduct.name!==null && this.newProduct.categoryId!==null && this.newProduct.baseUnit!==null
                && this.newProduct.baseAmount!==null && this.newProduct.baseExpirationDate!==null)
            {
                fetch("http://localhost:8080/products/newProduct", {
                    body: JSON.stringify(product),
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
        <input v-model="newProduct.name" required/>
        <input v-model="newProduct.categoryId" required/>
        <input v-model="newProduct.baseUnity" required/>
        <input v-model="newProduct.baseAmount" required/>
        <input v-model="newProduct.baseExpirationDate" required/>
        <button v-on:click="addProduct()" class="btn btn-default"> Add product</button>
        {{error}}
        </div>
    `,
});