function registerUser(username, password, callback) {
    $.post("/api/players", {
        username,
        password
    })
        .done(callback(null))
        .fail(response => callback(new Error(response.responseJSON.error)))
}