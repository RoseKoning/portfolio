const Games = {
    template: `
  <div>
    <button v-on:click="logout()" class="button">Logout</button>
    <h1>Salvo!</h1>
    <div class="main-box">
      <ol id="games">
        <div v-for="(game, key) in games">
          <li>
            start{{game.created}}
            <template v-if="game.endDate">end{{game.endDate}}</template>
            <div v-for="(gamePlayer, key) in game.gamePlayers">
            {{gamePlayer.player.username}}
            <router-link v-bind:to="'/game/'+ gamePlayer.id" v-if="gamePlayer.isMine" class="button">return to game</router-link>
              <div v-if="game.gamePlayers.length!=2 && !gamePlayer.isMine">
              <button v-on:click="joinGame(game.id)" class="button">Join game</button>
              </div>
            </div>
         
          </li>
        </div>
      </ol>
    </div>
  
    <button v-on:click="createNewGame()">Start a new game</button>
   
  
  
  </div>`,
    data() {
      return {
        username: null,
        games: null,
        access: false,
        polling:null
      }
    },
    created() {
      getCurrentUser((error, username) => {
        if (error) return alert(error)
  
        this.username = username

        this.polling=setInterval(()=>
        getGames((error, games) => {
          if(error) return alert(error)

          games.forEach(game => {
            const { gamePlayers } = game
  
            gamePlayers.forEach(gamePlayer => {
              gamePlayer.isMine = gamePlayer.player.username === username
            })
          })
          this.games = games;
        }), 3000)
      })
    },
    methods: {
      logout() {
        logoutUser(this.username, (error) => {
          if (error) return alert(error)
  
          this.$router.push("home");
        })
      },
      createNewGame() {
        createGame((error, gamePlayerId) => {
          if (error) return alert(error)
  
          this.$router.push({ path: `/game/${gamePlayerId}` })
        })
      },
      joinGame(gameId) {
       joinInGame(gameId, (error, gamePlayerId) => {
          if (error) return alert(error)

          this.$router.push({ path: `/game/${gamePlayerId}` })
        })
      },
  
    },
    beforeDestroy(){
      this.polling=null;
    }
  };