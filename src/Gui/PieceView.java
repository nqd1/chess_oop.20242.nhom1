package Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class PieceView extends GridPane {
    private ImageView imageView;
    private String pieceType;
    private String color;
    private int col;
    private int row;

    private static final int TILE_SIZE = 80;

    public PieceView(String pieceType, String color, int col, int row) {
        this.pieceType = pieceType.toLowerCase();
        this.color = color.toLowerCase();
        this.col = col;
        this.row = row;
        initializePiece();
        setPosition(col, row);
        setupClickHandler();
    }

    private void initializePiece() {
        String imagePath = String.format("/media/%s%s.png",
                color.charAt(0), capitalize(pieceType));

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView = new ImageView(image);
            imageView.setFitWidth(TILE_SIZE);
            imageView.setFitHeight(TILE_SIZE);
            getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
        }
    }

    void setPosition(int col, int row) {
        setTranslateX(col * TILE_SIZE);
        setTranslateY(row * TILE_SIZE);
    }

    private void setupClickHandler() {
        setOnMouseClicked(event -> {
            System.out.println(color + " " + pieceType + " clicked!");
            setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String getPieceType() { return pieceType; }
    public String getColor() { return color; }
    public int getCol() { return col; }
    public int getRow() { return row; }
    public void highlight(boolean enabled) {
        if(enabled) {
            setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
        } else {
            setStyle("");
        }
    }
}