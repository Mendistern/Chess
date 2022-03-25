package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;

public class Knight extends Piece {

    public Knight(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }


    //create 2 arrays: X for valid Spot distance from current spot by column and same for Y
    private static final int[] X = {2,1,-1,-2,-2,-1,1,2};
    private static final int[] Y = {1,2,2,1,-1,-2,-2,-1};

    private Spot[][] validAttackSpots ;

    @Override
    public boolean moveTo(Spot spot) {

        int targetColumn = spot.getColumn();
        int targetRow = spot.getRow();

        //loop through length of X and Y
        for (int i = 0; i <8; i++){
            //check if distance between targetSpot and currentSpot correspond with X and Y.
            if (targetColumn-getColumn()==X[i]&&targetRow-getRow()==Y[i]){
                //if yes, return true if target spot is empty or different color, false if same color
                checkIfAttacking(spot);
                return getBoard().getPieceIntern()[targetColumn][targetRow] == null || getBoard().getPieceIntern()[targetColumn][targetRow].getPieceColor() != getBoard().getLastTurnColor();
            }
        }

        return false;
    }





    public Spot[][] validMoves(Board board){

        super.setBoard(board);

        //change later to initialize the spots to the number of valid moves
        Spot[][] validSpots = new Spot[8][8];
        validAttackSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(moveTo(new Spot(i,j))){
                    validSpots[i][j] = new Spot(i,j);
                }
            }
        }
        return validSpots;

    }

    private boolean checkIfTargetIsEmpty(Spot spot) {
        return getBoard().getPieceFromSpot(spot.getColumn(), spot.getRow()) == null;
    }


    @Override
    public boolean checkIfAttacking(Spot spot) {

        if (checkIfTargetIsEmpty(spot)) return false;
        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor()==getPieceColor()) {

            return false;
        }
        validAttackSpots[spot.getColumn()][spot.getRow()]=new Spot(spot.getColumn(),spot.getRow());
        return true;
    }

    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }



    public Piecetype getPieceType(){
        return Piecetype.KNIGHT;
    }
}
