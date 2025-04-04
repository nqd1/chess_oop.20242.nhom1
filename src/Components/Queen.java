package Components;
import java.util.*;

public class Queen extends Piece {
    private final static int value = 9;
    
    public Queen(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
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