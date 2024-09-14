import Chart.BarChart;
import Chart.PieChart;

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
        setTitle("Hello world!");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        headerPanel = new JPanel();
        JButton btnGraph = new JButton("Graficar");
        headerPanel.add(btnGraph);
        btnGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = table.getData();
                Object headers[] = table.getHeaders();
                System.out.println("Headers: ");
                for (Object header : headers) {
                    System.out.print(header + " ");
                }
                System.out.println();
                for (Object[] row : data) {
                    for (Object cell : row) {
                        System.out.print(cell + " ");
                    }
                    System.out.println();
                }
                String type = JOptionPane.showInputDialog("¿Qué tipo de gráfico desea? (barras, pastel, lineas)");
                drawGraph(data, headers, type);
            }
        });
        //alinear boton a la parte superior derecha
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(headerPanel, BorderLayout.NORTH);

        table = new CustomTable(5, 5);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));
        sizePanel = new JPanel();
        sizePanel.add(new JLabel("Filas: "));
        txtRows = new JTextField(5);
        sizePanel.add(txtRows);
        sizePanel.add(new JLabel("Columnas: "));
        txtCols = new JTextField(5);
        sizePanel.add(txtCols);
        JButton btnChangeSize = new JButton("Cambiar tamaño");
        btnChangeSize.addActionListener(e -> {
            changeTableSize();
        });
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
        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void changeTableSize() {
        try {
            int rows = Integer.parseInt(txtRows.getText());
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
