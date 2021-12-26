import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;


public class Weekly_4_model extends DefaultTableModel implements Serializable{
	Object[] columnsWeekly;
	Object[][] dataWeekly;
	static boolean editFlag;
	public Weekly_4_model() {
		// TODO Auto-generated constructor stub
		
		columnsWeekly = new Object[] {"№","СТП 09150 СТП 19.270 СТП 19150","Наименование устройств и производимых работ","Исполнитель",
				"Периодичность выполнения работ","Измеритель","Норма времени на измеритель(мин.)","Число объектов","Общие затраты на проверку (чел. мин.)","Оформление результатов",
		" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};
	
		dataWeekly = new Object[200][38];	
		editFlag=true;
	}
	
	
	public Weekly_4_model(int rowCounts, int columnCounts) {
		// TODO Auto-generated constructor stub
		
		super(rowCounts,columnCounts);
		columnsWeekly = new Object[] {"№","СТП 09150 СТП 19.270 СТП 19150","Наименование устройств и производимых работ","Исполнитель",
				"Периодичность выполнения работ","Измеритель","Норма времени на измеритель(мин.)","Число объектов","Общие затраты на проверку (чел. мин.)","Оформление результатов",
				" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};
		
		dataWeekly = new Object[200][38];	
		editFlag=true;
			
	}
	
	public int getColumnCount(){
		return 38;
	}
	
	public Object getValueAt(int row, int col){
	
		try{
		
			return dataWeekly[row][col];
			
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	
	public String getColumnName(int col){
		return columnsWeekly[col].toString();
	}
	
	public boolean isCellEditable(int row, int col){
		
		if (editFlag==true)		
		 return true; 
		else{
			if ((col==0)||(col==2)||(col==4)||(col==15)||(col==16)||(col==22)||(col==23)||(col==29)||(col==30)||(col==36)||(col==37))
				return false;
		}
		return true;
    }	 
	
	
	public void setValueAt(Object value, int row, int col) {
		
		try{
			dataWeekly[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
}
