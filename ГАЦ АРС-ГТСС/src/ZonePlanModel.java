import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.table.DefaultTableModel;

import sun.security.util.Length;


public class ZonePlanModel extends DefaultTableModel{

	//Object[][] dataOfShedule;
	String[] columnPlan;
	Object[][] dataPlan;
	Object[][] dataWeekly;
	File file;
	
	public ZonePlanModel() {
		// TODO Auto-generated constructor stub
		
		columnPlan = new String[] {"№","По 4-х недельному","Годовые пункты"};
		dataPlan = new Object[31][3];
	}
	
	public ZonePlanModel(Object[][] dat, int kol) {
		// TODO Auto-generated constructor stub
		super(kol,3);
		
		columnPlan = new String[] {"№","По 4-х недельному","Годовые пункты"};
		dataPlan = new Object[31][3];
			
		int k=0;
		for(int i=0;i<dat.length;i++){
			
			for(int j=0;j<28;j++){
				if((dat[i][j])!=null){
					if(k>30) break;				//!!!
					dataPlan[k][0]=dat[i][j];
					
					Object[][] dataWeekly = getWeeklyModel();
					
					String str = "";
					for(int x=0;x<200;x++){
						if((dataWeekly[x][j+10])!=null){
							str+=dataWeekly[x][j+10].toString();
							
							char c[] = dataWeekly[x][j+10].toString().toCharArray();
							if(c.length>0){
								str+= ", ";
							}
						}
					}
				
					if(str.endsWith(", ")){
						str = str.substring(0, str.length()-2);
					}
					
					dataPlan[k][1]=str;
					
					k++;
				}
			}
		}
	}
	
	public Object getValueAt(int row, int col){
	
		try{
			if (dataPlan[row][col]==null){
				return "";
			}
			else{
				return dataPlan[row][col];
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	
	public String getColumnName(int col){
		return columnPlan[col].toString();
	}
	
	public boolean isCellEditable(int row, int col){
		
		
		return true;
    }	 
	
	
	public void setValueAt(Object value, int row, int col) {
		
		try{
			dataPlan[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}
	
	//Загрузим модель в таблицу из файла
	public Object[][] getWeeklyModel(){
		Object[][] data = new Object[200][38];
		try{
			if(new File("WeeklyShedule.out").exists()) {	// Проверка наличия файла	
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("WeeklyShedule.out"));
				        
				data = (Object[][])ois.readObject();
						 
				ois.close();	
			}
			else{
				file = new File("WeeklyShedule.out");
			}
		}	 
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return data;
	}
}
