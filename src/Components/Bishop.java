package Components;
import java.util.*;
import Gui.Square;

public class Bishop extends Piece {
    private final static int value = 3;
        public Bishop(Color color, int row, int col) {
            super(color, row, col, value);
    }
    public List<Move> getValidMoves(/*Board board*/int row, int col, Square[][] squares) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nr = row, nc = col;
            while (true) {
                nr += dr[i];
                nc += dc[i];
                if (!isValidPosition(nr, nc)) break;
//                if (board.getPiece(nr, nc) == null) {
                if(squares[nr][nc].getPiece() == null){
                    moves.add(new Move(row, col, nr, nc));
                } else {
//                    if (board.getPiece(nr, nc).getColor() != color)
                        if(squares[nr][nc].getPiece().getPiece().color != color)
                        moves.add(new Move(row, col, nr, nc));
                    break;
                }
            }
        }
        return moves;
    }
}