function authenticateUser(username, password, callback) {
    $.post("/api/login", {
        username,
        password
    })
        .done(callback(null))
        .fail( response => callback(new Error(response.responseJSON.error)))
}