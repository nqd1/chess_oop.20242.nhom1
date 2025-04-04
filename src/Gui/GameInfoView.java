package Gui;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;
public class GameInfoView extends VBox {
    private Label currentPlayerLabel;
    private ListView<String> moveHistoryListView;
    private Label capturedPiecesLabel;
    private Label gameStatusLabel;
    private Rectangle evaluationBar;
    public GameInfoView() {
        setPadding(new Insets(10));
        setSpacing(10);
        currentPlayerLabel = new Label("Current Player: White");
        moveHistoryListView = new ListView<>();
        capturedPiecesLabel = new Label("Captured Pieces: None");
        gameStatusLabel = new Label("Game Status: In Progress");
        evaluationBar = new Rectangle(20, 200, Color.GRAY);
        getChildren().addAll(currentPlayerLabel, moveHistoryListView, capturedPiecesLabel, evaluationBar, gameStatusLabel);
    }
    public void updateCurrentPlayer(String player) {
        currentPlayerLabel.setText("Current Player: " + player);
    }
    public void updateMoveHistory(List<String> moves) {
        moveHistoryListView.getItems().setAll(moves);
    }
    public void updateCapturedPieces(String pieces) {
        capturedPiecesLabel.setText("Captured Pieces: " + pieces);
    }

    public void updateGameStatus(String status) {
        gameStatusLabel.setText("Game Status: " + status);
    }

    public void updateEvaluationBar(double evaluation) {
        double height = (evaluation + 1) * 100; // normalize to 0-200 range
        evaluationBar.setHeight(height);
        evaluationBar.setFill(evaluation > 0 ? Color.WHITE : Color.BLACK);
    }
}