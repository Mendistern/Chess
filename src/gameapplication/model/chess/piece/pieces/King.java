package gameapplication.model.chess.piece.pieces;

import gameapplication.model.chess.Board;
import gameapplication.model.chess.piece.Piece;
import gameapplication.model.chess.piece.PieceColor;
import gameapplication.model.chess.spot.Spot;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private Spot[][] validAttackSpots;
    private List<Spot> lastKingPosition;

    public King(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);

        lastKingPosition = new ArrayList<>();
        setLastKingPosition(pieceLocation);

    }

    @Override
    public boolean moveToSpot(Board board, Spot spot) {

        super.setBoard(board);
        if (!moveTo(spot)) return false;

        if (checkForCastling(spot)) {
            castle(spot);
            //continue moving the king
        }

        if (checkIfAttacking(spot)) {
            attack(spot);
            return true;
        }

        //Als alles ok is, beweeg de Piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);
        setLastKingPosition(spot);
        return true;
    }

    @Override
    public boolean moveTo(Spot spot) {
        int currentRow = getRow();
        int currentColumn = getColumn();

        int destRow = spot.getRow();
        int destColumn = spot.getColumn();

        //same spot
        if (currentRow == destRow && currentColumn == destColumn) {
            return false;
        }


        if (!isMoved() && checkForCastling(spot)) {
            return true;
        }

        if (getBoard().getPieceIntern()[destColumn][destRow] != null && getBoard().getPieceIntern()[destColumn][destRow].getPieceColor() == getPieceColor()) {
            return false;
        }


        int dx, dy;

        dy = Math.abs(currentRow - destRow);
        dx = Math.abs(currentColumn - destColumn);


        if (dy > 1 || dx > 1) {
            return false;
        }

        if(getBoard().getPieceIntern()[destColumn][destRow] != null && getBoard().getPieceIntern()[destColumn][destRow].getPieceType()==Piecetype.KING){
            return false;
        }

        if (checkIfMoveCausesCheck(spot)) return false;


        return true;

    }


    private boolean checkForCastling(Spot spot) {
        if (isMoved()) return false;

        //set array of possible spots
        int[] posssibleColumns = {1, 6, 1, 6};
        int[] possibleRows = {0, 0, 7, 7};

        //set step direction
        int dx = getColumn() < spot.getColumn() ? 1 : -1;


        //loop through possible spots to castle
        for (int i = 0; i < posssibleColumns.length; i++) {
            //if current iteration does not correspond with the current spot, go back to the top of the loop
            if (spot.getColumn() != posssibleColumns[i] || spot.getRow() != possibleRows[i]) continue;

            if (spot.getRow() != getRow()) return false;
            if (getBoard().getPieceIntern()[spot.getColumn() + dx][spot.getRow()] == null) return false;
            //check if the next piece is the rook
            if (getBoard().getPieceIntern()[spot.getColumn() + dx][spot.getRow()].getPieceType() != Piecetype.ROOK)
                return false;
            //check if the rook is your piece
            if (getBoard().getPieceIntern()[spot.getColumn() + dx][spot.getRow()].getPieceColor() != getPieceColor())
                return false;
            //the rook may not have moved
            if (getBoard().getPieceIntern()[spot.getColumn() + dx][spot.getRow()].isMoved()) return false;

            //Loop through spots that lead to the castling spot
            //start from the spot next to the current column ( depends on dx if it's left or right from the spot
            //after each iteration go one ste further
            for (int x = getColumn() + dx; x != spot.getColumn() + dx; x += dx) {

                if (getBoard().getPieceIntern()[x][spot.getRow()] != null) {
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
    public void attack(Spot spot) {


        //haal geattackeerde piece
        Piece attackedPiece = getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()];
        getBoard().getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;


        getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setLastKingPosition(spot);
        setMoved(true);
    }

    private void castle(Spot spot) {
        //set direction
        int dx = getColumn() < spot.getColumn() ? 1 : -1;

        //get the rook to castle
        Rook rookToCastle = (Rook) getBoard().getPieceIntern()[spot.getColumn() + dx][spot.getRow()];
        //empty the current rook spot
        getBoard().getPieceIntern()[rookToCastle.getColumn()][rookToCastle.getRow()] = null;
        //move the rook
        rookToCastle.getPieceLocation().setSpot(spot.getColumn() - dx, spot.getRow());
        rookToCastle.setMoved(true);
        //put the rook back on board
        getBoard().getPieceIntern()[spot.getColumn() - dx][spot.getRow()] = rookToCastle;


    }

    @Override
    public boolean checkIfAttacking(Spot spot) {
        if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] == null) return false;


        validAttackSpots[spot.getColumn()][spot.getRow()] = spot;
        return true;
    }

    public Piecetype getPieceType() {
        return Piecetype.KING;
    }

    public Spot[][] validMoves(Board board) {
        super.setBoard(board);


        validAttackSpots = new Spot[8][8];

        Spot[][] validSpots = new Spot[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (moveTo(new Spot(i, j))) {
                    checkIfAttacking(new Spot(i, j));
                    validSpots[i][j] = new Spot(i, j);
                }
            }

        }

        return validSpots;
    }


    public boolean isCheck(Board board) {
        super.setBoard(board);



        List<Piece> attackersPieces = getBoard().getPiecesFromInternalBoard(getBoard().getOpponentColor());

        for (Piece piece : attackersPieces) {

            piece.setBoard(board);
            //load the valid moves
             piece.validMoves(getBoard());


            switch (piece.getPieceType()) {
                case PAWN:
                    Pawn pawn = (Pawn) piece;
                    if (pawn.getValidAttackSpots()[getColumn()][getRow()] == null) continue;
                    if (pawn.getValidAttackSpots()[getColumn()][getRow()].getColumn() == getColumn() && pawn.getValidAttackSpots()[getColumn()][getRow()].getRow() == getRow()) {
                        return true;
                    }
                    break;
                case KNIGHT:
                    Knight knight = (Knight) piece;
                    if (knight.getValidAttackSpots()[getColumn()][getRow()] == null) continue;
                    if (knight.getValidAttackSpots()[getColumn()][getRow()].getColumn() == getColumn() && knight.getValidAttackSpots()[getColumn()][getRow()].getRow() == getRow()) {
                        return true;
                    }
                    break;
                case QUEEN:
                case BISHOP:
                case ROOK:
                    if (piece.moveTo(new Spot(getColumn(), getRow()))) {
                        return true;
                    }
                    break;
                default:
                    continue;


            }


        }
        return false;


    }

    //check if the move that the king is about to take will cause check.
    public boolean checkIfMoveCausesCheck(Spot spot) {
        //first get all the opponents pieces
        List<Piece> oppenentsPieces = getBoard().getPieceSets()[getBoard().getArrayIndexForColor(getBoard().getOpponentColor())].getPieces();


        for (Piece piece : oppenentsPieces) {
            //if it's the king, continue, because the king can't attack another king
            if (piece.getPieceType() == Piecetype.KING) continue;
            //set the board for the piece incase it wasn't yet set
            piece.setBoard(getBoard());
            //if the destination spot isn't empty---what if is empty
            if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()] != null) {
                //and it's an opponents piece, than you've to temporarily remove this piece to check if another piece defends this spot.
                if (getBoard().getPieceIntern()[spot.getColumn()][spot.getRow()].getPieceColor() == getBoard().getOpponentColor()) {
                    //create a temporary board. not a clone
                    Board tempBoard = getBoard();
                    //save the piece, so to speak
                    Piece tempRemovablePiece = tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()];

                    //remove it from the board
                    tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                    //if, without the piece, the move still causes check
                    if (checkIfMoveCausesCheck(spot)) {
                        //return the piece to the board
                        tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                        tempBoard.getPieceIntern()[tempRemovablePiece.getColumn()][tempRemovablePiece.getRow()] = tempRemovablePiece;
                        return true;
                    }
                    //anyways return the piece to the board
                    tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                    tempBoard.getPieceIntern()[tempRemovablePiece.getColumn()][tempRemovablePiece.getRow()] = tempRemovablePiece;
                }
            } else {
                //create a temporary board. not a clone
                Board tempBoard = getBoard();

                King tempKing = (King) tempBoard.getPieceIntern()[getColumn()][getRow()];
                tempKing.setBoard(tempBoard);

                tempBoard.getPieceIntern()[getColumn()][getRow()] = null;
                tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = tempKing;

                if (piece.moveTo(new Spot(spot.getColumn(), spot.getRow()))) {
                    tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                    tempBoard.getPieceIntern()[tempKing.getColumn()][tempKing.getRow()] = tempKing;
                    return true;
                }
                tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                tempBoard.getPieceIntern()[tempKing.getColumn()][tempKing.getRow()] = tempKing;


            }


            switch (piece.getPieceType()) {
                case PAWN:
                    Pawn pawn = (Pawn) piece;


                    Board tempBoard = getBoard();


                    tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = this;


                    pawn.setBoard(tempBoard);
                    pawn.validMoves(tempBoard);
                    if (pawn.getValidAttackSpots()[spot.getColumn()][spot.getRow()] == null) {
                        tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                        continue;
                    }

                    if (pawn.getValidAttackSpots()[spot.getColumn()][spot.getRow()].getColumn() == spot.getColumn() && pawn.getValidAttackSpots()[spot.getColumn()][spot.getRow()].getRow() == spot.getRow()) {
                        tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                        return true;
                    }
                    tempBoard.getPieceIntern()[spot.getColumn()][spot.getRow()] = null;
                    break;
                case KNIGHT:
                    Knight knight = (Knight) piece;

                    if (knight.validMoves(getBoard())[spot.getColumn()][spot.getRow()] == null) continue;
                    if (knight.validMoves(getBoard())[spot.getColumn()][spot.getRow()].getColumn() == getColumn() && knight.validMoves(getBoard())[spot.getColumn()][spot.getRow()].getRow() == getRow()) {
                        return true;
                    }
                    break;
                case QUEEN:
                case BISHOP:
                case ROOK:


                    if (piece.moveTo(new Spot(spot.getColumn(), spot.getRow()))) {
                        return true;
                    }

                    break;
                default:
                    return false;


            }


        }
        return false;

    }



    private void setLastKingPosition(Spot position) {

        if (this.lastKingPosition.size() == 2) {
            this.lastKingPosition.remove(0);
        }

        //Explenation why  this.lastKingPosition.add(position); doesn't work:
        //https://stackoverflow.com/questions/19843506/why-does-my-arraylist-contain-n-copies-of-the-last-item-added-to-the-list/19843507#19843507
        this.lastKingPosition.add(new Spot(position.getColumn(), position.getRow()));
    }

    public List<Spot> getLastKingPosition() {
        return this.lastKingPosition;
    }


    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }
}
