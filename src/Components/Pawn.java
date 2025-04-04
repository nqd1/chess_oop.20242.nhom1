package Components;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
    private final static int value = 1;
        public Pawn(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int dir = (color == Color.WHITE) ? -1 : 1;
        int nr = row + dir, nc = col;
        if (board.isValidPosition(nr, nc) && board.getPiece(nr, nc) == null)
            moves.add(new Move(row, col, nr, nc));
        for (int dc = -1; dc <= 1; dc += 2) {
            int nc2 = col + dc;
            if (board.isValidPosition(nr, nc2) && board.getPiece(nr, nc2) != null && board.getPiece(nr, nc2).getColor() != color)
                moves.add(new Move(row, col, nr, nc2));
        }
        return moves;
    }
}