import java.io.Serializable;

import javax.swing.event.TableModelEvent;
import javax.swing.table.*;

public class CalendarTableModel extends DefaultTableModel implements Serializable{

	String[] columnNames;
	Object[][] data;
	
	CalendarTableModel(boolean flag){
		
		columnNames = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
		"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
		"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
		"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		
		if(flag){
		data = new Object[][]{
				{null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"},
				{"25","26","27","28","29","30","31",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"},
				{"22","23","24","25","26","27","28","29",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"},
				{"21","22","23","24","25","26","27","28","29","30","31",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"},
				{"18","19","20","21","22","23","24","25","26","27","28","29","30",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"},
				{"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12"},
				{"13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30",null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7","8","9","10"},
				{"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",null,null,null,null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4","5","6","7"},
				{"8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",null,null,null,null},
				
				{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"1","2","3","4"},
				{"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30",null,null},
				
				{"3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"},
				{"31",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"},
				{"28","29","30",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
				
				{null,null,null,"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"},
				{"26","27","28","29","30","31",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}};
	}
		else{
			data = new Object[][]{
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}};
		}
	}	
	



	public int getColumnCount(){
		return 28;
	}
	
	public int getRowCount(){
		return 24;
	}
	
	public Object getValueAt(int row, int col){
		return data[row][col];
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}
	
	
	 public boolean isCellEditable(int row, int col){
		 return true; 
     }	 
	 
	 public void setValueAt(Object value, int row, int col) {
		 String testStr = (String)value;

		 try{
			 int testInt = Integer.parseInt(testStr);
			 
			 if((testInt>31)||(testInt<1)){
				  value=null;
			 }
			 
		 }
		 catch(Exception e){
			 value=null;
		 }
		  
		 
		 data[row][col] = value;
		 fireTableCellUpdated(row, col);
	 }
}
