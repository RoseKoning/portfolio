const Register = {
    template: `<div>
  
  
  
  <div v-if="!username">
  <h2>Register to start playing</h2>
  </div>
  
  <div class="main-box">
  <h3>Enter name and password</h3>
  <form id="login" >
  <label>Name:<input v-model="username"></label>
  <label>Password:<input v-model="password"></label>
  <button v-on:click="register(username, password)">Register</button>
  </form>
  </div>
  
  <div>
  <h4>Already have an account?</h4>
  <router-link to="/login" class="button">login</router-link>
  </div>
  
  </div>`,
    data() {
      return {
        username: null,
        password: null
      }
    },
    methods: {
      register: function (username, password) {
        registerUser(username, password, function (error) {
          if (error) return alert("Username already taken")
  
          this.$router.push("games");
        }.bind(this))
      },
      getuser: function () {
        getCurrentUser(function (error) {
          if (error) return alert(error)
        }.bind(this))
      },
      logout: logoutUser
    }
  }