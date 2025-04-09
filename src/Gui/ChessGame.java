package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            ChessBoard board = new ChessBoard(null);
            
            EvaluationBar evaluationBar = new EvaluationBar();
            
            JPanel boardContainer = new JPanel(new GridBagLayout());
            boardContainer.add(board);
            
            JPanel gamePanel = new JPanel(new BorderLayout());
            gamePanel.add(boardContainer, BorderLayout.CENTER);
            gamePanel.add(evaluationBar, BorderLayout.WEST);
            
            frame.add(gamePanel, BorderLayout.CENTER);
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int eastPanelWidth = Math.max((int)(screenSize.width * 0.4), 300);
            
            JPanel eastPanel = new JPanel(new BorderLayout());
            eastPanel.setPreferredSize(new Dimension(eastPanelWidth, 0));
            
            JTextArea moveHistory = new JTextArea();
            moveHistory.setEditable(false);
            moveHistory.setFont(new Font("Monospaced", Font.PLAIN, 16));
            JScrollPane moveScrollPane = new JScrollPane(moveHistory);
            eastPanel.add(moveScrollPane, BorderLayout.CENTER);
            
            JTextArea FENTextArea = new JTextArea();
            FENTextArea.setEditable(false);
            FENTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            FENTextArea.setLineWrap(true);
            FENTextArea.setWrapStyleWord(true);
            JScrollPane FENScrollPane = new JScrollPane(FENTextArea);
            
            int preferredHeight = (int)(screenSize.height * 0.2);
            FENScrollPane.setPreferredSize(new Dimension(eastPanelWidth, preferredHeight));
            
            eastPanel.add(FENScrollPane, BorderLayout.SOUTH);
            
            frame.add(eastPanel, BorderLayout.EAST);
            
            board.setMoveHistory(moveHistory);
            board.setFENTextArea(FENTextArea);
            
            board.setEvaluationBar(evaluationBar);

            JMenuBar menuBar = new JMenuBar();
            
            JMenu gameMenu = new JMenu("Game");
            JMenuItem newGame = new JMenuItem("New Game");
            newGame.addActionListener(e -> {
                board.resetBoard();
            });
            gameMenu.add(newGame);

            JMenuItem playAsWhite = new JMenuItem("Play as White");
            playAsWhite.addActionListener(e -> {
                board.resetBoard();
                board.setPlayerColor(Components.Color.WHITE);
            });
            gameMenu.add(playAsWhite);

            JMenuItem playAsBlack = new JMenuItem("Play as Black");
            playAsBlack.addActionListener(e -> {
                board.resetBoard();
                board.setPlayerColor(Components.Color.BLACK);
                board.flipBoard();
            });
            gameMenu.add(playAsBlack);

            gameMenu.addSeparator();
            
            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.addActionListener(e -> System.exit(0));
            gameMenu.add(exitItem);
            
            JMenu settingsMenu = new JMenu("Settings");
            settingsMenu.add(new JMenuItem("Difficulty"));
            
            menuBar.add(gameMenu);
            menuBar.add(settingsMenu);
            frame.setJMenuBar(menuBar);
            
            frame.setMinimumSize(new Dimension(800, 600));
            
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int containerWidth = boardContainer.getWidth();
                    int containerHeight = boardContainer.getHeight();
                    
                    int boardSize = Math.min(containerWidth, containerHeight);
                    
                    if (boardSize > 0) {
                        board.updateSquareSize(boardSize);
                        boardContainer.revalidate();
                    }
                    
                    int newEastPanelWidth = Math.max((int)(frame.getWidth() * 0.4), 300);
                    eastPanel.setPreferredSize(new Dimension(newEastPanelWidth, eastPanel.getHeight()));
                    frame.revalidate();
                }
            });
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
} 