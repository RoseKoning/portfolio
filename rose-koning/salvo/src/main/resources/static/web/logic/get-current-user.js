function getCurrentUser(callback) {
    $.get("/api/username")
        .done(username => callback(null, username ? username : null))
        .fail(response => callback(new Error(response.responsJSON.error))
        )
}