package ru.amse.agregator.storage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class UI extends JFrame implements TableModelListener{

	private static final long serialVersionUID = 1L;
	
	private JTable collectionsTable;
	private CollectionsTableModel tableModel = new CollectionsTableModel();
	private JPanel controls;
	private JList dbList;
	private JList collectionsList; 
	private JList typeList;
	
	private Container container;
	private String[] dbNames = {DataBase.MAIN_DB_NAME, DataBase.DIRTY_DB_NAME};
	public UI() {
		super("Mongo DB Test");

		
		DataBase.connectToMainBase();
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		controls = new JPanel();
		controls.setLayout(new GridLayout(3, 1));
		
		dbList = new JList(dbNames);
		dbList.setVisibleRowCount(2);
		dbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(dbList));
		
		collectionsList = new JList();
		collectionsList.setVisibleRowCount(3);
		collectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(collectionsList));
		
		typeList = new JList();
		typeList.setVisibleRowCount(3);
		typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(typeList));
		
		collectionsTable = new JTable();
        collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableModel.addTableModelListener(this);
        container.add(collectionsTable.getTableHeader(), BorderLayout.PAGE_START);
        container.add((collectionsTable), BorderLayout.CENTER);
        
		dbList.addListSelectionListener(
			new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent event) {
				try{	
					if (dbList.getSelectedIndex() ==0)
						DataBase.connectToMainBase();
					else
						DataBase.connectToDirtyBase();
					Vector<String> collectionsNames = new Vector<String>();
					Set<String> collectionsNamesSet = DataBase.getDB().getCollectionNames();
					for (String collectionName : collectionsNamesSet) {
						collectionsNames.add(collectionName);
					}
					collectionsList.setListData(collectionsNames);
			
					}
					catch (Exception e) {
						System.out.print("ValueChanged!!!!!!!!!!!!");
					}
					}
					
				}
			);
			
		collectionsList.addListSelectionListener(
				new ListSelectionListener(){

				public void valueChanged(ListSelectionEvent event) {
					try{	
						Vector<String> typesNames = new Vector<String>();
						Set<String> typesNamesSet = DataBase.getTypesNames(collectionsList.getSelectedValue().toString());
						for (String typeName : typesNamesSet) {
							typesNames.add(typeName);
						}
						typeList.setListData(typesNames);
				
						}
						catch (Exception e) {
							System.out.print("ValueChanged!!!!!!!!!!!!");
						}
						}
						
					}
				);
		typeList.addListSelectionListener(
				new ListSelectionListener(){

				public void valueChanged(ListSelectionEvent event) {
					try{	
						container.remove(collectionsTable);
						container.remove(collectionsTable.getTableHeader());
						container.repaint();
						
						Set<String> s = DataBase.getAttributsNames(typeList.getSelectedValue().toString());
						Vector<String> v = new Vector<String>();
						for (String str : s){
							v.add(str);
						}
						Vector<Vector<Object>> ss = DataBase.getCollectionValues(typeList.getSelectedValue().toString(),s);
						tableModel.columnNames = v;
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
							System.out.print("ValueChanged!!!!!!!!!!!!\n");
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

		private static final long serialVersionUID = 1L;

		public Vector<String> columnNames = new Vector<String>();
        public Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
        {
                for (Vector<Object> vector : rowData) {
                        vector = new Vector<Object>();
                }
        }
        
        //@Override
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

        //@Override
        public int getColumnCount() {
                return columnNames.size();
        }

        //@Override
        public String getColumnName(int columnIndex) {
                return columnNames.get(columnIndex);
        }

        //@Override
        public int getRowCount() {
                return rowData.size();
        }

        //@Override
        public Object getValueAt(int rowIndex, int columnIndex) {
                if( (columnIndex >= rowData.get(rowIndex).size()))  return ("777777");
                return rowData.get(rowIndex).get(columnIndex);
                
        }

        //@Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
        }

        //@Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
              
        }
        
        public void addRow() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
                
        }
        
        public void removeRow(int index) {
              
        }

}
	//@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
