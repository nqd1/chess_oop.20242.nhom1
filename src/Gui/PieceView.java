package Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PieceView extends StackPane {

    private ImageView imageView;
    private String pieceType;
    private String color;

    public PieceView(String pieceType, String color) {
        this.pieceType = pieceType.toLowerCase();
        this.color = color.toLowerCase();
        initializePiece();
    }

    private void initializePiece() {
        String imagePath = String.format("/media/%s_%s.png", color, pieceType);

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            if (image.isError()) {
                throw new IllegalArgumentException("Error loading image: " + imagePath);
            }

            imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);

            getChildren().add(imageView);
            setOnMouseClicked(this::handlePieceClick);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
            e.printStackTrace();
        }
    }

    private void handlePieceClick(MouseEvent event) {
        System.out.println(pieceType + " clicked!");

    }

    public String getPieceType() {
        return pieceType;
    }

    public String getColor() {
        return color;
    }
}
