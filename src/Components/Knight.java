package Components;

import Gui.BoardView;
import Gui.Square;

import java.util.*;

public class Knight extends Piece {
    private final static int value = 3;
        public Knight(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(int row, int col, Square[][] squares) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};
        for (int i = 0; i < 8; i++) {
            int nr = row + dr[i], nc = col + dc[i];
            if (isValidPosition(nr, nc) && (squares[nr][nc].getPiece() == null || squares[nr][nc].getPiece().getPiece().color != color))
                moves.add(new Move(row, col, nr, nc));
        }
        return moves;
    }
}