package Components;
import Gui.*;

import java.util.*;



public abstract class Piece {
    protected Color color;
    protected int row, col;
    public Piece(Color color, int row, int col, int value) {
        this.color = color;
        this.row = row;
        this.col = col;
    }
    public Color getColor() {
        return color;
    }
    public abstract List<Move> getValidMoves(/*Board board*/ int row, int col, Square[][] squares);
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}