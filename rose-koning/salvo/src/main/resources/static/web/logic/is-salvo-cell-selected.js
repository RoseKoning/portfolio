function isSalvoCellSelected(cells,location){
    var keys = Object.keys(cells);
    return keys.some(salvo=>{
        if(cells[salvo] !=null && cells[salvo]== location){
            return true;
        }
    })
}