import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;


public class SheduleTableRenderer extends DefaultTableCellRenderer{
	
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
		comp.setFont(new Font("Serif", Font.PLAIN, 11));
		
		/*
		for(int i=0;i<table.getColumnCount();i++){
			TableColumn columnM = table.getColumnModel().getColumn(i);
			columnM.setPreferredWidth(4);
		}
		*/
		return comp;		   
	 }

}
