package Components;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private final static int value = 100000;
    private boolean hasMoved = false;
    
    public King(Color color, int row, int col) {
        super(color, row, col, value);
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
        
        // Add castling moves if conditions are met
        if (!hasMoved && !board.isKingInCheck(color)) {
            // King-side castling
            if (canCastleKingSide(board)) {
                moves.add(new Move(row, col, row, col + 2, true, true, false));
            }
            
            // Queen-side castling
            if (canCastleQueenSide(board)) {
                moves.add(new Move(row, col, row, col - 2, true, false, true));
            }
        }
        
        return moves;
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
    
    // Check if king-side castling is possible
    private boolean canCastleKingSide(Board board) {
        if (hasMoved) return false;
        
        // Check if king-side rook exists and hasn't moved
        Piece rook = board.getPiece(row, 7);
        if (rook == null || !(rook instanceof Rook) || rook.getColor() != color || ((Rook)rook).getHasMoved()) {
            return false;
        }
        
        // Check if squares between king and rook are empty
        for (int c = col + 1; c < 7; c++) {
            if (board.getPiece(row, c) != null) {
                return false;
            }
        }
        
        // Check if king passes through check
        for (int c = col; c <= col + 2; c++) {
            if (board.isSquareAttacked(row, c, color)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Check if queen-side castling is possible
    private boolean canCastleQueenSide(Board board) {
        if (hasMoved) return false;
        
        // Check if queen-side rook exists and hasn't moved
        Piece rook = board.getPiece(row, 0);
        if (rook == null || !(rook instanceof Rook) || rook.getColor() != color || ((Rook)rook).getHasMoved()) {
            return false;
        }
        
        // Check if squares between king and rook are empty
        for (int c = col - 1; c > 0; c--) {
            if (board.getPiece(row, c) != null) {
                return false;
            }
        }
        
        // Check if king passes through check
        for (int c = col; c >= col - 2; c--) {
            if (board.isSquareAttacked(row, c, color)) {
                return false;
            }
        }
        
        return true;
    }
}