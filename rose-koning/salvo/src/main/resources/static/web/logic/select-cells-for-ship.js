function selectCellsForShip(selectedCells, selectedShip, selectedCell) {
    if (!selectedShip) {
        throw Error("select a ship first!");
    }

    if (selectedCell.length === 1 || selectedCell === "10") {
        throw Error("Not a valid cell!")
    }

    var shipLength = selectedShip.length;
    var placedShips = Object.keys(selectedCells);

    placedShips.forEach(placedShip => {
        if (selectedCells[placedShip].includes(selectedCell)) {
            throw Error("cell is ocupied");
        }
    })

    var currentShip = selectedShip.ship;
    var currentShipPlaced = selectedCells[currentShip];
    if (currentShipPlaced.length != 0) {
        if (currentShipPlaced.length >= shipLength) {
            throw Error("ship placement is completed");
        }
    }

    var cells = selectedCells[selectedShip.ship];

    if (!cells.length) {
        cells.push(selectedCell)

        return selectedCells
    }

    var firstCell = cells[0];
    var cellLength = cells.length - 1;
    var lastCell = cells[cellLength];
    var rowToNumber = ["A","B","C","D","E","F","G","H","I","J"];

    if (selectedCell < firstCell) {
        //before

        var xSelected = selectedCell[0];//letter
        var ySelected = selectedCell[1]; //number

        var xFirst = firstCell[0];//first letter
        var yFirst = firstCell[1];//first number

        var xLast = lastCell[0];//first letter
        var yLast = lastCell[1];//first number


        if (xFirst === xLast) {
            if (xSelected === xLast) {
                if (yFirst - ySelected === 1) {
                    cells.push(selectedCell);
                    cells.sort();
                    return selectedCells;
                }
            }
        }

        if (yFirst === yLast) {
            if (ySelected === yLast) {
                var previousLetter = rowToNumber.substring(rowToNumber.indexOf(xFirst) - 1, rowToNumber.indexOf(xFirst));
                if (previousLetter === xSelected) {
                    cells.push(selectedCell);
                    cells.sort();
                    return selectedCells;
                }
            }
        }
    }
    if (selectedCell > firstCell) {
        //after

        var xFirst = firstCell[0];//letter
        var yFirst = firstCell[1];//number

        var xSelected = selectedCell[0];//letter
        var ySelected = selectedCell[1]; //number

        var xLast = lastCell[0];//first letter
        var yLast = lastCell[1];//first number


        if (xFirst === xLast) {
            if (xSelected === xLast) {
                if (ySelected - yLast === 1) {
                    cells.push(selectedCell);
                    cells.sort();
                    return selectedCells;
                }
            }
        }

        if (yFirst === yLast) {
            if (ySelected === yLast) {
                var indexLast = (rowToNumber.indexOf(xLast))+1;
                var nextLetter = rowToNumber[indexLast];
                if (nextLetter === xSelected) {
                    cells.push(selectedCell);
                    cells.sort();
                    return selectedCells;
                }
                else {
                    throw Error("not a valid input cell");
                }
            }
        }

        else {
            throw Error("not a valid input cell");
        }

    }

    return selectedCells
}