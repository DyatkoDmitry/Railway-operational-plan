import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class Yearly_renderer2 extends JTextArea implements TableCellRenderer{
	private final DefaultTableCellRenderer sourceRenderer = new DefaultTableCellRenderer();
	
	public Yearly_renderer2() {
		// TODO Auto-generated constructor stub
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
        setFont(new Font("Serif", Font.PLAIN, 14));
        //setFont(sourceRenderer.getFont());
        setText(sourceRenderer.getText());
      //  setText(value == null ? "" : value.toString());

        Rectangle rect = table.getCellRect(row, column, true);
        this.setSize(rect.getSize());//для установки ширины компоненты
               
        int height_wanted = (int) getPreferredSize().getHeight();
        setText(value == null ? "" : value.toString());
        if ((height_wanted > table.getRowHeight(row) | row != last_row) &  //если новая строчка и полученная высота больше чем установленна
                 height_wanted > table.getRowHeight()) //и высота больше чем дефолтная
            table.setRowHeight(row, height_wanted+5); 
        	//table.setRowMargin(10);
        	
        last_row = row;
     
        /////////////////////////
        if(column==2){
        try{
        	String str = value.toString();
        	char c[] = str.toCharArray();
        	if (Character.isDigit(c[0])){
        		
        	//	table.setRowMargin(0);
        		setFont(new Font("Serif", Font.PLAIN, 10));
        		
        		setForeground(Color.blue);
        		
        		table.setRowHeight(row, 24); 
        	
        	}
        }
        catch(Exception e){
        	//e.printStackTrace();
        }
        
        }
        
        /*
        int a[]={10,11,12,13,14,15,16,17,18,19,20,21};
        for(int i=0;i<a.length;i++){
        	if(column==a[i]){
        		setFont(new Font("Serif", Font.PLAIN, 15));	
        	}
        }
        */
       
        /*
        //Если номер пункта длинный, то уменьшим шрифт
        if(column==0){
        	try{
        		if(value!=null){	
        			String str = value.toString();
        
        			if(str.length()>3){
        				setFont(new Font("Serif", Font.PLAIN, 12));	
        			}
        			
        			else
        				setFont(new Font("Serif", Font.PLAIN, 14));	
        		}
        	}
        	catch(Exception exc){
        		exc.printStackTrace();
        	}
		}
        */
       
       
        return this;
      
	}
}