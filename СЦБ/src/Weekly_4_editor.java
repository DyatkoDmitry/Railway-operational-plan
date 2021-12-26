import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;


public class Weekly_4_editor extends AbstractCellEditor implements TableCellEditor{
	
	JComponent comp = new JTextField();
	JTable table;
	JComboBox jcb;
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,int rowIndex, int colIndex){
		
		table.revalidate();
		table.updateUI();
		
		((JTextField)comp).setText((String)value); 
		
			return comp;
		
	}
	
	public Object getCellEditorValue(){
	
		return ((JTextField)comp).getText();
		
	}
}
