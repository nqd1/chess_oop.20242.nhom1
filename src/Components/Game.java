package Components;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Color currentPlayer;
    private List<Move> moveHistory;
    private GameStatus status;
    private King whiteKing;
    private King blackKing;

    public enum GameStatus {
        ACTIVE,
        CHECK,
        CHECKMATE,
        STALEMATE,
        DRAW
    }

    public Game() {
        this.board = new Board();
        this.currentPlayer = Color.WHITE; 
        this.moveHistory = new ArrayList<>();
        this.status = GameStatus.ACTIVE;
        findKings();
    }


    private void findKings() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece instanceof King) {
                    if (piece.getColor() == Color.WHITE) {
                        whiteKing = (King) piece;
                    } else {
                        blackKing = (King) piece;
                    }
                }
            }
        }
    }

    public boolean makeMove(Move move) {
        Piece piece = board.getPiece(move.fromRow, move.fromCol);

        if (piece == null || piece.getColor() != currentPlayer) {
            return false;
        }
        
        List<Move> validMoves = piece.getValidMoves(board);
        boolean isValidMove = false;
        
        for (Move validMove : validMoves) {
            if (validMove.fromRow == move.fromRow && 
                validMove.fromCol == move.fromCol && 
                validMove.toRow == move.toRow && 
                validMove.toCol == move.toCol) {
                isValidMove = true;
                break;
            }
        }
        
        if (!isValidMove) {
            return false;
        }
        

        board.movePiece(move);
        moveHistory.add(move);

        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

        updateGameStatus();
        
        return true;
    }
    

    private boolean isInCheck(Color color) {
        King king = (color == Color.WHITE) ? whiteKing : blackKing;
        int kingRow = -1;
        int kingCol = -1;
        
        //find king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece == king) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
            if (kingRow != -1) break;
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.getColor() != color) {
                    List<Move> moves = piece.getValidMoves(board);
                    for (Move move : moves) {
                        if (move.toRow == kingRow && move.toCol == kingCol) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private void updateGameStatus() {
        boolean whiteInCheck = isInCheck(Color.WHITE);
        boolean blackInCheck = isInCheck(Color.BLACK);
        
        if (currentPlayer == Color.WHITE && whiteInCheck) {
            if (isCheckmate(Color.WHITE)) {
                status = GameStatus.CHECKMATE;
            } else {
                status = GameStatus.CHECK;
            }
        } else if (currentPlayer == Color.BLACK && blackInCheck) {
            if (isCheckmate(Color.BLACK)) {
                status = GameStatus.CHECKMATE;
            } else {
                status = GameStatus.CHECK;
            }
        } else if (isStalemate()) {
            status = GameStatus.STALEMATE;
        } else {
            status = GameStatus.ACTIVE;
        }
    }
    
    private boolean isCheckmate(Color color) {
        if (!isInCheck(color)) {
            return false;
        }
        
        return !hasLegalMoves(color);
    }
    
    private boolean isStalemate() {
        return !isInCheck(currentPlayer) && !hasLegalMoves(currentPlayer);
    }
    
    private boolean hasLegalMoves(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.getColor() == color) {
                    List<Move> moves = piece.getValidMoves(board);
                    if (!moves.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public List<Move> getValidMoves(int row, int col) {
        Piece piece = board.getPiece(row, col);
        if (piece == null || piece.getColor() != currentPlayer) {
            return new ArrayList<>();
        }
        return piece.getValidMoves(board);
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public GameStatus getStatus() {
        return status;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public List<Move> getMoveHistory() {
        return moveHistory;
    }
    
    public void resetGame() {
        board = new Board();
        currentPlayer = Color.WHITE;
        moveHistory.clear();
        status = GameStatus.ACTIVE;
        findKings();
    }
} 