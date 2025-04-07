package Gui;

import javafx.scene.layout.StackPane;

public class Square extends StackPane {
    private final int row;
    private final int col;
    private PieceView piece;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;

        setPrefSize(80, 80);
        setStyle((row + col) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color: gray;");
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public void setPiece(PieceView piece) {
        this.piece = piece;
        getChildren().clear();
        if (piece != null) {
            getChildren().add(piece);
        }
    }

    public PieceView getPiece() {
        return piece;
    }
}
