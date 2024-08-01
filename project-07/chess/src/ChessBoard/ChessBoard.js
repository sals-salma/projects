import ChessSquare from "../ChessSquare/ChessSquare";
import {useState} from 'react';
import './ChessBoard.css';

function ChessBoard(){

    let oldTurn = true;


    //this will run on the first re-draw
    //we need to be careful about state in React
    //let wTurn = true;

    //instead we'll call useState
    //the first time this runs, React creates a little storage spot under the hood
    //                          and assigns whatever value we've passed in
    //                          then returns that value as the 0th element of the array
    //                          and a setter function as the 1th element of the array
    //
    //ON FUTURE CALLS (when the control rerenders)
    //React will see that there is already a space in memory for this data, and will
    //simply return whatever was last saved there (either with the initial useState() call OR
    //the most recent call to the setter).
    const [wTurn, setWTurn] = useState(true);

    const [clickCount, setClickCount] = useState( 0 );

    function onSquareClicked( row, col ){
        console.log( "the board detected a click at " + col + ", " + row);
        console.log( oldTurn );
        oldTurn = false;
        console.log( wTurn );
        console.log( clickCount );

        //now this is wrong
        //wTurn = false;

        //instead we want to call the setter so that the underlying memory that React
        //tracks gets changed.  this will also invalidate the component (meaning it needs to redraw)
        //IF the value is actually different
        setWTurn( !wTurn );
        setClickCount( Math.random() );
    }


    const squares = [];



    for( let rowIndex = 0; rowIndex < 8; rowIndex++){
        for( let colIndex = 0; colIndex < 8; colIndex++){

            let startingPiece = "";
            if( rowIndex === 0 ) startingPiece = "b";
            else if (rowIndex === 7 ) startingPiece = "w";

            if( rowIndex === 0 || rowIndex == 7){
                //black's back row
                //brook, bknight, bbishop, bqueen, bking, bbishop, bknight, brook
                switch( colIndex ){
                    case 0: startingPiece += "rook"; break;
                    case 1: startingPiece += "knight"; break;
                    case 2: startingPiece += "bishop"; break;
                    case 3: startingPiece += "queen"; break;
                    case 4: startingPiece += "king"; break;
                    case 5: startingPiece += "bishop"; break;
                    case 6: startingPiece += "knight"; break;
                    case 7: startingPiece += "rook"; break;
                }
            }
            if( rowIndex === 1 ) startingPiece = "bpawn";

            if(rowIndex == 6) startingPiece = "wpawn";

            squares.push( <ChessSquare 
                light={(rowIndex + colIndex) % 2 === 0} 
                piece={startingPiece} 
                row={rowIndex} 
                col={colIndex} 
                onSquareClicked={onSquareClicked}
                /> )
        }
    }


    //get row[0] to display...

    return (
        <div className="chessBoard">
            {squares}
        </div>
    );


}

export default ChessBoard;