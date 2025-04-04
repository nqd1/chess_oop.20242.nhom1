package Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Components.*;

public class testimg {
    public static void main(String[] args) {
        

        Piece rook = new Rook(Color.WHITE, 0, 0);
        String imagePath = rook.getImage();
        System.out.println(imagePath);
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            JLabel label = new JLabel(new ImageIcon(image));

            JFrame frame = new JFrame("img test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.add(label);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
