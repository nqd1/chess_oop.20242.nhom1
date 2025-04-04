package Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GameView extends Application {

    private BoardView boardView;
    private GameInfoView gameInfoView;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        boardView = new BoardView();
        gameInfoView = new GameInfoView();

        root.setCenter(boardView);
        root.setRight(gameInfoView);

        HBox controlButtons = createControlButtons();
        root.setBottom(controlButtons);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createControlButtons() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));

        Button newGameButton = new Button("New Game");
        Button undoButton = new Button("Undo");
        Button switchSidesButton = new Button("Switch Sides");
        Button settingsButton = new Button("Settings");

        newGameButton.setOnAction(e -> startNewGame());
        undoButton.setOnAction(e -> undoMove());
        switchSidesButton.setOnAction(e -> switchSides());
        settingsButton.setOnAction(e -> openSettings());

        hbox.getChildren().addAll(newGameButton, undoButton, switchSidesButton, settingsButton);

        return hbox;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        MenuItem playerVsPlayer = new MenuItem("Player vs Player");
        MenuItem playerVsAI = new MenuItem("Player vs AI");

        playerVsPlayer.setOnAction(e -> setGameMode("Player vs Player"));
        playerVsAI.setOnAction(e -> setGameMode("Player vs AI"));

        gameMenu.getItems().addAll(playerVsPlayer, playerVsAI);

        Menu settingsMenu = new Menu("Settings");
        MenuItem setDifficulty = new MenuItem("Set AI Difficulty");

        setDifficulty.setOnAction(e -> setAIDifficulty());

        settingsMenu.getItems().add(setDifficulty);

        menuBar.getMenus().addAll(gameMenu, settingsMenu);

        return menuBar;
    }

    private void startNewGame() {
        System.out.println("New game started!");
    }

    private void undoMove() {
        System.out.println("Move undone!");
    }

    private void switchSides() {
        System.out.println("Sides switched!");
    }

    private void openSettings() {
        System.out.println("Settings opened!");
    }

    private void setGameMode(String mode) {
        System.out.println("Game mode set to: " + mode);
    }

    private void setAIDifficulty() {
        System.out.println("AI difficulty set!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}