function getGameView(gamePlayerId, callback) {
    fetch("/api/game_view/" + gamePlayerId)
        .then(function (response) {
            if (response.ok)
                return response.json();

            if (response.status === 401)
                throw new Error('session expired')

            throw new Error('unknown error')
        })
        .then(game=> callback(null, game))
        .catch(error => callback(error))
}