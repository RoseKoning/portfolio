function createGame(callback) {
    $.post("/api/games")
        .done(response => callback(null, response.gamePlayerId))
        .fail(response => callback(new Error(response.responseJSON.error)))
}