import './ChessSquare.css'

function ChessSquare({row, col, piece, light, onSquareClicked}){

    function squareClicked(){
        console.log(`(${col},${row}) was clicked!`);
        onSquareClicked( row, col );
    }


    return (
        <div className={"chessSquare " + (light ? "lightSquare" : "darkSquare")} onClick={squareClicked}>
            { piece ? <img src={"./images/" + piece + ".png"}></img> : null }
        </div>
    );

}

export default ChessSquare;