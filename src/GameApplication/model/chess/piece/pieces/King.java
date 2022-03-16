package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.piece.PieceSets;
import GameApplication.model.chess.spot.Spot;

import java.util.List;

public class King extends Piece {
    //private Board board;
    private Spot[][] validAttackSpots ;

    public King(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    @Override
    public boolean moveToSpot(Board board, Spot spot) {

        super.setBoard(board);
        if(!moveTo(spot)) return false;

        if(checkForCastling(spot)){
            castle(spot);
            //continue moving the king
        }

        if(checkIfAttacking(spot)){
            attack(spot);
            return true;
        }

        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);
        return true;
    }

    @Override
    public boolean moveTo(Spot spot) {
        int currentRow = getRow();
        int currentColumn = getColumn();

        int destRow = spot.getRow();
        int destColumn = spot.getColumn();

        //same spot
        if (currentRow==destRow&&currentColumn==destColumn){
            return false;
        }


        if (!isMoved()&&checkForCastling(spot)){
            return true;
        }

        if (getBoard().getPieceIntern()[destColumn][destRow]!=null && getBoard().getPieceIntern()[destColumn][destRow].getPieceColor()==getPieceColor()){
            return false;
        }


        int dx, dy;

        dy = Math.abs(currentRow-destRow);
        dx = Math.abs(currentColumn-destColumn);



        if(dy>1||dx>1){
            return false;
        }


        if(checkIfMoveCausesCheck(spot))return false;



        return true;

    }


    private boolean checkForCastling(Spot spot){
        if (isMoved())return false;

        //set array of possible spots
        int[] posssibleColumns = {1,6,1,6};
        int[] possibleRows = {0,0,7,7};

        //set step direction
        int dx = getColumn()<spot.getColumn()?1:-1;


        //loop through possible spots to castle
        for (int i = 0; i < posssibleColumns.length; i++) {
            //if current iteration does not correspond with the current spot, go back to the top of the loop
            if (spot.getColumn()!=posssibleColumns[i]||spot.getRow()!=possibleRows[i])continue;

                if (spot.getRow()!=getRow())return false;
                //check if the next piece is the rook
                if(getBoard().getPieceIntern()[spot.getColumn()+dx][spot.getRow()].getPieceType()!=Piecetype.ROOK)return false;
                //check if the rook is your piece
                if(getBoard().getPieceIntern()[spot.getColumn()+dx][spot.getRow()].getPieceColor()!=getPieceColor())return false;
                //the rook may not have moved
                if (getBoard().getPieceIntern()[spot.getColumn()+dx][spot.getRow()].isMoved())return false;

                //Loop through spots that lead to the castling spot
                //start from the spot next to the current column ( depends on dx if it's left or right from the spot
                //after each iteration go one ste further
                for (int x=getColumn()+dx;x!=spot.getColumn()+dx;x+=dx){

                    if (getBoard().getPieceIntern()[x][spot.getRow()]!=null){
                        return false;
                    }

                }

                //if all the spots that lead to the castle spot are empty
                return true;


        }

        //if true wasn't returned from the for loop.
        return false;
    }

    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }

    @Override
    public void attack(Spot spot) {
        //haal geattackeerde piece
        Piece attackedPiece = getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()];
        getBoard().getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


        getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);
    }

    private void castle(Spot spot){
        //set direction
        int dx = getColumn() < spot.getColumn()?1:-1;

        //get the rook to castle
        Rook rookToCastle = (Rook)getBoard().getPieceIntern()[spot.getColumn()+dx][spot.getRow()];
        //empty the current rook spot
        getBoard().getPieceIntern()[rookToCastle.getColumn()][rookToCastle.getRow()] = null;
        //move the rook
        rookToCastle.getPieceLocation().setSpot(spot.getColumn()-dx, spot.getRow());
        rookToCastle.setMoved(true);
        //put the rook back on board
        getBoard().getPieceIntern()[spot.getColumn()-dx][spot.getRow()] = rookToCastle;



    }

    @Override
    public boolean checkIfAttacking(Spot spot) {
        if(getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()]==null) return false;



        validAttackSpots[spot.getColumn()][spot.getRow()] = spot;
        return true;
    }

    public Piecetype getPieceType(){
        return Piecetype.KING;
    }

    public Spot[][] validMoves(Board board){
        super.setBoard(board);


        validAttackSpots = new Spot[8][8];

        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(moveTo(new Spot(i,j))){
                    checkIfAttacking(new Spot(i,j));
                    validSpots[i][j] = new Spot(i,j);
                }
            }

        }

        return validSpots;
    }


    public boolean isCheck(Board board){
        super.setBoard(board);
        int pieceSetIndex = getBoard().getArrayIndexForColor(getPieceColor())==0?1:0;

       List<Piece> attackersPieces =  getBoard().getPieceSets()[pieceSetIndex].getPieces();

        for (Piece piece:attackersPieces) {

            piece.setBoard(board);
            Spot[][] pieceValidMoves = piece.validMoves(getBoard());

            System.out.println(piece.getPieceType()+" "+piece.moveTo(new Spot(getColumn(),getRow())));

           /* for (int i = 0; i < pieceValidMoves.length; i++) {
                for (int j = 0; j < pieceValidMoves[i].length; j++) {
                    if (pieceValidMoves[i][j]!=null&&pieceValidMoves[i][j].getRow()==getRow()&&pieceValidMoves[i][j].getColumn()==getColumn()){
                        return true;
                    }
                }
            }*/

            if (piece.moveTo(new Spot(getColumn(),getRow()))){
                return true;
            }


        }
       return false;

       /*for (Piece piece: attackersPieces){

       }*/

    }

    public boolean checkIfMoveCausesCheck(Spot spot){
        List<Piece> oppenentsPieces = getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getBoard().getCurrentPlayer().getColor())].getPieces();

        for (Piece piece:oppenentsPieces){
            if (piece.getPieceType()==Piecetype.KING)continue;
            piece.setBoard(getBoard());
            if (piece.moveTo(spot))return true;
        }
        return false;

    }
}
