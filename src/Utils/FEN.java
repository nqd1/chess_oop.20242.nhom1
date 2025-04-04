package Utils;

import Components.*;

public class FEN {
    public static String boardToFen(Board board) {
        StringBuilder fen = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int emptyCount = 0;
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPiece(i, j);
                if (p == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(p.toString());
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            if (i < 7) {
                fen.append('/');
            }
        }
        
        return fen.toString();
    }
    
    public static String gameToFen(Game game) {
        StringBuilder fen = new StringBuilder();
        Board board = game.getBoard();
        
        for (int i = 0; i < 8; i++) {
            int emptyCount = 0;
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPiece(i, j);
                if (p == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    fen.append(p.toString());
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            if (i < 7) {
                fen.append('/');
            }
        }

        fen.append(' ').append(game.getCurrentPlayer() == Color.WHITE ? 'w' : 'b');
        
        return fen.toString();
    }
}
