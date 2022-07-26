function isShipInLocation(ships, location) {
    if (ships.length != null) {
        var keys = Object.keys(ships);

        return keys.some(ship => {
            if (ships[ship].location.includes(location)) {
                return true;
            }
        })
    }
}