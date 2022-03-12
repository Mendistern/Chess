package GameApplication.model.chess.piece.pieces;

import GameApplication.model.chess.Board;
import GameApplication.model.chess.piece.Piece;
import GameApplication.model.chess.piece.PieceColor;
import GameApplication.model.chess.spot.Spot;

public class Queen extends Piece {
    private Board board;
    private Spot[][] validAttackSpots;

    public Queen(PieceColor pieceColor, Spot pieceLocation) {
        super(pieceColor, pieceLocation);
    }

    @Override
    public boolean moveTo(Spot spot) {

        int qR, qC, oR, oC;
        qC = getColumn();
        qR = getRow();
        oC = spot.getColumn();
        oR = spot.getRow();

        //check if destination contains same piece of same color
        if (board.getPieceIntern()[oC][oR] != null && board.getPieceIntern()[oC][oR].getPieceColor() == getPieceColor()) {
            return false;
        }

        //check if valid move direction
        if (qC != oC && qR != oR) {
            return false;
        }

        //if queen and opponent same row
        if (qC == oC) {
            int dx;
            int y;
            dx = qR < oR ? 1 : -1;

            for (y = qR + dx; y != oR; y += dx) {
                if (board.getPieceIntern()[oC][y] != null) {
                    return false;
                }
            }

            return true;
        }
        //if queen and opponent same column
        if (qC == oC) {
            int qY, x;
            qY = qC < oC ? 1 : -1;
            for (x = qC + qY; x != oC; x += qY) {
                if (board.getPieceIntern()[x][oR] != null) {
                    return false;
                }
            }
            return true;
        }
        if (Math.abs(qR - oR) == Math.abs(qC - oC)) {
            int qZ, z;
            qZ = 0;

            for (z = qC + qZ; z != Math.abs(oC - qC); z += qZ) {
                if (board.getPieceIntern()[z][oC - qC] != null) {
                    return false;
                }
            }

            return true;
        }

        //if moving up down

        return false;

    }

    @Override
    public Spot[][] getValidAttackSpots() {
        return validAttackSpots;
    }

    @Override
    public void attack(Spot spot) {
        Piece attackedPiece = board.getPieceIntern()[spot.getColumn()][spot.getRow()];
        board.getPieceIntern()[attackedPiece.getColumn()][attackedPiece.getRow()] = null;

        board.getPieceSets()[board.getArrayIndexForColor(getAttackerColor())].removePiece(attackedPiece);
        spot.setPiece(this);
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        setMoved(true);

    }

    @Override
    public boolean checkIfAttacking(Spot spot) {
        if (board.getPieceIntern()[spot.getColumn()][spot.getRow()] != null) {
            validAttackSpots[spot.getColumn()][spot.getRow()] = new Spot(spot.getColumn(), spot.getRow());
            return true;
        }
        return false;
    }

    public Piecetype getPieceType() {
        return Piecetype.QUEEN;
    }

    public Spot[][] validMoves(Board board) {
        //todo
        this.board = board;
        validAttackSpots = new Spot[8][8];

        Piece[][] boardPieces = board.getPieceIntern();
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


    public boolean moveToSpot(Board board, Spot spot) {
        this.board = board;
        if (!moveTo(spot)) return false;

        if (checkIfAttacking(spot)) {
            attack(spot);
            return true;
        }
        //als alles gaat, beweeg piece
        getPieceLocation().setSpot(spot.getColumn(), spot.getRow());
        board.getPieceIntern()[spot.getColumn()][spot.getRow()] = this;

        setMoved(true);
        return true;
    }
}
