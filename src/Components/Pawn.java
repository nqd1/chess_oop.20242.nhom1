package Components;
import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
    private final static int value = 1;
    private boolean hasMoved = false;

    public Pawn(Color color, int row, int col) {
        super(color, row, col, value);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> validMoves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? -1 : 1;
        
        // Di chuyển 1 ô về phía trước
        int newRow = row + direction;
        if (board.isValidPosition(newRow, col) && board.getPiece(newRow, col) == null) {
            validMoves.add(new Move(row, col, newRow, col));
            
            // Di chuyển 2 ô trong nước đầu tiên
            if (!hasMoved) {
                int doubleMoveRow = row + 2 * direction;
                if (board.isValidPosition(doubleMoveRow, col) && 
                    board.getPiece(doubleMoveRow, col) == null) {
                    validMoves.add(new Move(row, col, doubleMoveRow, col));
                }
            }
        }
        
        // Ăn chéo
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            if (board.isValidPosition(newRow, captureCol)) {
                Piece targetPiece = board.getPiece(newRow, captureCol);
                if (targetPiece != null && targetPiece.getColor() != color) {
                    validMoves.add(new Move(row, col, newRow, captureCol));
                }
            }
        }
        
        return validMoves;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        hasMoved = true;
    }
}