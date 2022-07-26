function logoutUser(username, callback) {
    $.post("/api/logout", { username })
        .done(callback(null))
        .fail(response => callback(new Error(response.responseJSON.error)))
}