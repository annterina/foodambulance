const login = new Vue({
    el: "#login",
    data: {
        form: {
            mail: '',
            passwordHash: ''
        },
        show: true
    },
    methods: {
        onSubmit (evt) {
            evt.preventDefault();
            fetch("http://localhost:8080/login", {
                body: JSON.stringify(this.form),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(response => {console.log(response); return response.json()})
                .then((data) => {
                        if (data.status === "incorrectData"){
                            alert("Incorrect email and/or password!");
                        }
                        else {
                            Cookies.set("customerId", data.customerId);
                            window.location.href = "http://localhost:8080/index.jsp";
                        }
            })
        },
        onReset (evt) {
            evt.preventDefault();
            this.form.mail = '';
            this.form.passwordHash = '';
            this.show = false;
            this.$nextTick(() => { this.show = true });
        }
    },
    beforeMount(){
    },
    mounted() {
    },
    template: `
        <div class="registerArea">
            <h2>Please log in.</h2>
            <div class="col-6 offset-3">
                <b-form @submit="onSubmit" @reset="onReset" v-if="show">
                <b-form-group id="emailGroup"
                            label="Email address:"
                            label-for="email"
                    <b-form-input id="email"
                              type="email"
                              v-model="form.mail"
                              required
                              placeholder="Enter email">
                    </b-form-input>
                </b-form-group>
                <b-form-group id="passwordGroup"
                            label="Password:"
                            label-for="password">
                    <b-form-input id="password"
                              type="password"
                              v-model="form.passwordHash"
                              required
                              placeholder="Enter password">
                    </b-form-input>
                </b-form-group>
                <b-button type="submit" variant="primary">Submit</b-button>
                <b-button type="reset" variant="danger">Reset</b-button>
                </b-form>
            </div>
        </div>
    `,
});