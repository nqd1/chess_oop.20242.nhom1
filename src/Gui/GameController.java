package Gui;

import Components.Move;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private PieceView selectedPiece;
    private final List<Move> legalMoves = new ArrayList<>();
    private final BoardView boardView;
    public GameController(BoardView boardView) {
        this.boardView = boardView;
    }

    public void handleBoardClick(int row, int col) {
        if (selectedPiece == null) {
            // Select piece logic
        } else {
            Move attemptedMove = new Move(
                    selectedPiece.getRow(),
                    selectedPiece.getCol(),
                    row,
                    col
            );

            if (isValidMove(attemptedMove)) {
                executeMove(attemptedMove);
            }
            clearSelection();
        }
    }

    public void handlePieceSelection(PieceView piece) {
        if (selectedPiece == null) {
            selectedPiece = piece;
            piece.highlight(true);
        } else if (selectedPiece == piece) {
            clearSelection();
        }
    }

    private boolean isValidMove(Move move) {
        return legalMoves.contains(move);
    }

    private void executeMove(Move move) {
        selectedPiece.setPosition(move.toCol, move.toRow);
    }

    private void clearSelection() {
        if (selectedPiece != null) {
            selectedPiece.highlight(false);
            selectedPiece = null;
        }
        legalMoves.clear();
    }
}