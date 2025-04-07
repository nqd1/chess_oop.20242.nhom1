package Utils;

//import Components.Board;
//import Components.Piece;
//import Components.Color;

//public class Board2Fen {
//    public static String boardToFen(Board board){
//        StringBuilder fen = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                Piece p = board.getPiece(i, j);
//                if (p != null) {
//                    fen.append(p.getColor() == Color.WHITE
//                    ?                       p.getClass().getSimpleName().charAt(0)
//                    :Character.toLowerCase( p.getClass().getSimpleName().charAt(0))
//                    );
//                }
//            }
//        }
//        return fen.toString();
//    }
//}
