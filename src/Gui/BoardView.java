package Gui;

import Components.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Components.*;
import java.util.ArrayList;
import java.util.List;

public class BoardView extends GridPane {
    private final Square[][] squares = new Square[8][8];

    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 80;
    private List<Square> highlightedSquares = new ArrayList<>(); // Lưu các ô đã được highlight
    private Square yellowhighlight = null;
//    private Pane highlightLayer;
    private List<Move> moveHints = new ArrayList<>();
    String[] backRank = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"}; // xep co tu trai sang phai

    public BoardView() {
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++) {
                Square square = new Square(row, col);
//                khoi tao cac o co quan co
                if(row == 0){
                    square.setPiece(new PieceView(backRank[col], "black"));
                }
                else if(row == 1){
                    square.setPiece(new PieceView("Pawn", "black"));
                }
                else if(row == 6){
                    square.setPiece(new PieceView("Pawn", "white"));
                }
                else if(row == 7){
                    square.setPiece(new PieceView(backRank[col], "white"));
                }
                squares[row][col] = square; // thuan tien cho viec xoa vao them vao o moi nhu ví du o duoi
//                squares[6][4].setPiece(null);        // Xóa quân ở ô cũ
//                squares[4][4].setPiece(movingPiece); // Di chuyển đến ô mới
                square.setOnMouseClicked(e -> { // khi nao click thi xu ly o day
//                    System.out.println("Thao tac voi cac o trong ban co");
                    if(square.getPiece() != null) {
                        int x = square.getRow();
                        int y = square.getCol();
//                  danh dau o nao minh dang click vao, xoa o cu da click
                        if (yellowhighlight != null) {
                            //yellowhighlight.setStyle((yellowhighlight.getRow() + yellowhighlight.getCol()) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color: gray;");
                            yellowhighlight.getPiece().setStyle("-fx-border-color: transparent; -fx-border-width: 0px");
                        }
                        squares[x][y].getPiece().setStyle("-fx-border-color: yellow; -fx-border-width: 2px; -fx-border-style: solid;");
                        yellowhighlight = squares[x][y];
                        highlightedSquares.add(squares[x][y]);
                        Hintview(x, y, square.getPiece(), squares); // lay vi tri o dang click
                    }
                    else { // bam ra o trang thi xoa het
                        if (yellowhighlight != null) yellowhighlight.getPiece().setStyle("-fx-border-color: transparent; -fx-border-width: 0px");
                        clearHighlights();
                    }
                });
                this.add(square, col, row); // them square vao trong Boardview tai vi tri row, col
            }
//        Image boardImage = new Image(getClass().getResource("/media/ChessBoard.png").toExternalForm());
//        ImageView boardImageView = new ImageView(boardImage);
//        boardImageView.setFitWidth(BOARD_SIZE * TILE_SIZE);
//        boardImageView.setFitHeight(BOARD_SIZE * TILE_SIZE);
//
//        highlightLayer = new Pane();
//        highlightLayer.setPickOnBounds(false); // allow clicks to pass through
//
//        getChildren().addAll(boardImageView, highlightLayer);
    }

    //    tao o trang den cho ban co

    public void Hintview(int row, int col, PieceView piece, Square[][] squares){
        // 2 truong hop bam vao o khong hoac co quan co
        // TH2 neu o co co chua quan co
        if(piece != null){
            moveHints = piece.getPiece().getValidMoves(row, col, squares);
            // xoa hightlight cu
            clearHighlights();
            for (Move movehint : moveHints) {
                int toRow = movehint.toRow;
                int toCol = movehint.toCol;
                squares[toRow][toCol].setStyle("-fx-background-color: green;");
                highlightedSquares.add(squares[toRow][toCol]);
            }
        }
    }

    public void clearHighlights() {
        for (Square square : highlightedSquares) {
            // Đặt lại màu ban đầu cho các ô đã được highlight
            int row = square.getRow();
            int col = square.getCol();
            square.setStyle((row + col) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color: gray;");
        }
        highlightedSquares.clear(); // Xóa danh sách các ô đã highlight
    }
//    public void showLegalMoves(List<int[]> legalMoves) {
//        clearHighlights();
//
//        for (int[] move : legalMoves) {
//            int row = move[0];
//            int col = move[1];
//
//            Circle circle = new Circle(TILE_SIZE / 6.0);
//            circle.setFill(Color.rgb(30, 144, 255, 0.5)); // semi-transparent blue
//            circle.setTranslateX(col * TILE_SIZE + TILE_SIZE / 2.0);
//            circle.setTranslateY(row * TILE_SIZE + TILE_SIZE / 2.0);
//
//            moveHints.add(circle);
//        }
//
//        highlightLayer.getChildren().addAll(moveHints);
//    }
//
//    public void clearHighlights() {
//        highlightLayer.getChildren().clear();
//        moveHints.clear();
//    }
}
