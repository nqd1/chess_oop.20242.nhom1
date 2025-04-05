package Gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameInfoView extends VBox {
    private TextArea moveHistoryArea;
    private Label capturedLabel;
    private HBox capturedWhite, capturedBlack;
    private Rectangle evaluationBar;
    private Label gameStatus;

    public GameInfoView() {
        setPadding(new Insets(10));
        setSpacing(15);
        setPrefWidth(200);
        setStyle("-fx-background-color: #f0f0f0;");
        moveHistoryArea = new TextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setPrefHeight(200);
        moveHistoryArea.setWrapText(true);
        moveHistoryArea.setPromptText("Move History");
        capturedLabel = new Label("Captured Pieces:");
        capturedWhite = new HBox(5);
        capturedBlack = new HBox(5);

        Label evalLabel = new Label("Evaluation:");
        evaluationBar = new Rectangle(20, 100);
        evaluationBar.setFill(Color.GRAY);


        gameStatus = new Label("White to move");

        getChildren().addAll(
                new Label("Moves:"), moveHistoryArea,
                capturedLabel, new Label("White Captures:"), capturedWhite,
                new Label("Black Captures:"), capturedBlack,
                evalLabel, evaluationBar,
                new Label("Status:"), gameStatus
        );
    }

    public void addMove(String move) {
        moveHistoryArea.appendText(move + "\n");
    }

    public void addCapturedPiece(String color, ImageView pieceIcon) {
        pieceIcon.setFitWidth(20);
        pieceIcon.setFitHeight(20);
        if (color.equalsIgnoreCase("white")) {
            capturedWhite.getChildren().add(pieceIcon);
        } else {
            capturedBlack.getChildren().add(pieceIcon);
        }
    }

    public void updateEvaluation(double score) {
        double height = 100 * (0.5 + Math.tanh(score / 3) / 2);
        evaluationBar.setHeight(height);
        evaluationBar.setFill(score > 0 ? Color.WHITE : Color.BLACK);
    }

    public void setGameStatus(String status) {
        gameStatus.setText(status);
    }
}
