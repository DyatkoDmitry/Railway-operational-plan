import javax.swing.*;

import java.awt.*;

import javax.swing.table.*;

public class CalendarTableRenderer extends DefaultTableCellRenderer{
	
	 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			 boolean hasFocus, int row, int column)
	 {
	
		 
	JLabel comp = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	
	//	 JTextField comp = (JTextField) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
		comp.setHorizontalAlignment(CENTER);

		comp.setForeground(Color.BLACK);
		comp.setEnabled(true);
		
		if ((column==5)||(column==6)||(column==12)||(column==13)||(column==19)||(column==20)||(column==26)||(column==27)){
			comp.setForeground(Color.RED);
		}
		
		return comp;		   
	 }
}	

