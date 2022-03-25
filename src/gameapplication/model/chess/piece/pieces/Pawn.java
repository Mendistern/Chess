package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;

import java.util.List;

public class Pawn extends Piece {
    int numOfRowsFromOrigin;
    private boolean isEnpassantAvailable = false;


    //enpassant variable to check if opponents Pawn has advanced 2 moves.
    private boolean isLastMove2SpotsForward;

    private Spot[][] validAttackSpots ;



    public Pawn(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    public Piecetype getPieceType() {
        return Piecetype.PAWN;
    }



    @Override
    public boolean moveTo(Spot spot) {


        boolean returnBoolean;
        numOfRowsFromOrigin = getMoveDirectionValue() == 1 ? spot.getRow() - getRow() : getRow() - spot.getRow();



        if (!checkIfGoingInRightDirection(spot)) {

            return false;

        }

        if (checkIfEnPassantIsAvailable(spot))return true;


        //check if in same column as in to move forward
        if (!checkIfInSameColumn(spot)) {
            if (checkIfAttacking(spot)) {


                return true;
            } else {
                return false;
            }
        }


        if (numOfRowsFromOrigin > 2) {
         return false;
        }

        if (numOfRowsFromOrigin == 2 && isMoved()) {

            return false;
        }

        if (numOfRowsFromOrigin==2&&!isMoved()){
            if (getBoard().getPieceIntern()[getColumn()][getRow()+getMoveDirectionValue()]!=null){
                return false;
            }
        }

        //after checking that dest is possible numOfRowsFromOrigin wise, check if target is empty
        if (!checkIfTargetIsEmpty(spot)) {
            return false;
        }



        returnBoolean = true;


        return returnBoolean;
    }

    private boolean checkIfEnPassantIsAvailable(Spot spot) throws NullPointerException{

        int dy = getRow() < spot.getRow()?-1:+1;

        Pawn opponentsPiece;
        try{
        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()+dy]!=null&&getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()+dy].getPieceType()==Piecetype.PAWN&&getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()+dy].getPieceColor()!=getBoard().getLastTurnColor()){
            opponentsPiece=(Pawn)getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()+dy];
        }else{
            return false;
        }}catch (IndexOutOfBoundsException ioob){
            return false;
        }

        if (!opponentsPiece.isLastMove2SpotsForward())return false;

        if (!checkAttackColumnOnly1SpotAway(spot)) {

            return false;

        }

        if (numOfRowsFromOrigin>1){
            return false;
        }


        int rowValueForEnPassant = getPieceColor() ==PieceColor.WHITE ?3:4;

        if (Math.abs(7-getRow())!=rowValueForEnPassant){
            return false;
        }



        return true;

    }


    public boolean checkIfAttacking(Spot spot) {


        //Maak rekenkundige waarde voor Spot (wit is +1 zwart is -1)
        int moveDirectionValue = getMoveDirectionValue();

        if (numOfRowsFromOrigin != Math.abs(moveDirectionValue)) {

            return false;
        }

        if (!checkAttackColumnOnly1SpotAway(spot)) {

            return false;

        }

        if (checkIfTargetIsEmpty(spot)) {

            return false;
        }

        //check if attack target is same color
        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor()==getPieceColor()) {

            return false;
        }


        validAttackSpots[spot.getColumn()][spot.getRow()]=new Spot(spot.getColumn(),spot.getRow());
        return true;


    }



    private boolean checkIfTargetIsEmpty(Spot spot) {
        return getBoard().getPieceFromSpot(spot.getColumn(), spot.getRow()) == null;
    }

    public boolean checkIfGoingInRightDirection(Spot spot) {
        boolean isGoingInRightDirection = false;
        if (getPieceColor() == PieceColor.WHITE) {
            isGoingInRightDirection = spot.getRow() > getRow();
        }
        if (getPieceColor() == PieceColor.BLACK) {
            isGoingInRightDirection = spot.getRow() < getRow();
        }
        return isGoingInRightDirection;
    }


    public boolean checkIfInSameColumn(Spot spot) {
        return getColumn() == spot.getColumn();
    }

    public boolean checkAttackColumnOnly1SpotAway(Spot spot) {

        int columnOffset = getColumn() - spot.getColumn();

        //Bekijk als target column 1 naar rechts of 1 naar links van origineele spot is.
        return columnOffset == 1 || columnOffset == -1;
    }

    public Spot[][] validMoves(Board board) {
        super.setBoard(board);

        validAttackSpots = new Spot[8][8];

        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(moveTo(new Spot(i,j))){
                    validSpots[i][j] = new Spot(i,j);
                }
            }
        }


             return validSpots;
    }


    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }


    public int getMoveDirectionValue() {
        return this.getPieceColor() == PieceColor.WHITE ? 1 : -1;
    }



    public boolean moveToSpot(Board board,Spot spot) {
       super.setBoard(board);
        if(!moveTo(spot)) return false;


        //set value if moving 2 spots forward
        if (!isMoved()&&numOfRowsFromOrigin==2){
            isLastMove2SpotsForward=true;
        }else{
            isLastMove2SpotsForward=false;
        }

        if (checkIfEnPassantIsAvailable(spot)){
            int dy = getRow() < spot.getRow()?-1:+1;

            Piece attackedPiece = getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()+dy];
            getBoard().getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()+dy] = null;


            getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
            spot.setPiece(this);
            getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
            setMoved(true);

            return true;
        }

        if(checkIfAttacking(spot)){
            attack(spot);
            return true;
        }
        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);


        List<Piece> piecesOfAttacker = getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].getPieces();
        for (Piece piece : piecesOfAttacker) {
            if (piece.getPieceType()==Piecetype.PAWN){
                Pawn opponentsPawn = (Pawn) piece;
                opponentsPawn.isLastMove2SpotsForward = false;
            }

        }


        return true;
    }

    public boolean checkIfPromotionAvailable(){
        switch(getPieceColor()){
            case WHITE:
                if (getRow()==7){
                    return true;
                }else{
                    return false;
                }
            case BLACK:
                if (getRow()==0){
                    return true;
                }
                return false;
        }
        return false;
    }

    public boolean isLastMove2SpotsForward() {
        return isLastMove2SpotsForward;
    }
}