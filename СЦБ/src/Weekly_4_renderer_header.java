import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class Weekly_4_renderer_header extends JTextArea implements TableCellRenderer{
	public Weekly_4_renderer_header() {
		// TODO Auto-generated constructor stub
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		// TODO Auto-generated method stub
		if (table != null){

			JTableHeader header = table.getTableHeader(); 
			
			if (header != null){

				setForeground(header.getForeground());

				setBackground(header.getBackground());
				
				setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				if(col==1){
					setFont(new Font("Tahoma", Font.PLAIN, 9));	
				}
			}

		}

		setText((value == null) ? "" : value.toString()); 
		setBorder(UIManager.getBorder("TableHeader.cellBorder")); 
		return this;
		
	}

}
