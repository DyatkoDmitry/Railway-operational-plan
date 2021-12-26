import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import javax.swing.event.*;

public class Calendar_panel extends JPanel{
	
	BufferedImage imgHead;
	BufferedImage imgLeftBar;
	int yearNow;
	JLabel labYear;
	JTable tableCalendar;
	CalendarTableModel tableModel;
	File file;
	TableModelListener listenerAll;
	
	public Calendar_panel(){
		
		tableModel = new CalendarTableModel(true);       // ����������� ������ �������
		
		tableCalendar = new JTable();                //�������� ���� �������
				
		loadDataFromFile();
		
		tableCalendar.setTableHeader(null);          //����� �����
		
		tableCalendar.setRowHeight(15);              //������� ������ �����
		
		tableCalendar.setDefaultRenderer(Object.class, new CalendarTableRenderer());
		
		CalendarTableEditor tableEditor = new CalendarTableEditor();
		
		tableCalendar.setCellEditor(tableEditor);
		
		JScrollPane scrollPane = new JScrollPane(tableCalendar); // �������� ������� � ���������
		
		scrollPane.setBounds(172, 179, 760, 363);   //������������ ���������
		
		this.setLayout(null); //����� ����������
		
		//�������� ��������� ��� �������
		
		listenerAll = new TableModelListener() {
			
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				 writeDataToFile(); 
				 if(Visio.tabbedPane.getTabCount()==7){
						Visio.tabbedPane.removeTabAt(6);
				 }
			}
		};
		
		tableCalendar.getModel().addTableModelListener(listenerAll);
		
		
				
		//��� � ����������
		Calendar objectCalendar = Calendar.getInstance();
		yearNow = objectCalendar.get(Calendar.YEAR);
		
		labYear = new JLabel();
		//labYear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		labYear.setText(yearNow +" ���");
		labYear.setBounds(452, 20, 90, 40);
		Font fontYear = new Font("Tahoma", Font.PLAIN, 22);
		labYear.setFont(fontYear);
				
		
		JButton buttonClear = new JButton("�������� ���������");
		Font fontbuttonGo = new Font("Tahoma", Font.PLAIN, 13);
		buttonClear.setFont(fontbuttonGo);
		buttonClear.setBounds(380, 580, 260, 25);
		
		JButton buttonLoad = new JButton("��������� ��������� �� 2016 ���");
		Font fontbuttonLoad = new Font("Tahoma", Font.PLAIN, 13);
		buttonLoad.setFont(fontbuttonLoad);
		buttonLoad.setBounds(380, 550, 260, 25);
		
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int response = JOptionPane.showConfirmDialog(null, "�� ������������� ������ �������� ��������� ?","��������!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/help.png"));
				if(JOptionPane.YES_OPTION==response){
				
					tableModel = new CalendarTableModel(false);

					tableCalendar.setModel(tableModel);
					tableCalendar.getModel().addTableModelListener(listenerAll);
					writeDataToFile();
					
					if(Visio.tabbedPane.getTabCount()==7){
						Visio.tabbedPane.removeTabAt(6);
					}
				}
			}		
		});
	
		buttonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int response = JOptionPane.showConfirmDialog(null, "�� ������������� ������ ��������� ��������� �� 2016 �.?","��������!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/help.png"));
				if(JOptionPane.YES_OPTION==response){
				
					tableModel = new CalendarTableModel(true);

					tableCalendar.setModel(tableModel);
					tableCalendar.getModel().addTableModelListener(listenerAll);
					writeDataToFile();
					
					if(Visio.tabbedPane.getTabCount()==7){
						Visio.tabbedPane.removeTabAt(6);
					}
				}
			}
		});
		
		//������� ���������� �� ������
		this.add(scrollPane);
		this.add(labYear);
		this.add(buttonClear);		
		this.add(buttonLoad);	
		
		// ������� ����� � ����� ������
		try{
     		imgHead = ImageIO.read(new File("images/head.png"));
     	}
     	catch (IOException ex) {
     		System.out.println("Image not found!");	
     	}
		
		//������� ����� ������ ���������
		try{
     		imgLeftBar = ImageIO.read(new File("images/left_bar.png"));
     	}
     	catch (IOException ex) {
     		System.out.println("Image not found!");	
     	}

		
	}
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		
		g.drawImage(imgHead,172,80,null);
		//this.repaint();
		
		g.drawImage(imgLeftBar,70,80,null);
		//this.repaint();
	}
	
	private void loadDataFromFile(){
		try{
			 if(new File("Calendar.out").exists()) {	// �������� ������� �����	
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Calendar.out"));
			        
				 tableModel.data = (Object[][])ois.readObject();
	         
				 tableCalendar.setModel(tableModel);
				 tableCalendar.repaint();
				 ois.close();
			 }
			 else{
				 file = new File("Calendar.out");
				 tableCalendar.setModel(tableModel);
			 }
		}	 
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
	    }
	}
	
	private void writeDataToFile(){
		try{
			 FileOutputStream fos = new FileOutputStream("Calendar.out");
			 ObjectOutputStream oos = new ObjectOutputStream(fos);
			 
			 oos.writeObject(tableModel.data);

			 oos.flush();
			 oos.close();
			 fos.close();
			 
	    }
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}  
	}
}
