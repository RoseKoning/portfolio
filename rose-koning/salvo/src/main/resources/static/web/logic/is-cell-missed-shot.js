function isCellMissedShot(cell, missedShots) {
    var keys = Object.keys(missedShots);
    if ( keys.length !=0 && missedShots.includes(cell)) {
        return true;
    }
}
