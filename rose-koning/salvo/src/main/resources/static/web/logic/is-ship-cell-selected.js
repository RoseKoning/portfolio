function isShipCellSelected(cells, location) {
    var keys = Object.keys(cells);

    return keys.some(ship => {
        if (cells[ship] != null && cells[ship].includes(location)) {
            return true;
        }
    }
    )
}