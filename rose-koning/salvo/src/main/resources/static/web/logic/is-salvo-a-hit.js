function isSalvoAHit(cell, opponentShips){
   var shots = []
   if(opponentShips != null)
   opponentShips.forEach(ship => {
       if(ship.hits != null){
           ship.hits.forEach(hit=> shots.push(hit));
       }
       
   });
   if(shots.includes(cell)){
       return true;
   }
}