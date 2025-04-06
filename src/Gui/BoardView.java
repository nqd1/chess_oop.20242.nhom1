package Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends GridPane {
    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 80;

    private Pane highlightLayer;
    private List<Circle> moveHints = new ArrayList<>();

    public BoardView() {
        Image boardImage = new Image(getClass().getResource("/media/ChessBoard.png").toExternalForm());
        ImageView boardImageView = new ImageView(boardImage);
        boardImageView.setFitWidth(BOARD_SIZE * TILE_SIZE);
        boardImageView.setFitHeight(BOARD_SIZE * TILE_SIZE);

        highlightLayer = new Pane();
        highlightLayer.setPickOnBounds(false); // allow clicks to pass through

        getChildren().addAll(boardImageView, highlightLayer);
    }

 
    public void showLegalMoves(List<int[]> legalMoves) {
        clearHighlights();

        for (int[] move : legalMoves) {
            int row = move[0];
            int col = move[1];

            Circle circle = new Circle(TILE_SIZE / 6.0);
            circle.setFill(Color.rgb(30, 144, 255, 0.5)); // semi-transparent blue
            circle.setTranslateX(col * TILE_SIZE + TILE_SIZE / 2.0);
            circle.setTranslateY(row * TILE_SIZE + TILE_SIZE / 2.0);

            moveHints.add(circle);
        }

        highlightLayer.getChildren().addAll(moveHints);
    }

    public void clearHighlights() {
        highlightLayer.getChildren().clear();
        moveHints.clear();
    }
}
