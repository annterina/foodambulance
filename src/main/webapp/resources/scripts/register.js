const register = new Vue({
    el: "#register",
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
            fetch("http://localhost:8080/register", {
                body: JSON.stringify(this.form),
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(response => response.json())
                .then((data) => {
                        if (data.status === "incorrectData"){
                            alert("User mail already exists!");
                        }
                        else {
                            window.location.href = "http://localhost:8080/login";
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
        Cookies.set("customerId", -1);
    },
    mounted() {
    },
    template: `
        <div class="registerArea">
            <div class="col-6 offset-3">
                <b-form @submit="onSubmit" @reset="onReset" v-if="show">
                <b-form-group id="emailGroup"
                            label="Email address:"
                            label-for="email"
                            description="We'll never share your email with anyone else. Or we'll do. You will never know.">
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