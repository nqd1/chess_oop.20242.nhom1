package Test;

import Components.*;

public class test_game {
    public static void main(String[] args) {
        Game game = new Game();
        Board board = game.getBoard();
        System.out.println(board);
        String fen = Utils.FEN.gameToFen(game);
        System.out.println(fen);
        System.out.println((game.getCurrentPlayer()==Color.WHITE)? "white" : "black");
        System.out.println(board.getPiece(0, 1));
        System.out.println(board.getPiece(2, 2));
        System.out.println(board.getPiece(7, 1));
        System.out.println(board.getPiece(5, 2));

        Move move = new Move(7, 1, 5, 2);
        boolean moveSuccess = game.makeMove(move);
        
        if (moveSuccess) {
            System.out.println("valid move");
            
            Game.GameStatus status = game.getStatus();
            System.out.println("status" + status);
            
            System.out.println("current player" + game.getCurrentPlayer());
        } else {
            System.out.println("invalid move");
        }

        System.out.println(board);
        String fen2 = Utils.FEN.gameToFen(game);
        System.out.println(fen2);
        
    }
}
