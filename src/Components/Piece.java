package Components;
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
    public abstract List<Move> getValidMoves(Board board);
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String getImage() {
        String colorPrefix = (color == Color.BLACK) ? "B" : "W";
        String pieceName = this.getClass().getSimpleName();
        return "./src/media/" + colorPrefix + pieceName + ".png";
    }
    
    @Override
    public String toString() {
        String symbol = "";
        if (this instanceof King) symbol = "K";
        else if (this instanceof Queen) symbol = "Q";
        else if (this instanceof Rook) symbol = "R";
        else if (this instanceof Bishop) symbol = "B";
        else if (this instanceof Knight) symbol = "N";
        else if (this instanceof Pawn) symbol = "P";
        
        return color == Color.WHITE ? symbol : symbol.toLowerCase();
    }
}
