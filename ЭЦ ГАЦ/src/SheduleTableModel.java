import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;


public class SheduleTableModel extends DefaultTableModel{
	String[] columnShedule;
	Object[][] dataShedule;
	CalendarTableModel ctm;
	
	public SheduleTableModel() {
		// TODO Auto-generated constructor stub
		columnShedule = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

		dataShedule = new Object[][]{
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
						
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}};
	}
	
	
	public SheduleTableModel(int indx) {
		// TODO Auto-generated constructor stub
	
		columnShedule = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
				"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

		dataShedule = new Object[][]{
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
						
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}};
	
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Calendar.out"));
     
			ctm = new CalendarTableModel(false);
			ctm.data = (Object[][])ois.readObject();
	
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		

		
		
		int k=(indx)*2;
		for(int i=0;i<dataShedule.length;i++){
			for(int j=0;j<columnShedule.length;j++){
				dataShedule[i][j]=ctm.data[k][j];
			}
			k++;
		}
	}
	
	
	
	
	

	public int getColumnCount(){
		return 28;
	}
	
	public int getRowCount(){
		return 2;
	}
	
	public Object getValueAt(int row, int col){
		return dataShedule[row][col];
	}
	
	public String getColumnName(int col){
		return columnShedule[col];
	}
	
	
	 public boolean isCellEditable(int row, int col){
		 return false; 
     }	 
	 
	 public void setValueAt(Object value, int row, int col) {

		 dataShedule[row][col] = value;
		 fireTableCellUpdated(row, col);
	 }
	 
	
	 
	 
}
