import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class Weekly_4_ren_2col extends JTextArea implements TableCellRenderer{
	private final DefaultTableCellRenderer sourceRenderer = new DefaultTableCellRenderer();
	
	public Weekly_4_ren_2col() {
		// TODO Auto-generated constructor stub
		 setLineWrap(true);
	     setWrapStyleWord(true);
	     setOpaque(true);
	}
	
	 int last_row = -1;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

	sourceRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
		setForeground(sourceRenderer.getForeground());
        setBackground(sourceRenderer.getBackground());
        setBorder(sourceRenderer.getBorder());
        setFont(new Font("Serif", Font.PLAIN, 14));
        setText(sourceRenderer.getText());

        Rectangle rect = table.getCellRect(row, column, true);
        this.setSize(rect.getSize());//��� ��������� ������ ����������
               
        int height_wanted = (int) getPreferredSize().getHeight();
        setText(value == null ? "" : value.toString());
        if ((height_wanted > table.getRowHeight(row) | row != last_row) &  //���� ����� ������� � ���������� ������ ������ ��� ������������
                 height_wanted > table.getRowHeight()) //� ������ ������ ��� ���������
            table.setRowHeight(row, height_wanted+30); 
        	
        	table.setRowMargin(10);
        	
        last_row = row;
       
        try{
        	String str = value.toString();
        	char c[] = str.toCharArray();
        	if (Character.isDigit(c[0])){
        		
        		table.setRowMargin(0);
        		setFont(new Font("Serif", Font.PLAIN, 15));
        			
        		setForeground(Color.blue);
        		
        		//table.setRowHeight(row, 21); 
              
        	}
        }
        catch(Exception e){
        	//e.printStackTrace();
        }
	return this;
	}

}
