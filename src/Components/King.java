import java.util.ArrayList;
import java.util.List;

class King extends Piece {
    public King(Color color, int row, int col) {
        super(color, row, col);
    }
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] d = {-1, 0, 1};
        for (int dr : d) {
            for (int dc : d) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = col + dc;
                if (board.isValidPosition(nr, nc) && (board.getPiece(nr, nc) == null || board.getPiece(nr, nc).getColor() != color)) {
                    moves.add(new Move(row, col, nr, nc));
                }
            }
        }
        return moves;
    }
}