package ru.amse.agregator.storage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import ru.amse.agregator.storage.ArchitectualAttraction;
import ru.amse.agregator.storage.Cafe;
import ru.amse.agregator.storage.City;
import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.storage.Hotel;
import ru.amse.agregator.storage.NaturalAttraction;
import ru.amse.agregator.storage.StorageObject;
import ru.amse.agregator.storage.BasicStorageObject;

public class UI extends JFrame implements TableModelListener{
	
	private JTable collectionsTable;
	private CollectionsTableModel tableModel = new CollectionsTableModel();
	private JPanel controls;
	private JList dbList;
	private JList collectionsList; 
	private Container container;
	private String[] dbNames = {DataBase.MAIN_DB_NAME, DataBase.DIRTY_DB_NAME};
	
	public UI() {
		super("Mongo DB Test");
		DataBase.connectToMainBase();

		JMenuItem addItem = new JMenuItem("Add row");
		addItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				try{
				tableModel.addRow();
			}catch (Exception e) {
				System.out.println("Epic Fail");
			}
			
		}});

		JMenuItem deleteItem = new JMenuItem("Delete Row");
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.removeRow(collectionsTable.getSelectedRow());
			}
		});
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(addItem);
		bar.add(deleteItem);

		
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		controls = new JPanel();
		controls.setLayout(new GridLayout(2, 1));
		
		dbList = new JList(dbNames);
		dbList.setVisibleRowCount(2);
		dbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(dbList));
		
		collectionsList = new JList();
		collectionsList.setVisibleRowCount(3);
		collectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(collectionsList));
		
		collectionsTable = new JTable();
		collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel.addTableModelListener(this);
		container.add(collectionsTable.getTableHeader(), BorderLayout.PAGE_START);
		container.add((collectionsTable), BorderLayout.CENTER);
		
		dbList.addListSelectionListener(
			new ListSelectionListener(){

				public void valueChanged(ListSelectionEvent event) {
				try{	if (dbList.getSelectedIndex() ==0)
						DataBase.connectToMainBase();
					else
						DataBase.connectToDirtyBase();
					Vector<String> collectionsNames = new Vector<String>();
					Set<String> collectionsNamesSet = DataBase.myDB.getCollectionNames();
					for (String collectionName : collectionsNamesSet) {
						collectionsNames.add(collectionName);
					}
					collectionsList.setListData(collectionsNames);
					//collectionsList.setSelectedValue(collectionsNames.get(0), true);
				}
				catch (Exception e) {
					System.out.print("ValueChanged!!!!!!!!!!!!");
				}
				}
				
			}
		);
		
		collectionsList.addListSelectionListener(
			new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					try{container.remove(collectionsTable);
					container.remove(collectionsTable.getTableHeader());
					container.repaint();
					
					Vector<String> s = DataBase.getCollectionAttributes(collectionsList.getSelectedValue().toString());
					Vector<Vector<Object>> ss = DataBase.getCollectionValues(collectionsList.getSelectedValue().toString(),s);
					tableModel.columnNames = s;
					tableModel.rowData = ss;
				
					collectionsTable = new JTable(tableModel);
					collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					container.add(collectionsTable, BorderLayout.CENTER);
					container.add(collectionsTable.getTableHeader(), BorderLayout.PAGE_START);
					container.repaint();
					collectionsTable.revalidate();
					collectionsTable.repaint();
					}
					catch (Exception e) {
					}
					}
				
			}
		);
		
		container.add(controls, BorderLayout.EAST);
		
		setSize(600, 400);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		(new UI()).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class CollectionsTableModel extends AbstractTableModel {

		public Vector<String> columnNames = new Vector<String>();
		public Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		{
			for (Vector<Object> vector : rowData) {
				vector = new Vector<Object>();
			}
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			int i = 0;
			while( (i < rowData.size())&&( getValueAt(i, columnIndex)==null)) {
					i++;	
				}
			if ( i == rowData.size()){
			
				rowData.get(0).set(columnIndex,"");
				return getValueAt(0, columnIndex).getClass();
			}
			return getValueAt(i, columnIndex).getClass();
		}

		@Override
		public int getColumnCount() {
			return columnNames.size();
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames.get(columnIndex);
		}

		@Override
		public int getRowCount() {
			return rowData.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if( (columnIndex >= rowData.get(rowIndex).size()))  return ("777777");
			return rowData.get(rowIndex).get(columnIndex);
			
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Vector<Object> v = rowData.get(rowIndex);
			v.set(columnIndex, aValue);
			rowData.set(rowIndex, v);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		
		public void addRow() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			Vector<Object> v = new Vector<Object>();
			
			if (dbList.getSelectedIndex() ==0)
				DataBase.connectToMainBase();
			else
				DataBase.connectToDirtyBase();
			String collectionName = collectionsList.getSelectedValue().toString();
//			Object obj = c.newInstance(); //дефолтный конструктор
//			DataBase.add(()obj);
			ObjectId id;
			
			if (collectionName.equalsIgnoreCase(DataBase.COLLECTION_ATTRACTIONS)) {
				id = DataBase.add( new ArchitectualAttraction()); 
			} else if (collectionName.equalsIgnoreCase(DataBase.COLLECTION_CAFE)) {
				id = DataBase.add(new Cafe());
			} else if (collectionName.equalsIgnoreCase(DataBase.COLLECTION_CITIES)) {
				id = DataBase.add(new City());
			} else {
				id = DataBase.add(new Hotel());
			} 
			v.add(id);
			for ( int i = 1; i < columnNames.size();++i){
				v.add(null);
			}
		    
			rowData.add(v);
			
			collectionsTable.revalidate();
			collectionsTable.repaint();
		}
		
		public void removeRow(int index) {
			int temp = 0;
			for ( int i = 0; i < columnNames.size();++i){
				if (columnNames.get(i).equalsIgnoreCase("_id")) temp = i;
			}
		
			ObjectId id =(ObjectId )rowData.get(index).get(temp);
			DataBase.myDB.getCollection(collectionsList.getSelectedValue().toString()).remove(new BasicDBObject("_id",id));
			rowData.remove(index);
			collectionsTable.revalidate();
			collectionsTable.repaint();
			fireTableRowsDeleted(index, index);
		}
	
	}

	@Override
	public void tableChanged(TableModelEvent e) {
//		int temp = 0;
//		for ( int i = 0; i < CollectionsTableModel.columnNames.size();++i){
//			if (columnNames.get(i).equalsIgnoreCase("_id")) temp = i;
//		}
//		
		//JOptionPane.showMessageDialog(null, "что-то произошло");
		
	}
	
}