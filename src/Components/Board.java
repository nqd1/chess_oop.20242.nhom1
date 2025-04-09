package Components;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] board;
    private King whiteKing;
    private King blackKing;
    
    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }
    
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    public Piece getPiece(int row, int col) {
        if (!isValidPosition(row, col))
            return null;
        return board[row][col];
    }
    
    public void movePiece(Move move) {
        Piece piece = getPiece(move.fromRow, move.fromCol);
        if (piece != null) {
            if (move.isCastling) {
                board[move.toRow][move.toCol] = piece;
                board[move.fromRow][move.fromCol] = null;
                piece.setPosition(move.toRow, move.toCol);
                
                int rookFromCol, rookToCol;
                if (move.isKingSideCastling) {
                    rookFromCol = 7;
                    rookToCol = move.toCol - 1;
                } else {
                    rookFromCol = 0;
                    rookToCol = move.toCol + 1;
                }
                
                Piece rook = getPiece(move.fromRow, rookFromCol);
                if (rook != null && rook instanceof Rook) {
                    board[move.fromRow][rookToCol] = rook;
                    board[move.fromRow][rookFromCol] = null;
                    rook.setPosition(move.fromRow, rookToCol);
                }
            } else {
                board[move.toRow][move.toCol] = piece;
                board[move.fromRow][move.fromCol] = null;
                piece.setPosition(move.toRow, move.toCol);
            }
        }
    }
    
    public void setupBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Color.BLACK, 1, i);
            board[6][i] = new Pawn(Color.WHITE, 6, i);
        }
        board[0][0] = new Rook(Color.BLACK, 0, 0);
        board[0][7] = new Rook(Color.BLACK, 0, 7);
        board[7][0] = new Rook(Color.WHITE, 7, 0);
        board[7][7] = new Rook(Color.WHITE, 7, 7);
        board[0][1] = new Knight(Color.BLACK, 0, 1);
        board[0][6] = new Knight(Color.BLACK, 0, 6);
        board[7][1] = new Knight(Color.WHITE, 7, 1);
        board[7][6] = new Knight(Color.WHITE, 7, 6);
        board[0][2] = new Bishop(Color.BLACK, 0, 2);
        board[0][5] = new Bishop(Color.BLACK, 0, 5);
        board[7][2] = new Bishop(Color.WHITE, 7, 2);
        board[7][5] = new Bishop(Color.WHITE, 7, 5);
        board[0][3] = new Queen(Color.BLACK, 0, 3);
        board[7][3] = new Queen(Color.WHITE, 7, 3);
        blackKing = new King(Color.BLACK, 0, 4);
        whiteKing = new King(Color.WHITE, 7, 4);
        board[0][4] = blackKing;
        board[7][4] = whiteKing;
    }
    
    public void resetBoard() {
        board = new Piece[8][8];
        setupBoard();
    }
    
    public boolean isSquareAttacked(int row, int col, Color defendingColor) {
        Color attackingColor = (defendingColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
        
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);
                if (piece != null && piece.getColor() == attackingColor) {
                    if (piece instanceof King) {
                        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
                        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
                        for (int i = 0; i < 8; i++) {
                            int nr = r + dr[i];
                            int nc = c + dc[i];
                            if (isValidPosition(nr, nc) && nr == row && nc == col) {
                                return true;
                            }
                        }
                    } else {
                        List<Move> moves = piece.getValidMoves(this);
                        for (Move move : moves) {
                            if (move.toRow == row && move.toCol == col) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isKingInCheck(Color kingColor) {
        King king = (kingColor == Color.WHITE) ? whiteKing : blackKing;
        return isSquareAttacked(king.row, king.col, kingColor);
    }
    
    public List<Move> getLegalMoves(Color playerColor) {
        List<Move> allLegalMoves = new ArrayList<>();
        
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);
                if (piece != null && piece.getColor() == playerColor) {
                    List<Move> pieceMoves = piece.getValidMoves(this);
                    for (Move move : pieceMoves) {
                        if (!wouldBeInCheckAfterMove(move, playerColor)) {
                            allLegalMoves.add(move);
                        }
                    }
                }
            }
        }
        
        return allLegalMoves;
    }
    
    public boolean wouldBeInCheckAfterMove(Move move, Color playerColor) {
        Piece movingPiece = getPiece(move.fromRow, move.fromCol);
        Piece capturedPiece = getPiece(move.toRow, move.toCol);
        
        board[move.toRow][move.toCol] = movingPiece;
        board[move.fromRow][move.fromCol] = null;
        
        int originalRow = movingPiece.row;
        int originalCol = movingPiece.col;
        
        movingPiece.row = move.toRow;
        movingPiece.col = move.toCol;
        
        boolean inCheck;
        if (movingPiece instanceof King) {
            inCheck = isSquareAttacked(move.toRow, move.toCol, playerColor);
        } else {
            King king = (playerColor == Color.WHITE) ? whiteKing : blackKing;
            inCheck = isSquareAttacked(king.row, king.col, playerColor);
        }
        
        board[move.fromRow][move.fromCol] = movingPiece;
        board[move.toRow][move.toCol] = capturedPiece;
        
        movingPiece.row = originalRow;
        movingPiece.col = originalCol;
        
        return inCheck;
    }
    
    public King getKing(Color color) {
        return (color == Color.WHITE) ? whiteKing : blackKing;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece == null) {
                    sb.append(".\t");
                } else {
                    sb.append(piece.toString()).append("\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int evaluateBoard() {
        int whiteValue = 0;
        int blackValue = 0;
        
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);
                if (piece != null) {
                    int pieceValue = getPieceValue(piece);
                    
                    if (piece.getColor() == Color.WHITE) {
                        whiteValue += pieceValue;
                    } else {
                        blackValue += pieceValue;
                    }
                }
            }
        }
        
        return whiteValue - blackValue;
    }
    
    private int getPieceValue(Piece piece) {
        if (piece instanceof Pawn) return 100;
        if (piece instanceof Knight) return 300;
        if (piece instanceof Bishop) return 300;
        if (piece instanceof Rook) return 500;
        if (piece instanceof Queen) return 900;
        if (piece instanceof King) return 20000;
        
        return 0;
    }
}
