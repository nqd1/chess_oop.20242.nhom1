package Gui;

import Components.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PieceView extends StackPane {
    private ImageView imageView;
    private String pieceType;
    private String color;
    private Piece piece;

    private static final int TILE_SIZE = 80;

    public PieceView(String pieceType, String color/*, int col, int row*/) {
        this.pieceType = pieceType.toLowerCase();
        this.color = color.toLowerCase();
        Color color1;

        if(color.equals("white")) color1 = Color.WHITE;
        else color1 = Color.BLACK;
//        them thuoc tinh Piece
//        nho xoa het cai col voi row truyen vao Rook nhe
        switch (pieceType.toLowerCase()) {
            case "rook":
                this.piece = new Rook(color1, 0, 0);
                break;
            case "queen":
                this.piece = new Queen(color1, 0, 0);
                break;
            case "pawn":
                this.piece = new Pawn(color1, 0, 0);
                break;
            case "knight":
                this.piece = new Knight(color1, 0, 0);
                break;
            case "king":
                this.piece = new King(color1, 0, 0);
                break;
            case "bishop":
                this.piece = new Bishop(color1, 0, 0);
                break;
        }
        initializePiece(); // load image
//        setPosition(col, row); // set position on board
//        setupClickHandler();
    }

    private void initializePiece() {
        String imagePath = String.format("/media/%s%s.png", color.charAt(0), capitalize(pieceType));

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView = new ImageView(image);
            imageView.setFitWidth(TILE_SIZE-50);
            imageView.setFitHeight(TILE_SIZE-50);
            getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
        }
    }

//    private void setPosition(int col, int row) {
//        setTranslateX(col * TILE_SIZE);
//        setTranslateY(row * TILE_SIZE);
//    }

//    private void setupClickHandler() {
//        setOnMouseClicked(event -> {
//            System.out.println(color + " " + pieceType + " clicked!");
//            // Optionally: notify controller, highlight, or toggle selection
//            setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
//        });
//    }
//
   private String capitalize(String str) {
       if (str == null || str.isEmpty()) return str;
       return str.substring(0, 1).toUpperCase() + str.substring(1);
   }
   public Piece getPiece(){
        return piece;
   }
//
//    public String getPieceType() {
//        return pieceType;
//    }
//
//    public String getColor() {
//        return color;
//    }
}
