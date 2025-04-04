package Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BoardView extends StackPane {
    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 80;

    public BoardView() {
        Image boardImage = new Image(getClass().getResource("/media/ChessBoard.png").toExternalForm());
        ImageView boardImageView = new ImageView(boardImage);
        boardImageView.setFitWidth(BOARD_SIZE * TILE_SIZE);
        boardImageView.setFitHeight(BOARD_SIZE * TILE_SIZE);
        getChildren().add(boardImageView);
    }
}
