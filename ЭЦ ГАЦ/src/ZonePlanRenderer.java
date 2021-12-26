import java.awt.Color;
import java.awt.Component;
import java.awt.Font;



import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class ZonePlanRenderer extends JTextArea implements TableCellRenderer{
	private final DefaultTableCellRenderer sourceRenderer = new DefaultTableCellRenderer();
	
	public ZonePlanRenderer() {
		 setLineWrap(true);
	     setWrapStyleWord(true);
	     setOpaque(true);
	
    }
	
	 int last_row = -1;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
		sourceRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
		
		setForeground(sourceRenderer.getForeground());
        setBackground(sourceRenderer.getBackground());
        setBorder(sourceRenderer.getBorder());
        setFont(new Font("Serif", Font.PLAIN, 13));
        //setFont(sourceRenderer.getFont());
        setText(sourceRenderer.getText());
        setText(value == null ? "" : value.toString());

        Rectangle rect = table.getCellRect(row, column, true);
        this.setSize(rect.getSize());//для установки ширины компоненты
               
        int height_wanted = (int) getPreferredSize().getHeight();
        setText(value == null ? "" : value.toString());
        if ((height_wanted > table.getRowHeight(row) | row != last_row) &  //если новая строчка и полученная высота больше чем установленна
                 height_wanted > table.getRowHeight()) //и высота больше чем дефолтная
            table.setRowHeight(row, height_wanted-1); 
        	//table.setRowMargin(1);
        	
        last_row = row;
        
        try{
        	char c[] = value.toString().toCharArray();
        
        	if(c.length==0){
        	
        		setBackground(new Color(213,220,225));
        		
        	}
        }
        catch(Exception exc){
        	//exc.printStackTrace();
        }
       
        return this;
      
	}
}
