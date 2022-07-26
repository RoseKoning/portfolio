function isSalvoInLocation(salvoes, location) {
    if (salvoes != null) {
        return salvoes.some(salvo => salvo.location.includes(location))
    }
}