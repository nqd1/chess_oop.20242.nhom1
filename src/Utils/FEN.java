package Utils;

import Components.*;
import java.util.HashMap;
import java.util.Map;

public class FEN {
    
    public static String generateFEN(Board board, Components.Color activeColor, int moveCounter) {
        StringBuilder fen = new StringBuilder();
        
        // 1. Piece placement
        appendPiecePlacement(fen, board);
        fen.append(" ");
        
        // 2. Active color
        fen.append(activeColor == Components.Color.WHITE ? "w" : "b");
        fen.append(" ");
        
        // 3. Castling availability
        appendCastlingAvailability(fen, board);
        fen.append(" ");
        
        // 4. En passant target square
        fen.append("-"); // Simplified: no en passant tracking
        fen.append(" ");
        
        // 5. Halfmove clock
        fen.append("0"); // Simplified: not tracking halfmoves for 50-move rule
        fen.append(" ");
        
        // 6. Fullmove number
        int fullMoveNumber = (moveCounter + 1) / 2; // Increment after Black's move
        fen.append(Math.max(1, fullMoveNumber)); // Minimum is 1
        
        return fen.toString();
    }
    
    private static void appendPiecePlacement(StringBuilder fen, Board board) {
        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;
            
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                
                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    
                    char pieceChar = getPieceChar(piece);
                    fen.append(pieceChar);
                }
            }
            
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            
            if (row < 7) {
                fen.append('/');
            }
        }
    }
    
    private static void appendCastlingAvailability(StringBuilder fen, Board board) {
        boolean hasCastling = false;
        
        // Check white king castling rights
        King whiteKing = board.getKing(Components.Color.WHITE);
        if (whiteKing != null && !whiteKing.getHasMoved()) {
            Piece kingsideRook = board.getPiece(7, 7);
            if (kingsideRook instanceof Rook && !((Rook)kingsideRook).getHasMoved()) {
                fen.append("K");
                hasCastling = true;
            }
            
            Piece queensideRook = board.getPiece(7, 0);
            if (queensideRook instanceof Rook && !((Rook)queensideRook).getHasMoved()) {
                fen.append("Q");
                hasCastling = true;
            }
        }
        
        // Check black king castling rights
        King blackKing = board.getKing(Components.Color.BLACK);
        if (blackKing != null && !blackKing.getHasMoved()) {
            Piece kingsideRook = board.getPiece(0, 7);
            if (kingsideRook instanceof Rook && !((Rook)kingsideRook).getHasMoved()) {
                fen.append("k");
                hasCastling = true;
            }
            
            Piece queensideRook = board.getPiece(0, 0);
            if (queensideRook instanceof Rook && !((Rook)queensideRook).getHasMoved()) {
                fen.append("q");
                hasCastling = true;
            }
        }
        
        if (!hasCastling) {
            fen.append("-");
        }
    }
    
    private static char getPieceChar(Piece piece) {
        char pieceChar;
        
        if (piece instanceof Pawn) pieceChar = 'p';
        else if (piece instanceof Knight) pieceChar = 'n';
        else if (piece instanceof Bishop) pieceChar = 'b';
        else if (piece instanceof Rook) pieceChar = 'r';
        else if (piece instanceof Queen) pieceChar = 'q';
        else if (piece instanceof King) pieceChar = 'k';
        else return '?'; // Unknown piece
        
        return piece.getColor() == Components.Color.WHITE ? Character.toUpperCase(pieceChar) : pieceChar;
    }
    
    public static Map<String, Object> parseFEN(String fen, Board board) {
        Map<String, Object> gameState = new HashMap<>();
        
        String[] parts = fen.split(" ");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid FEN string: not enough fields");
        }
        
        // 1. Piece placement
        String placement = parts[0];
        // Logic to place pieces on board from FEN
        
        // 2. Active color
        String activeColor = parts[1];
        gameState.put("activeColor", activeColor.equals("w") ? Components.Color.WHITE : Components.Color.BLACK);
        
        // 3. Castling availability
        String castling = parts[2];
        gameState.put("whiteCastleKingside", castling.contains("K"));
        gameState.put("whiteCastleQueenside", castling.contains("Q"));
        gameState.put("blackCastleKingside", castling.contains("k"));
        gameState.put("blackCastleQueenside", castling.contains("q"));
        
        // 4. En passant target square
        String enPassant = parts[3];
        gameState.put("enPassantSquare", enPassant);
        
        // 5. Halfmove clock
        int halfmoveClock = Integer.parseInt(parts[4]);
        gameState.put("halfmoveClock", halfmoveClock);
        
        // 6. Fullmove number
        int fullmoveNumber = Integer.parseInt(parts[5]);
        gameState.put("fullmoveNumber", fullmoveNumber);
        
        return gameState;
    }
}
