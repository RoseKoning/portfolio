function joinInGame(gameId, callback) {
    return $.post("/api/game/" + gameId + "/players")
    .done(response => callback(null, response.gamePlayerId))
    .fail(response => callback(new Error(response.responseJSON.error)))
}
