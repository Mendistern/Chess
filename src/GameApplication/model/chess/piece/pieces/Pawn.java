package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

import java.util.List;

public class Pawn extends Piece {
    int numOfRowsFromOrigin;
    private Board board;
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

    //Strings of possible error messages related to the pawn
    // TODO add explanation to presentation
    static class ErrorMessages {
        private boolean printError = false;

        public void setPrintError(boolean printError) {
            this.printError = printError;
        }

        void moveInRightDirection() {
            if (!printError)return;
            System.out.println("Please move in right direction.");
        }

        void goStraight() {
            if (!printError)return;
            System.out.println("You can only go straight.");
        }

        void move2Up() {
            if (!printError)return;
            System.out.println("You can only move a max of 2 rows up.");
        }

        void move1up() {
            if (!printError)return;
            System.out.println("You can only move a max of 1 row up.");
        }

        void moveNotPossible() {
            if (!printError)return;
            System.out.println("Move not possible.");
        }

        void must1StepUp() {
            if (!printError)return;
            System.out.println("You must go 1 move forward.");
        }

        void column1Sideways() {
            if (!printError)return;
            System.out.println("You can only move 1 column sideways.");
        }

        void cannotAttackSameColor() {
            if (!printError)return;
            System.out.println("You cannot attack your own pieces.");
        }
    }

    ErrorMessages errorMsg = new ErrorMessages();


    @Override
    public boolean moveTo(Spot spot) {


        boolean returnBoolean;
        numOfRowsFromOrigin = getMoveDirectionValue() == 1 ? spot.getRow() - getRow() : getRow() - spot.getRow();



        if (!checkIfGoingInRightDirection(spot)) {

            errorMsg.moveInRightDirection();
            return false;

        }

        if (checkIfEnPassantIsAvailable(spot))return true;


        //check if in same column as in to move forward
        if (!checkIfInSameColumn(spot)) {
            if (checkIfAttacking(spot)) {


                //attack(spot);
                return true;
            } else {
                errorMsg.goStraight();
                return false;
            }
        }


        if (numOfRowsFromOrigin > 2) {
            if (!isMoved()) {
                errorMsg.move2Up();
            } else {
                errorMsg.move1up();
            }

            return false;
        }

        if (numOfRowsFromOrigin == 2 && isMoved()) {
            errorMsg.move1up();

            return false;
        }

        if (numOfRowsFromOrigin==2&&!isMoved()){
            if (board.getPieceIntern()[getColumn()][getRow()+getMoveDirectionValue()]!=null){
                return false;
            }
        }

        //after checking that dest is possible numOfRowsFromOrigin wise, check if target is empty
        if (!checkIfTargetIsEmpty(spot)) {
            errorMsg.moveNotPossible();
            return false;
        }



        returnBoolean = true;


        return returnBoolean;
    }

    private boolean checkIfEnPassantIsAvailable(Spot spot){

        int dy = getRow() < spot.getRow()?-1:+1;

        Pawn opponentsPiece;
        try{
        if (board.getPieceIntern()[spot.getColumn()][spot.getRow()+dy]!=null&&board.getPieceIntern()[spot.getColumn()][spot.getRow()+dy].getPieceType()==Piecetype.PAWN&&board.getPieceIntern()[spot.getColumn()][spot.getRow()+dy].getPieceColor()!=board.getLastTurnColor()){
            opponentsPiece=(Pawn)board.getPieceIntern()[spot.getColumn()][spot.getRow()+dy];
        }else{
            return false;
        }}catch (IndexOutOfBoundsException ioob){
            return false;
        }

        if (!opponentsPiece.isLastMove2SpotsForward())return false;

        if (!checkAttackColumnOnly1SpotAway(spot)) {
            // errorMsg.column1Sideways();
            return false;

        }

        if (numOfRowsFromOrigin>1){
            return false;
        }


        int rowValueForEnPassant = getPieceColor() ==PieceColor.WHITE ?3:4;
        //bug here
        if (Math.abs(7-getRow())!=rowValueForEnPassant){
            return false;
        }



        return true;

    }


    public boolean checkIfAttacking(Spot spot) {


        //Maak rekenkundige waarde voor Spot (wit is +1 zwart is -1)
        int moveDirectionValue = getMoveDirectionValue();

        if (numOfRowsFromOrigin != Math.abs(moveDirectionValue)) {
            //errorMsg.must1StepUp();
            return false;
        }

        if (!checkAttackColumnOnly1SpotAway(spot)) {
            // errorMsg.column1Sideways();
            return false;

        }

        if (checkIfTargetIsEmpty(spot)) {
            // errorMsg.moveNotPossible();
            return false;
        }

        //check if attack target is same color
        if (board.getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor()==getPieceColor()) {
            //errorMsg.cannotAttackSameColor();
            return false;
        }


        validAttackSpots[spot.getColumn()][spot.getRow()]=new Spot(spot.getColumn(),spot.getRow());
        return true;


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

    private boolean checkIfTargetIsEmpty(Spot spot) {
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

    public boolean checkAttackColumnOnly1SpotAway(Spot spot) {

        int columnOffset = getColumn() - spot.getColumn();

        //Bekijk als target column 1 naar rechts of 1 naar links van origineele spot is.
        return columnOffset == 1 || columnOffset == -1;
    }

    public Spot[][] validMoves(Board board) {
        errorMsg.setPrintError(false);
        this.board = board;
        System.out.println(getColumn() + " "+getRow());

        validAttackSpots = new Spot[8][8];

        Piece[][] boardPieces = board.getPieceIntern();
        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(moveTo(new Spot(i,j))){
                    validSpots[i][j] = new Spot(i,j);
                }
            }
        }


        /*
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
         */


        return validSpots;
    }


    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }

    public Spot[][] attackableSpots(Board board) {
        errorMsg.setPrintError(false);
        this.board = board;
        System.out.println(getColumn() + " "+getRow());

        Piece[][] boardPieces = board.getPieceIntern();
        Spot[][] validAttackSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0;j<8; j++) {
                if(checkIfAttacking(new Spot(j,i))){
                    validAttackSpots[i][j] = new Spot(j,i);
                }
            }
        }





        return validAttackSpots;
    }



    public int getMoveDirectionValue() {
        return this.getPieceColor() == PieceColor.WHITE ? 1 : -1;
    }


    public boolean isEnpassantAvailable() {
        return isEnpassantAvailable;
    }

    public boolean moveToSpot(Board board,Spot spot) {
        this.board = board;
        errorMsg.setPrintError(true);
        if(!moveTo(spot)) return false;


        //set value if moving 2 spots forward
        if (!isMoved()&&numOfRowsFromOrigin==2){
            isLastMove2SpotsForward=true;
        }else{
            isLastMove2SpotsForward=false;
        }

        if (checkIfEnPassantIsAvailable(spot)){
            int dy = getRow() < spot.getRow()?-1:+1;

            Piece attackedPiece = board.getPieceIntern()[spot.getColumn()][spot.getRow()+dy];
            board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()+dy] = null;

            //board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


            board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
            spot.setPiece(this);
            //System.out.println(board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())]);
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
        board.getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);


        List<Piece> piecesOfAttacker = board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].getPieces();
        for (Piece piece : piecesOfAttacker) {
            if (piece.getPieceType()==Piecetype.PAWN){
                Pawn opponentsPawn = (Pawn) piece;
                opponentsPawn.isLastMove2SpotsForward = false;
            }

        }

        errorMsg.setPrintError(false);
        return true;
    }

    public boolean isLastMove2SpotsForward() {
        return isLastMove2SpotsForward;
    }
}