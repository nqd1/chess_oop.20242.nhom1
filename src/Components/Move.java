package Components;

import java.util.Objects;

public class Move {
    public int fromRow, fromCol, toRow, toCol;
    public boolean isCastling;
    public boolean isKingSideCastling;
    public boolean isQueenSideCastling;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isCastling = false;
        this.isKingSideCastling = false;
        this.isQueenSideCastling = false;
    }
    
    public Move(int fromRow, int fromCol, int toRow, int toCol, boolean isCastling, boolean isKingSideCastling, boolean isQueenSideCastling) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isCastling = isCastling;
        this.isKingSideCastling = isKingSideCastling;
        this.isQueenSideCastling = isQueenSideCastling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return fromRow == move.fromRow &&
               fromCol == move.fromCol &&
               toRow == move.toRow &&
               toCol == move.toCol &&
               isCastling == move.isCastling &&
               isKingSideCastling == move.isKingSideCastling &&
               isQueenSideCastling == move.isQueenSideCastling;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromRow, fromCol, toRow, toCol, isCastling, isKingSideCastling, isQueenSideCastling);
    }
} 