import Chart.BarChart;
import Chart.PieChart;
import Utils.Background;
import Utils.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {
    private CustomTable table;
    private JTextField txtRows, txtCols;
    private JTextField[] txtHeaders;
    private JPanel namePanel, sizePanel, headerPanel;
    private int columns;


    public Window() {
        setTitle("Maximos goleadores del futbol");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String path = "src/images/futbol.jpg";
        Background backgroundPanel = new Background(path);
        setContentPane(backgroundPanel);
        headerPanel = new JPanel();
        JButton btnGraph = new JButton("Graficar");
        JLabel title = new JLabel("Maximos goleadores del futbol");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(title);
        //añadir salto de linea
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        headerPanel.add(btnGraph);
        btnGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = table.getData();
                Object headers[] = table.getHeaders();
                if (headers == null || data == null || data.length == 0 || headers.length == 0) {
                    JOptionPane.showMessageDialog(backgroundPanel, "Por favor, ingrese nombres para las columnas");
                    return;
                }
                int selectedOption = JOptionPane.showOptionDialog(backgroundPanel, "Escoge un tipo de grafica", "Tipo de grafica",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Pastel", "Barras"}, 0);
                String type = (selectedOption == 0) ? "pastel" : "barras";
                drawGraph(data, headers, type);
            }
        });
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);  // Agregar el panel sobre el fondo

        table = new CustomTable(1, 5);
        JScrollPane scrollPane = new JScrollPane(table);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));
        sizePanel = new JPanel();
        // De momento solo habra un numero limitado de filas
        //sizePanel.add(new JLabel("Filas: "));
        //txtRows = new JTextField(5);
        //sizePanel.add(txtRows);
        sizePanel.add(new JLabel("Columnas: "));
        txtCols = new JTextField(5);
        sizePanel.add(txtCols);
        JButton btnChangeSize = new JButton("Cambiar tamaño");
        btnChangeSize.addActionListener(e -> changeTableSize());
        sizePanel.add(btnChangeSize);
        controlPanel.add(sizePanel);

        namePanel = new JPanel();
        columns = 5;
        txtHeaders = new JTextField[columns];
        for (int i = 0; i < columns; i++) {
            txtHeaders[i] = new JTextField(5);
            namePanel.add(new JLabel("Columna " + (i + 1)));
            namePanel.add(txtHeaders[i]);
        }
        JButton btnChangeNames = new JButton("Cambiar nombres");
        btnChangeNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeColumnNames();
            }
        });
        namePanel.add(btnChangeNames);
        controlPanel.add(namePanel);
        backgroundPanel.add(controlPanel, BorderLayout.SOUTH);  // Agregar el panel de control

        setVisible(true);
    }

    private void changeTableSize() {
        try {
            // De momento solo habra un numero limitado de filas
            //int rows = Integer.parseInt(txtRows.getText());
            int rows = 1;
            int cols = Integer.parseInt(txtCols.getText());
            columns = cols;
            if (columns > 5) {
                throw new Exception("No puedes tener más de 5 columnas");
            }
            table.setModel(new DefaultTableModel(rows, cols));
            txtHeaders = new JTextField[columns];
            for (int i = 0; i < columns; i++) {
                txtHeaders[i] = new JTextField(5);
            }
            removeComponent(namePanel, new Class[]{JLabel.class, JTextField.class, JButton.class});
            namePanel.repaint();
            for (int i = 0; i < columns; i++) {
                namePanel.add(new JLabel("Columna " + (i + 1)));
                namePanel.add(txtHeaders[i]);
            }
            JButton btnChangeNames = new JButton("Cambiar nombres");
            namePanel.add(btnChangeNames);
            btnChangeNames.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeColumnNames();
                }
            });
            namePanel.revalidate();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void changeColumnNames() {
        Object[] headers = new Object[columns];
        for (int i = 0; i < columns; i++) {
            if (txtHeaders[i] == null) {
                continue;
            }
            String name = txtHeaders[i].getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre válido");
                return;
            }
            headers[i] = name;
        }
        table.setHeaders(headers);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.fireTableStructureChanged();
        model.setColumnIdentifiers(headers);
    }

    private void removeComponent(JPanel panel, Class<?>[] types) {
        Component[] components = panel.getComponents();
        for (Class<?> type : types) {
            for (Component component : components) {
                if (type.isInstance(component)) {
                    panel.remove(component);
                }
            }
        }
    }
    private void drawGraph(Object[][] data, Object[] headers, String type) {
        if (type.equals("barras")) {
            new BarChart(data, headers);
        } else if (type.equals("pastel")) {
            new PieChart(data, headers);
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de gráfico no válido");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}
