function selectCellsForSalvo(placedSalvoes, salvoSize, cell) {
    if (cell.length === 1 || cell === "10") {
        throw Error("Not a valid cell!")
    }

    placedSalvoes.forEach(salvo => {
        if (salvo.location === cell) {
            throw Error("shot already fired on this cell")
        }
    })


    if (salvoSize > 2) {
        throw Error("wait until next turn")
    }

    return cell;
}