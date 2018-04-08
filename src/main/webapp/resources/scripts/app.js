const app = new Vue({
    el: "#app",
    data: {
        editFriend: null,
        products: [],
        myProducts: [],
        customerId: 1
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
        }
        // deleteFriend(id, i) {
        //     fetch("http://rest.learncode.academy/api/vue-5/friends/" + id, {
        //         method: "DELETE"
        //     })
        //         .then(() => {
        //         this.friends.splice(i, 1);
        // })
        // },
        // updateFriend(friend) {
        //     fetch("http://rest.learncode.academy/api/vue-5/friends/" + friend.id, {
        //         body: JSON.stringify(friend),
        //         method: "PUT",
        //         headers: {
        //             "Content-Type": "application/json",
        //         },
        //     })
        //         .then(() => {
        //         this.editFriend = null;
        // })
        // }
    },
    mounted() {
        this.methods.showAvailableProducts()
    },
    template: `
        <div>
        <h2>All products</h2>
        <li v-for="product, i in products">
              Name : {{product.name}}
              Base Amount : {{product.baseAmount}}
        </li>
        
        <button v-on:click="showMyProducts(1)"> Show My Products</button>
          <li v-for="product, i in myProducts">
              {{product.product.name}}
          </li>
        </div>
    `,
});