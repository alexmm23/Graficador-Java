package Utils;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private ImageIcon image;

    public Background(String path) {
        image = new ImageIcon(path);
        setLayout(new FlowLayout());
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
