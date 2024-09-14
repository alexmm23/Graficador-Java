package Chart;

import javax.swing.*;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BarChart extends JPanel {
    private Object[][] data;
    private Object[] headers;

    public BarChart(Object[][] data, Object[] headers) {
        this.data = data;
        this.headers = headers;
        JFrame frame = new JFrame("Grafica de barras");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth() - 100;
        int height = getHeight();
        int numBars = getDataSize();
        int barWidth = width / numBars;
        int max = getMaxValue();
        int barIndex = 0;
        g2d.drawLine(50, 50, 50, height - 40);
        g2d.drawString("0", 5, height - 50);
        g2d.drawString(String.valueOf(max), 5, 50);
        g2d.drawString("Goles", 5, 300);
        g2d.drawLine(50, height - 40, width + 100, height - 40);
        g2d.drawString("0", 25, height - 35);

        for (int i = 0; i < data.length; i++) {
            if (max == 0) {
                System.out.println("No hay datos");
                JOptionPane.showMessageDialog(null, "No hay datos para graficar", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
            for (int j = 0; j < data[i].length; j++) {
                System.out.println(data[i][j]);
                int value = Integer.parseInt(data[i][j].toString());
                System.out.println(value);
                System.out.println(max);
                //int y = (int) ((1.0 - (double) value / max) * height);
                int barHeight = (int) ((double) value / max * height * 0.8);
                int x = barIndex * barWidth + 50;
                int y = height - barHeight - 50;
                g2d.setPaint(createTexture(barIndex));
                g2d.fillRect(x, y, barWidth - 2, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, barWidth - 2, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(value), x, y);
                g2d.drawString(String.valueOf(headers[j]), x, height - 10);
                barIndex++;
            }
        }

    }

    private TexturePaint createTexture(int index) {
        BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        if (index % 2 == 0) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 10, 10);
            g2d.setColor(Color.GREEN);
            g2d.drawOval(2, 2, 6, 6);
        } else {
            //textura de lineas diagonales
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 10, 10);
            g2d.setColor(Color.GREEN);
            g2d.drawLine(0, 0, 10, 10);
        }
        return new TexturePaint(bufferedImage, new Rectangle(10, 10));
    }

    public int getMaxValue() {
        int max = 0;
        for (Object[] row : data) {
            for (Object cell : row) {
                int value = Integer.parseInt(cell.toString());
                if (value > max) {
                    max = value;
                }
            }
        }
        return max;
    }

    public int getDataSize() {
        int size = 0;
        for (Object[] row : data) {
            size += row.length;
        }
        return size;
    }
}
