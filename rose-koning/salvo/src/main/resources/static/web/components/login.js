const Login = {
    template: `<div>
  
  <h1> Welcome to Salvo</h1>
  
  <div class="main-box">
  <h2>login</h2>
  <form id="login" >
  <label>Name:<input v-model="username"></label>
  <label>Password:<input v-model="password"></label>
  <button v-on:click="authenticate(username, password)">login</button>
  </form>
  
  <h4>Don´t have an account yet? register to start playing</h4>
  
  <router-link to="/registration" class="button">Register here</router-link>
  </div>
  
  </div>`,
    data() {
      return {
        username: null,
        password: "",
        showLogin: false,
        showRegistration: false,
      }
    },
    methods: {
      authenticate: function (username, password) {
        authenticateUser(username, password, function (error) {
          if (error) return alert("wrong credentials")
  
          this.$router.push("games");
        }.bind(this))
      },
      getuser: function () {
        getCurrentUser(function (error, username) {
          if (error) return alert(error)
  
          this.username = username
        }.bind(this))
      },
    }
  }