package Utils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CustomTable extends JTable {
    private Object[] columnNames;
    private int cols, rows;
    private DefaultTableModel model;

    public CustomTable(int rows, int columns) {
        this.cols = columns;
        this.rows = rows;
        model = new DefaultTableModel(rows, columns);
        setModel(model);
    }

    public CustomTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    public CustomTable() {
        super();
    }
    public CustomTable(TableModel dm) {
        super(dm);
    }

    public void setHeaders(Object[] columnNames) {
        this.columnNames = columnNames;
    }

    public Object[] getHeaders() {
        return columnNames;
    }

    public Object[][] getData() {
        Object[][] data = new Object[getRowCount()][getColumnCount()];
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                data[i][j] = getValueAt(i, j);
            }
        }
        return data;
    }
}
