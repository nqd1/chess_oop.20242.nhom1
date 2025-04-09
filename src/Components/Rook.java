package Components;
import java.util.*;


public class Rook extends Piece {
    private final static int value = 5;
    private boolean hasMoved = false;

    public Rook(Color color, int row, int col) {
        super(color, row, col, value);
    }
    
    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        hasMoved = true;
    }
    
    public boolean getHasMoved() {
        return hasMoved;
    }
    
    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }
    
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, 0, 0, 1};
        int[] dc = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            int nr = row, nc = col;
            while (true) {
                nr += dr[i];
                nc += dc[i];
                if (!board.isValidPosition(nr, nc)) break;
                if (board.getPiece(nr, nc) == null) {
                    moves.add(new Move(row, col, nr, nc));
                } else {
                    if (board.getPiece(nr, nc).getColor() != color)
                        moves.add(new Move(row, col, nr, nc));
                    break;
                }
            }
        }
        return moves;
    }
}