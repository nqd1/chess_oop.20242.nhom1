package Components;
import Gui.Square;

import java.util.*;


public class Rook extends Piece {
    private final static int value = 5;

    public Rook(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(int row, int col, Square[][] squares) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, 0, 0, 1};
        int[] dc = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            int nr = row, nc = col;
            while (true) {
                nr += dr[i];
                nc += dc[i];
                if (!isValidPosition(nr, nc)) break;
                if (squares[nr][nc].getPiece() == null) {
                    moves.add(new Move(row, col, nr, nc));
                } else {
                    if (squares[nr][nc].getPiece().getPiece().color != color)
                        moves.add(new Move(row, col, nr, nc));
                    break;
                }
            }
        }
        return moves;
    }
}