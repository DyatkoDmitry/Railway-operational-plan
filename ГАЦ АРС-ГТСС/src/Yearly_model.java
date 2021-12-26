import java.io.Serializable;

import javax.swing.table.DefaultTableModel;

public class Yearly_model extends DefaultTableModel implements Serializable{
	Object[] columnsYearly;
	Object[][] dataYearly;
	static boolean editFlag2;
	
	public Yearly_model() {
		// TODO Auto-generated constructor stub
		columnsYearly = new Object[] {"№","СТП 09150 СТП 19.270 СТП 19150","Наименование устройств и производимых работ","Исполнитель",
				"Периодичность выполнения работ","Измеритель","Норма времени на измеритель(мин.)","Число объектов","Общие затраты на проверку (чел. мин.)","Оформление результатов",
		" "," "," "," "," "," "," "," "," "," "," "," "};
	
		dataYearly = new Object[200][22];	
		editFlag2=true;
	}
	
	public Yearly_model(int rowCounts, int columnCounts) {
		// TODO Auto-generated constructor stub
		
		super(rowCounts,columnCounts);
		columnsYearly = new Object[] {"№","СТП 09150 СТП 19.270 СТП 19150","Наименование устройств и производимых работ","Исполнитель",
				"Периодичность выполнения работ","Измеритель","Норма времени на измеритель(мин.)","Число объектов","Общие затраты на проверку (чел. мин.)","Оформление результатов",
		" "," "," "," "," "," "," "," "," "," "," "," "};
	
		dataYearly = new Object[200][22];	
		editFlag2=true;	
	}
	
	public int getColumnCount(){
		return 22;
	}
	
	public Object getValueAt(int row, int col){
	
		try{
		
			return dataYearly[row][col];
			
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	
	public String getColumnName(int col){
		return columnsYearly[col].toString();
	}
	
	public boolean isCellEditable(int row, int col){
		
		if (editFlag2==true)		
		 return true; 
		else{
			if ((col==0)||(col==2)||(col==4))
				return false;
		}
		return true;
    }	 
	
	
	public void setValueAt(Object value, int row, int col) {
		
		try{
			dataYearly[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
}
