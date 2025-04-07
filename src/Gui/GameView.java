package Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends Application {
    private BoardView boardView;
//    private Pane pieceLayer;
    private BorderPane root;

//    dien tich cua moi o tren ban co
    private static final int TILE_SIZE = 80;
//    chieu dai, rong cua board
    private static final int BOARD_SIZE = 8;

//    chay chuong trinh
    @Override
    public void start(Stage primaryStage) {
//      Chia giao dien thành 5 vùng: Top, Bottom, Left, Right, và Center
        root = new BorderPane();
 //        khoi tao ban co moi
         boardView = new BoardView();
        initializeGame();
//         // phai set kích thước cho boardView, nếu không sẽ khong can giua duoc khi full screen
//         boardView.setMaxSize(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
//         boardView.setMinSize(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);

// //        pieceLayer = new Pane();

// //        xep chong quan co len ban co, neu dao nguoc 2 thang lai thi ban co se xep chong len quan co
// //        StackPane gameBoard = new StackPane(boardView, pieceLayer);
//         StackPane centerPane = new StackPane(boardView);
//         root.setCenter(centerPane); //

        // root.setCenter(boardView);

//        tao controlbutton o đáy cửa sổ
        HBox controlButtons = createControlButtons();
        root.setBottom(controlButtons);

//        menu o tren top
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root); // khoi tao cua so scene de hien thi ban co 
        primaryStage.setTitle("Chess Game");    // set title cho cửa sổ
        primaryStage.setScene(scene); 
        primaryStage.show();

//        setupInitialBoard();
    }

//    private void setupInitialBoard() {
//        pieceLayer.getChildren().clear(); // xoa tat ca quan co tren ban co
//        String[] backRank = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"}; // xep co tu trai sang phai
//
//        // xep quan co tren ban
//        for (int col = 0; col < BOARD_SIZE; col++) {
//            pieceLayer.getChildren().add(new PieceView(backRank[col], "black", col, 0));
//            pieceLayer.getChildren().add(new PieceView("Pawn", "black", col, 1));
//            pieceLayer.getChildren().add(new PieceView("Pawn", "white", col, 6));
//            pieceLayer.getChildren().add(new PieceView(backRank[col], "white", col, 7));
//        }
//    }

    private void initializeGame() {
        // Xóa trạng thái cũ nếu cần
        boardView.getChildren().clear();

        // Khởi tạo lại bàn cờ
        boardView = new BoardView();
        boardView.setMaxSize(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        boardView.setMinSize(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);

        // Đặt lại vào StackPane
        StackPane centerPane = new StackPane(boardView);
        root.setCenter(centerPane);
    }

    private HBox createControlButtons() {
        HBox hbox = new HBox(10); // khoang cach giua cac nut
        hbox.setPadding(new Insets(10)); // khoang cach giua nut va biên

        Button newGameButton = new Button("New Game");
        Button undoButton = new Button("Undo");
        Button switchSidesButton = new Button("Switch Sides");
        Button settingsButton = new Button("Settings");

        // Tắt focus mặc định
        newGameButton.setFocusTraversable(false);
        undoButton.setFocusTraversable(false);
        switchSidesButton.setFocusTraversable(false);
        settingsButton.setFocusTraversable(false);

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
//        setupInitialBoard();
        initializeGame();
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

//    chay function start, chay javafx
    public static void main(String[] args) {
        launch(args);
    }
}
