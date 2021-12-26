import java.awt.Component;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

public class CalendarTableEditor extends AbstractCellEditor implements TableCellEditor{
	JComponent comp = new JTextField();
	JTable table;
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,int rowIndex, int vColIndex){
		((JTextField)comp).setText((String)value); 
		String testStr = (String)value;
		try{
			int testInt = Integer.parseInt(testStr);
		}
		catch(Exception e){
			value=null;
		}
		
		
		return comp;
	}
		
	public Object getCellEditorValue(){
		return ((JTextField)comp).getText();
	}

}
