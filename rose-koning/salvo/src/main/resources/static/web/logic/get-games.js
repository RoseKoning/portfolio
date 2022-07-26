function getGames(callback) {
    fetch("/api/games")
    .then(function (response) {
        return response.json();
    })
    .then(function (games) {
        callback(null, games);
    })
        .catch(response => callback(new Error(response.responseJSON.error)))
}