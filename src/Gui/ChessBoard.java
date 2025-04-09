package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import Components.Board;
import Components.Piece;
import Components.Move;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import Utils.FEN;

public class ChessBoard extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static int SQUARE_SIZE = 60;
    private static final int PIECE_PADDING = 10;
    private static final int OUTLINE_THICKNESS = 1;
    private static final Color OUTLINE_COLOR = Color.BLACK;

    private JButton[][] squares;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Board gameBoard;
    private JTextArea moveHistory;
    private Map<String, ImageIcon> pieceImages;
    private ImageIcon dotIcon = createDotIcon();
    private Components.Color currentPlayerTurn = Components.Color.WHITE;
    private int moveCounter = 1;
    private EvaluationBar evaluationBar;
    private JTextArea FENTextArea;
    
    private boolean isDragging = false;
    private int dragSourceRow = -1;
    private int dragSourceCol = -1;
    private int dragX, dragY;
    private ImageIcon draggedPieceIcon = null;

    private boolean boardFlipped = false;

    public ChessBoard(JTextArea moveHistory) {
        this.moveHistory = moveHistory;
        gameBoard = new Board();
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        squares = new JButton[BOARD_SIZE][BOARD_SIZE];
        pieceImages = new HashMap<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setContentAreaFilled(true);

                final int finalRow = row;
                final int finalCol = col;

                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!isDragging) {
                            handleSquareClick(finalRow, finalCol);
                        }
                    }
                    
                    @Override
                    public void mousePressed(MouseEvent e) {
                        final int boardRow = boardFlipped ? (BOARD_SIZE - 1 - finalRow) : finalRow;
                        final int boardCol = boardFlipped ? (BOARD_SIZE - 1 - finalCol) : finalCol;
                        
                        Piece piece = gameBoard.getPiece(boardRow, boardCol);
                        if (piece != null && piece.getColor() == currentPlayerTurn) {
                            isDragging = true;
                            dragSourceRow = boardRow;
                            dragSourceCol = boardCol;
                            dragX = e.getX();
                            dragY = e.getY();
                            
                            String imagePath = piece.getImage();
                            draggedPieceIcon = loadPieceImage(imagePath);
                            
                            square.setIcon(null);
                            repaint();
                        }
                    }
                    
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (isDragging) {
                            isDragging = false;
                            
                            Point mousePosition = getMousePosition();
                            if (mousePosition != null) {
                                int visualRow = mousePosition.y / SQUARE_SIZE;
                                int visualCol = mousePosition.x / SQUARE_SIZE;
                                
                                int targetRow = boardFlipped ? (BOARD_SIZE - 1 - visualRow) : visualRow;
                                int targetCol = boardFlipped ? (BOARD_SIZE - 1 - visualCol) : visualCol;
                                
                                if (isValidPosition(targetRow, targetCol)) {
                                    Move move = new Move(dragSourceRow, dragSourceCol, targetRow, targetCol);
                                    Piece pieceToMove = gameBoard.getPiece(dragSourceRow, dragSourceCol);
                                    
                                    if (pieceToMove != null) {
                                        List<Move> validMoves = pieceToMove.getValidMoves(gameBoard);
                                        Move legalMove = null;
                                        
                                        for (Move m : validMoves) {
                                            if (m.toRow == targetRow && m.toCol == targetCol && 
                                                !gameBoard.wouldBeInCheckAfterMove(m, currentPlayerTurn)) {
                                                legalMove = m;
                                                break;
                                            }
                                        }
                                        
                                        if (legalMove != null) {
                                            executeMove(legalMove);
                                        }
                                    }
                                }
                                
                                resetBoardVisuals();
                            }
                        }
                    }
                });
                
                square.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (isDragging) {
                            dragX = e.getX();
                            dragY = e.getY();
                            repaint();
                        }
                    }
                });

                squares[row][col] = square;
                add(square);
            }
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                updateSquare(row, col);
            }
        }
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    dragX = e.getX();
                    dragY = e.getY();
                    repaint();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging) {
                    isDragging = false;
                    
                    int visualRow = e.getY() / SQUARE_SIZE;
                    int visualCol = e.getX() / SQUARE_SIZE;
                    
                    int targetRow = boardFlipped ? (BOARD_SIZE - 1 - visualRow) : visualRow;
                    int targetCol = boardFlipped ? (BOARD_SIZE - 1 - visualCol) : visualCol;
                    
                    if (isValidPosition(targetRow, targetCol)) {
                        Move move = new Move(dragSourceRow, dragSourceCol, targetRow, targetCol);
                        Piece pieceToMove = gameBoard.getPiece(dragSourceRow, dragSourceCol);
                        
                        if (pieceToMove != null) {
                            List<Move> validMoves = pieceToMove.getValidMoves(gameBoard);
                            Move legalMove = null;
                            
                            for (Move m : validMoves) {
                                if (m.toRow == targetRow && m.toCol == targetCol && 
                                    !gameBoard.wouldBeInCheckAfterMove(m, currentPlayerTurn)) {
                                    legalMove = m;
                                    break;
                                }
                            }
                            
                            if (legalMove != null) {
                                executeMove(legalMove);
                            }
                        }
                    }
                    
                    resetBoardVisuals();
                }
            }
        });
    }

    private ImageIcon loadPieceImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        
        if (pieceImages.containsKey(imagePath)) {
            return pieceImages.get(imagePath);
        }
        
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.err.println("Image file not found: " + imagePath);
                return null;
            }
            
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                System.err.println("Failed to read image: " + imagePath);
                return null;
            }
            
            int width = SQUARE_SIZE - 2 * PIECE_PADDING;
            int height = SQUARE_SIZE - 2 * PIECE_PADDING;
            
            BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImg.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(img, 0, 0, width, height, null);
            g2d.dispose();
            
            ImageIcon icon = new ImageIcon(resizedImg);
            pieceImages.put(imagePath, icon);
            
            return icon;
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    private void updateSquare(int row, int col) {
        int visualRow = boardFlipped ? (BOARD_SIZE - 1 - row) : row;
        int visualCol = boardFlipped ? (BOARD_SIZE - 1 - col) : col;
        
        Piece piece = gameBoard.getPiece(row, col);
        JButton square = squares[visualRow][visualCol];

        Color defaultBgColor = (visualRow + visualCol) % 2 == 0 ? Color.WHITE : Color.GRAY;

        boolean isSelected = (row == selectedRow && col == selectedCol);
        boolean isValidMoveTarget = false;
        ImageIcon currentIcon = null;

        boolean isKingInCheck = false;
        if (piece instanceof Components.King) {
            Components.King king = (Components.King) piece;
            isKingInCheck = gameBoard.isKingInCheck(king.getColor());
        }

        if (selectedRow != -1 && !isSelected) {
            Piece selectedPiece = gameBoard.getPiece(selectedRow, selectedCol);
            if (selectedPiece != null) {
                List<Move> validMoves = selectedPiece.getValidMoves(gameBoard);
                List<Move> legalMoves = new ArrayList<>();
                
                for (Move move : validMoves) {
                    if (!gameBoard.wouldBeInCheckAfterMove(move, selectedPiece.getColor())) {
                        legalMoves.add(move);
                    }
                }
                
                for (Move move : legalMoves) {
                    if (move.toRow == row && move.toCol == col) {
                        isValidMoveTarget = true;
                        if (piece == null) {
                            square.setBackground(new Color(150, 200, 150, 150));
                            currentIcon = dotIcon;
                        } else {
                            square.setBackground(new Color(255, 100, 100, 150));
                        }
                        break;
                    }
                }
            }
        }

        if (isSelected) {
            square.setBackground(new Color(100, 150, 255, 180));
        } else if (isKingInCheck) {
            square.setBackground(new Color(255, 0, 0, 128));
        } else if (!isValidMoveTarget) {
            square.setBackground(defaultBgColor);
        }

        if (piece != null) {
            String imagePath = piece.getImage();
            ImageIcon pieceIcon = loadPieceImage(imagePath);
            if (pieceIcon != null) {
                square.setIcon(pieceIcon);
                square.setText("");
                square.setHorizontalAlignment(SwingConstants.CENTER);
                square.setVerticalAlignment(SwingConstants.CENTER);
            } else {
                square.setIcon(null);
                square.setText(piece.toString());
                System.err.println("Failed to load icon for: " + imagePath);
            }
        } else {
            square.setIcon(currentIcon);
            square.setText("");
        }
    }

    private void resetBoardVisuals() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                updateSquare(i, j);
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        int boardRow = boardFlipped ? (BOARD_SIZE - 1 - row) : row;
        int boardCol = boardFlipped ? (BOARD_SIZE - 1 - col) : col;
        
        Piece clickedPiece = gameBoard.getPiece(boardRow, boardCol);

        if (selectedRow == -1) {
            if (clickedPiece != null && clickedPiece.getColor() == currentPlayerTurn) {
                selectedRow = boardRow;
                selectedCol = boardCol;
                resetBoardVisuals();
            }
        } else {
            Move potentialMove = new Move(selectedRow, selectedCol, boardRow, boardCol);
            Piece pieceToMove = gameBoard.getPiece(selectedRow, selectedCol);

            boolean isValidTarget = false;
            if (pieceToMove != null && pieceToMove.getColor() == currentPlayerTurn) {
                List<Move> validMoves = pieceToMove.getValidMoves(gameBoard);
                for (Move move : validMoves) {
                    if (move.equals(potentialMove) && !gameBoard.wouldBeInCheckAfterMove(move, currentPlayerTurn)) {
                        isValidTarget = true;
                        potentialMove = move;
                        break;
                    }
                }
            }

            if (isValidTarget) {
                executeMove(potentialMove);

                selectedRow = -1;
                selectedCol = -1;

                resetBoardVisuals();
            } else if (boardRow == selectedRow && boardCol == selectedCol) {
                selectedRow = -1;
                selectedCol = -1;
                resetBoardVisuals();

            } else if (clickedPiece != null && clickedPiece.getColor() == pieceToMove.getColor()) {
                selectedRow = boardRow;
                selectedCol = boardCol;
                resetBoardVisuals();
            } else {
                selectedRow = -1;
                selectedCol = -1;
                resetBoardVisuals();
            }
        }
    }

    private void recordMove(String moveNotation) {
        if (currentPlayerTurn == Components.Color.WHITE) {
            moveHistory.append(moveCounter + ". " + moveNotation);
        } else {
            moveHistory.append(" " + moveNotation + "\n");
            moveCounter++;
        }
    }

    private ImageIcon createDotIcon() {
        int dotSize = SQUARE_SIZE / 4;
        BufferedImage image = new BufferedImage(dotSize, dotSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(50, 50, 50, 180));
        g2d.fillOval(0, 0, dotSize, dotSize);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private String getMoveNotation(Move move, Piece piece) {
        if (move.isCastling) {
            if (move.isKingSideCastling) {
                return "O-O";
            }
            else if (move.isQueenSideCastling) {
                return "O-O-O";
            }
        }
        
        String pieceSymbol = piece.toString().toUpperCase().substring(0, 1);
        if (piece.toString().toUpperCase().contains("PAWN")) {
            pieceSymbol = "";
        }
        String from = getSquareNotation(move.fromRow, move.fromCol);
        String to = getSquareNotation(move.toRow, move.toCol);

        return pieceSymbol + from + "-" + to;
    }

    private String getSquareNotation(int row, int col) {
        char file = (char) ('a' + col);
        int rank = BOARD_SIZE - row;
        return "" + file + rank;
    }

    public void resetBoard() {
        gameBoard.resetBoard();
        selectedRow = -1;
        selectedCol = -1;
        currentPlayerTurn = Components.Color.WHITE;
        moveCounter = 1;
        
        moveHistory.setText("");
        
        resetBoardVisuals();
        
        updateEvaluation();
        
        updateFEN();
    }
    
    public void setPlayerColor(Components.Color playerColor) {
        resetBoard();
        
        currentPlayerTurn = Components.Color.WHITE;
        
        boardFlipped = (playerColor == Components.Color.BLACK);
        resetBoardVisuals();
    }

    public void setMoveHistory(JTextArea moveHistory) {
        this.moveHistory = moveHistory;
    }

    @Override
    public Dimension getPreferredSize() {
        int size = SQUARE_SIZE * BOARD_SIZE;
        return new Dimension(size, size);
    }
    
    public void updateSquareSize(int containerSize) {
        SQUARE_SIZE = containerSize / BOARD_SIZE;
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
            }
        }
        
        pieceImages.clear();
        dotIcon = createDotIcon();
        
        resetBoardVisuals();
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (isDragging && draggedPieceIcon != null) {
            Point mousePosition = getMousePosition();
            if (mousePosition != null) {
                int draggedPieceWidth = draggedPieceIcon.getIconWidth();
                int draggedPieceHeight = draggedPieceIcon.getIconHeight();
                
                draggedPieceIcon.paintIcon(this, g, mousePosition.x - draggedPieceWidth / 2, mousePosition.y - draggedPieceHeight / 2);
            }
        }
    }

    public void flipBoard() {
        boardFlipped = !boardFlipped;
        resetBoardVisuals();
    }

    public void setEvaluationBar(EvaluationBar evaluationBar) {
        this.evaluationBar = evaluationBar;
        updateEvaluation();
    }
    
    private void updateEvaluation() {
        if (evaluationBar != null) {
            int evaluation = gameBoard.evaluateBoard();
            evaluationBar.setEvaluation(evaluation);
        }
    }
    
    public void setFENTextArea(JTextArea FENTextArea) {
        this.FENTextArea = FENTextArea;
        updateFEN();
    }
    
    private void updateFEN() {
        if (FENTextArea != null) {
            String fenString = FEN.generateFEN(gameBoard, currentPlayerTurn, moveCounter);
            FENTextArea.setText(fenString);
        }
    }
    
    private void executeMove(Move move) {
        Piece movingPiece = gameBoard.getPiece(move.fromRow, move.fromCol);
        
        String moveNotation = getMoveNotation(move, movingPiece);
        gameBoard.movePiece(move);
        
        currentPlayerTurn = (currentPlayerTurn == Components.Color.WHITE) ?
                           Components.Color.BLACK : Components.Color.WHITE;
        
        recordMove(moveNotation);
        moveHistory.setCaretPosition(moveHistory.getDocument().getLength());
        
        updateEvaluation();
        updateFEN();
    }
}