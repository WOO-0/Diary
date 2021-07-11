import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddTimeTable extends JFrame {
	private MainProcess main;
//	private UserDAO dao = new UserDAO();
	private JPanel panel;
	private JTable table;
	DefaultTableModel model;
	private JScrollPane scrollTable;
	private JComboBox<String> comBox;
	private JTextField searchText;
	private JButton searchBut;

	private JButton addBut;
	private JButton cancelBut;
	private String header[] = main.dao.loadTableHeader();
	private String data[][] = main.dao.loadTableData(header);
	ListenButtons butListener = new ListenButtons();

	public AddTimeTable(MainProcess main) {
		this.main=main;
		setTitle("AddTimeTable");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		comBox = new JComboBox<String>(header);
		comBox.setBounds(200, 10, 130, 23);
		searchText = new JTextField();
		searchText.setBounds(340, 10, 250, 23);
		searchBut = new JButton("검색");
		searchBut.addActionListener(butListener);
		searchBut.setBounds(600, 10, 60, 23);

		model = new DefaultTableModel(data, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(0);
		resizeColumnWidth(table);
		scrollTable = new JScrollPane(table);
		scrollTable.setBounds(0, 50, 875, 400);

		addBut = new JButton("추가");
		addBut.addActionListener(butListener);
		addBut.setBounds(330, 460, 60, 23);
		cancelBut = new JButton("취소");
		cancelBut.addActionListener(butListener);
		cancelBut.setBounds(480, 460, 60, 23);

		add(addBut);
		add(cancelBut);
		add(scrollTable);
		add(searchBut);
		add(searchText);
		add(comBox);

		setSize(880, 520);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	private class ListenButtons implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchBut) {
				model.setRowCount(0);
				int searchCol = -1;
				for (int i = 0; i < header.length; i++) {
					if (header[i].equals(comBox.getSelectedItem())) {
						searchCol = i;
						break;
					}
				}
				if (searchCol < 0) {
					return;
				}
				if ("".equals(searchText.getText())) {
					model.setRowCount(0);
					for (int i = 0; i < data.length; i++) {
						model.addRow(data[i]);
					}
				} else {
					int j = data.length;

					for (int i = 0; i < j; i++) {
						if (data[i][searchCol].trim().contains(searchText.getText())) {
							model.addRow(data[i]);
						}
					}
				}
			} else if (e.getSource() == addBut) {
				
				int row = table.getSelectedRow();
			    String[] objects = new String[table.getColumnCount()];
				for (int column = 0; column < objects.length; column++) {
				      objects[column] = (String) table.getValueAt(row, column);
				    }
				boolean ok = main.dao.saveTableData(main.id,header, objects);
				if (ok == true) {
					main.disposeTimeTableView();
					dispose();
					main.showTimetable();
				} else
					JOptionPane.showMessageDialog(null, "정상적으로 처리되지 않았습니다.");
			} else if (e.getSource() == cancelBut) {
				dispose();
			}
		}
		
		
	}

}