package Components;

//public class Board {
//    private Piece[][] board;
//    public Board() {
//        board = new Piece[8][8];
//        setupBoard();
//    }
//    // co nam trong bang hay khong
////    public boolean isValidPosition(int row, int col) {
////        return row >= 0 && row < 8 && col >= 0 && col < 8;
////    }
//    //  lay ra de kiem tra ô cờ đó có gì không
//    public Piece getPiece(int row, int col) {
//        if (!isValidPosition(row, col))
//            return null;
//        return board[row][col];
//    }
//    // di chuyen quan co
//    public void movePiece(Move move) {
//        Piece p = getPiece(move.fromRow, move.fromCol);
//        if (p != null) {
//            // cap nhat tren bang
//            board[move.toRow][move.toCol] = p;
//            board[move.fromRow][move.fromCol] = null;
//            p.setPosition(move.toRow, move.toCol); // cap nhat lai vi tri cho object
//        }
//    }
//    public void setupBoard() {
//        for (int i = 0; i < 8; i++) {
//            board[1][i] = new Pawn(Color.BLACK, 1, i);
//            board[6][i] = new Pawn(Color.WHITE, 6, i);
//        }
//        board[0][0] = new Rook(Color.BLACK, 0, 0);
//        board[0][7] = new Rook(Color.BLACK, 0, 7);
//        board[7][0] = new Rook(Color.WHITE, 7, 0);
//        board[7][7] = new Rook(Color.WHITE, 7, 7);
//        board[0][1] = new Knight(Color.BLACK, 0, 1);
//        board[0][6] = new Knight(Color.BLACK, 0, 6);
//        board[7][1] = new Knight(Color.WHITE, 7, 1);
//        board[7][6] = new Knight(Color.WHITE, 7, 6);
//        board[0][2] = new Bishop(Color.BLACK, 0, 2);
//        board[0][5] = new Bishop(Color.BLACK, 0, 5);
//        board[7][2] = new Bishop(Color.WHITE, 7, 2);
//        board[7][5] = new Bishop(Color.WHITE, 7, 5);
//        board[0][3] = new Queen(Color.BLACK, 0, 3);
//        board[7][3] = new Queen(Color.WHITE, 7, 3);
//        board[0][4] = new King(Color.BLACK, 0, 4);
//        board[7][4] = new King(Color.WHITE, 7, 4);
//    }
//}
