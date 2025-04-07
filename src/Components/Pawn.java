package Components;
import Gui.Square;

import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
    private final static int value = 1;
        public Pawn(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(int row, int col, Square[][] squares) {
        List<Move> moves = new ArrayList<>();
        int dir = (color == Color.WHITE) ? -1 : 1;
        int nr = row + dir, nc = col;
        if (isValidPosition(nr, nc) && squares[nr][nc].getPiece() == null)
            moves.add(new Move(row, col, nr, nc));
        for (int dc = -1; dc <= 1; dc += 2) {
            int nc2 = col + dc;
            if (isValidPosition(nr, nc2) && /*board.getPiece(nr, nc2)*/squares[nr][nc2].getPiece() != null && squares[nr][nc2].getPiece().getPiece().color != color)
                moves.add(new Move(row, col, nr, nc2));
        }
        return moves;
    }
}