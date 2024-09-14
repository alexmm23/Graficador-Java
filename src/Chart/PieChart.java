package Chart;

import javax.swing.*;
import java.awt.*;

public class PieChart extends JPanel {
    private Object[][] data;
    private Object[] headers;

    public PieChart(Object[][] data, Object[] headers) {
        this.data = data;
        this.headers = headers;
        JFrame frame = new JFrame("Gráfica de Pastel");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 100;
        int centerX = width / 2 - diameter / 2;
        int centerY = height / 2 - diameter / 2;

        // Sumar los valores para determinar los ángulos de cada rebanada
        int total = 0;
        for (Object[] row : data) {
            for (int i = 0; i < row.length; i++) {
                total += Integer.parseInt(row[i].toString());
            }
        }
        // Dibujar las secciones con degradado
        int currentAngle = 0;
        int currentIndex = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.println(data[i][j]);
                int value = 0;
                try {
                    value = Integer.parseInt(data[i][j].toString());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese solo números enteros", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int angle = (int) Math.round(((double) value / total) * 360);

                // Crear un degradado para cada rebanada
                GradientPaint gradient = new GradientPaint(centerX, centerY, getColor(currentIndex), centerX + diameter, centerY + diameter, Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillArc(centerX, centerY, diameter, diameter, currentAngle, angle);

                // Dibujar leyenda
                g2d.setColor(getColor(currentIndex));
                g2d.fillRect(50, 50 + currentIndex * 20, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.drawString(headers[j].toString() + " (" + value + ")", 70, 60 + currentIndex * 20);
                currentAngle += angle;
                currentIndex++;
            }
        }
    }

    private Color getColor(int index) {
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE};
        return colors[index % colors.length];
    }

}
