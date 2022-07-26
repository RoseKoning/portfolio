function getCells() {
    var rows = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    var columns = ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    var cells = [];
    for (i = 0; i < columns.length; i++) {

        var column = (columns[i])
        for (j = 0; j < rows.length; j++) {
            var cell = rows[j] + column
            cells.push(cell);
        }
    }
    return cells;
}