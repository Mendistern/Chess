package GameApplication.model.Chess.piece.pieces;

import GameApplication.model.Chess.Board;
import GameApplication.model.Chess.piece.Piece;
import GameApplication.model.Chess.piece.PieceColor;
import GameApplication.model.Chess.spot.Spot;

public class Pawn extends Piece {

    private boolean isEnpassantAvailable = false;
    private Board board;

    public Pawn(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    public Piecetype getPieceType() {
        return Piecetype.PAWN;
    }

    //Strings of possible error messages related to the pawn
    // TODO add explanation to presentation
       static class ErrorMessages {
         void moveInRightDirection(){
            System.out.println("Please move in right direction.");
        }
         void goStraight(){
            System.out.println("You can only go straight.");
        }
        void move2Up(){
            System.out.println("You can only move a max of 2 rows up.");
        }
        void move1up(){
            System.out.println("You can only move a max of 1 row up.");
        }
        void moveNotPossible(){
            System.out.println("Move not possible.");
        }
        void must1StepUp(){
            System.out.println("You must go 1 move forward.");
        }
        void column1Sideways(){
            System.out.println("You can only move 1 column sideways.");
        }
    }

    ErrorMessages errorMsg = new ErrorMessages();


    @Override
    public boolean moveTo(Board board, Spot spot) {
        this.board = board;

        boolean returnBoolean = true;
        int numOfRowsFromOrigin = spot.getRow() - getRow();



        if (!checkIfGoingInRightDirection(spot)) {

           errorMsg.moveInRightDirection();
            return false;




        }


        //check if in same column as in to move forward
        //TODO
        //if attack possible to go 1 left or 1 right (mendi: if !sameColumn check if its attacking something)
        if (!checkIfInSameColumn(spot)) {
        if(checkIfAttacking(spot,  numOfRowsFromOrigin)) {
            attack(spot);
            return true;
        }else {
            errorMsg.goStraight();
            return false;
        }
        }



        if (numOfRowsFromOrigin > 2) {
            if (!isMoved()){
               errorMsg.move2Up();
            }else{
                errorMsg.move1up();
            }

            return false;
        }

        if (numOfRowsFromOrigin==2&& isMoved()){
            errorMsg.move1up();

            return false;
        }

        //after checking that dest is possible numOfRowsFromOrigin wise, check if target is empty
        if(!checkIfTargetIsEmpty(  spot)){
            errorMsg.moveNotPossible();
            return false;
        }


        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(),spot.getRow());
        getPieceLocation().getPiece().getPieceLocation().setPiece(spot.getPiece());
        setMoved(true);
        returnBoolean = true;




        return returnBoolean;
}





    private boolean checkIfAttacking(Spot spot,int  numOfRowsFromOrigin) {

        //Maak rekenkundige waarde voor Spot (wit is +1 zwart is -1)
        int moveDirectionValue = this.getPieceColor()==PieceColor.WHITE ? 1 : -1;

        if (numOfRowsFromOrigin != moveDirectionValue){
            errorMsg.must1StepUp();
            return false;
        }

        if (!checkAttackColumnOnly1SpotAway(spot)){
            errorMsg.column1Sideways();
            return false;

        }

        if(checkIfTargetIsEmpty(spot)){
            errorMsg.moveNotPossible();
            return false;
        }

        System.out.println("attacking");

        return true;


    }

    private void attack(Spot spot) {
        //haal geatackeerde piece
        Piece attackedPiece = spot.getPiece();
        Piece a = board.getPieceIntern()[spot.getColumn()][spot.getRow()];
        board.getPieceIntern()[a.getColumn()][a.getRow()] = null;

        //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        System.out.println("attacking spot: " + spot.getColumn() + " " + spot.getRow());

        System.out.println(a.getPieceLocation().getPiece());
        board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].removePiece(a);
        spot.setPiece(this);
        //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
    }

    private boolean checkIfTargetIsEmpty( Spot spot) {
        return board.getPieceFromSpot(spot.getColumn(), spot.getRow()) == null;
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
    public boolean checkAttackColumnOnly1SpotAway(Spot spot){

        int columnOffset = getColumn() - spot.getColumn();

        //Bekijk als target column 1 naar rechts of 1 naar links van origineele spot is.
        return columnOffset == 1 || columnOffset == -1;
    }

    public Spot validMoves(Board board) {
        //todo
        boolean isFirstMove = isMoved();
        PieceColor color = getPieceColor();


        Piece[][] piecesOnBoard = board.getPieceIntern();

        for (Piece[] piece :
                piecesOnBoard) {
            System.out.println(piece);
        }


        //change later to initialize the spots to the number of valid moves
        Spot[][] spot = new Spot[1][1];
        return spot[3][4];
    }


    public boolean isEnpassantAvailable() {
        return isEnpassantAvailable;
    }

    private void moveToSpot(Spot spot){
        getPieceLocation().setSpot(spot.getColumn(),spot.getRow());
    }
}
