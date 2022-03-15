package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class King extends Piece {
    private Board board;
    private Spot[][] validAttackSpots ;

    public King(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    @Override
    public boolean moveToSpot(Board board, Spot spot) {

        this.board = board;
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
        board.getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

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

        if (board.getPieceIntern()[destColumn][destRow]!=null && board.getPieceIntern()[destColumn][destRow].getPieceColor()==getPieceColor()){
            return false;
        }


        int dx, dy;

        dy = Math.abs(currentRow-destRow);
        dx = Math.abs(currentColumn-destColumn);



        if(dy>1||dx>1){
            return false;
        }




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
                if(board.getPieceIntern()[spot.getColumn()+dx][spot.getRow()].getPieceType()!=Piecetype.ROOK)return false;
                //check if the rook is your piece
                if(board.getPieceIntern()[spot.getColumn()+dx][spot.getRow()].getPieceColor()!=getPieceColor())return false;
                //the rook may not have moved
                if (board.getPieceIntern()[spot.getColumn()+dx][spot.getRow()].isMoved())return false;

                //Loop through spots that lead to the castling spot
                //start from the spot next to the current column ( depends on dx if it's left or right from the spot
                //after each iteration go one ste further
                for (int x=getColumn()+dx;x!=spot.getColumn()+dx;x+=dx){

                    if (board.getPieceIntern()[x][spot.getRow()]!=null){
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
        Piece attackedPiece = board.getPieceIntern()[spot.getColumn()][spot.getRow()];
        board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


        board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);
    }

    private void castle(Spot spot){
        //set direction
        int dx = getColumn() < spot.getColumn()?1:-1;

        //get the rook to castle
        Rook rookToCastle = (Rook)board.getPieceIntern()[spot.getColumn()+dx][spot.getRow()];
        //empty the current rook spot
        board.getPieceIntern()[rookToCastle.getColumn()][rookToCastle.getRow()] = null;
        //move the rook
        rookToCastle.getPieceLocation().setSpot(spot.getColumn()-dx, spot.getRow());
        rookToCastle.setMoved(true);
        //put the rook back on board
        board.getPieceIntern()[spot.getColumn()-dx][spot.getRow()] = rookToCastle;



    }

    @Override
    public boolean checkIfAttacking(Spot spot) {
        if(board.getPieceIntern()[spot.getColumn()][spot.getRow()]==null) return false;



        validAttackSpots[spot.getColumn()][spot.getRow()] = spot;
        return true;
    }

    public Piecetype getPieceType(){
        return Piecetype.KING;
    }

    public Spot[][] validMoves(Board board){
        this.board = board;


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
}
