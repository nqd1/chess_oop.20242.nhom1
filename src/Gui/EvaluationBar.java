package GUI;

import javax.swing.*;
import java.awt.*;
import Components.Color;

public class EvaluationBar extends JPanel {
    private static final int BAR_WIDTH = 20;
    private static final int BAR_HEIGHT = 400;
    private static final int MAX_ADVANTAGE = 2000;
    
    private int evaluation = 0;
    
    public EvaluationBar() {
        setPreferredSize(new Dimension(BAR_WIDTH, BAR_HEIGHT));
        setBackground(java.awt.Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY));
    }
    
    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = getWidth();
        int height = getHeight();
        
        int middlePoint = height / 2;
        
        int clampedEval = Math.max(-MAX_ADVANTAGE, Math.min(MAX_ADVANTAGE, evaluation));
        
        float percentage = (float) Math.abs(clampedEval) / MAX_ADVANTAGE;
        percentage = Math.min(1.0f, percentage);
        
        int barSize = (int)(height * percentage / 2);
        
        if (evaluation > 0) {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, middlePoint, width, barSize);
        }
        else if (evaluation < 0) {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, middlePoint - barSize, width, barSize);
        }
        
        g.setColor(java.awt.Color.GRAY);
        g.drawLine(0, middlePoint, width, middlePoint);
        
        String evalText = Math.abs(evaluation / 100.0) + "";
        if (evalText.length() > 4) {
            evalText = evalText.substring(0, 4);
        }
        
        g.setColor(java.awt.Color.BLACK);
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(evalText);
        
        if (evaluation > 0) {
            g.drawString(evalText, (width - textWidth) / 2, middlePoint + barSize + metrics.getHeight());
        } else if (evaluation < 0) {
            g.drawString(evalText, (width - textWidth) / 2, middlePoint - barSize - 2);
        } else {
            g.drawString("0.0", (width - textWidth) / 2, middlePoint - 2);
        }
    }
} 