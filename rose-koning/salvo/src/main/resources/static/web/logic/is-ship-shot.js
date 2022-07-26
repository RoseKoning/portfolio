function isShipShot(cell, shotsPerBoat) {
    if (shotsPerBoat != null) {
        var keys = Object.keys(shotsPerBoat);
        if (keys.length != 0) {
            for (var i = 0; i < keys.length; i++) {
                    var shots = shotsPerBoat[keys[i]].hits;
                    if (shots.length != 0)
                        if (shots.includes(cell)) {
                            return true;
                        }
            }
        }
    }
}