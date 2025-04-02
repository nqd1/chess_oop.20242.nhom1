package Components;
import java.util.*;

enum Color {
    WHITE,
    BLACK
}

public abstract class Piece {
    
    protected Color color;
    protected int row, col;
    public Piece(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }
    public Color getColor() {
        return color;
    }
    public abstract List<Move> getValidMoves(Board board);
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}