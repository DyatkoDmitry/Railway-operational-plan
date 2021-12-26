import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;


public class Yearly_panel extends JPanel{
	JTable tableYear;
	Yearly_model modelYearly;
	int kol;
	File file;
	JComboBox jcb;
	
	public Yearly_panel() {
		// TODO Auto-generated constructor stub
		tableYear = new JTable();                // �������� �������
		
		if(getCountRowModel()<197){ 			// ��������� ���������� ����� � ������� ���� ��� ���������� ������
			kol=getCountRowModel();
		}
		else {
			kol=197;
		}
		 				  
		modelYearly = new Yearly_model(kol+2,22);  // ����� ������ ��� �������
		
		//�������� ������ � ������� �� �����	
		try{
			if(new File("WeeklyShedule.out").exists()) {	// �������� ������� �����	
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("YearlyShedule.out"));
					        
				modelYearly.dataYearly = (Object[][])ois.readObject();
							 
				ois.close();	
			}
			else{
				file = new File("YearlyShedule.out");
			}
		}	 
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		
		tableYear.setModel(modelYearly);	// ������� ������ ��� �������
		tableYear.repaint();
		
		
		
		tableYear.setCellEditor(new Weekly_4_editor()); //��������� ��������
		
		// ������� ��������� ��� �������
		tableYear.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(getJcb3()));
		tableYear.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(getJcb4()));
		tableYear.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(getJcb9()));
				
		tableYear.getTableHeader().setPreferredSize(new Dimension(100,50));  //������� �����
		this.widthForColumns();  // ��������� ������� ��� �������
		tableYear.getTableHeader().setDefaultRenderer(new Weekly_4_renderer_header()); // �������� ��� �����
		
		TableModelListener listenerWeekly = new TableModelListener() {
			
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				saveDataToFile();
				
				if(Visio.tabbedPane.getTabCount()==7){
					Visio.tabbedPane.removeTabAt(6);
				}
			}
		};
		
		tableYear.getModel().addTableModelListener(listenerWeekly);	//������� ��������� � �������
		
		tableYear.setDefaultRenderer(Object.class, new Yearly_renderer());  // �������� ��� �������
		
		tableYear.getColumnModel().getColumn(2).setCellRenderer(new Weekly_4_ren_2col());
		
		// �������� ������
		final JButton buttonAdd = new JButton("�������� ������");
		buttonAdd.setFont(new Font("Serif", Font.PLAIN, 11));
		buttonAdd.setBounds(200, 615, 130, 25);
		buttonAdd.setVisible(false);
		buttonAdd.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				insertMyRow();
			}
		});
				
		// ������� ������
		final JButton buttonDelete = new JButton("������� ������");
		buttonDelete.setFont(new Font("Serif", Font.PLAIN, 11));
		buttonDelete.setBounds(600, 615, 130, 25);
		buttonDelete.setVisible(false);
				
		buttonDelete.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
						    
				if (tableYear.getSelectedRow()!=-1){ 
					deleteMyRow();
				}
			}
		});
		
		
		
		JLabel jlbMain = new JLabel("������� ����-������ ������������ ������������ ��������� ���");
        jlbMain.setBounds(275, 0, 800, 20);
		jlbMain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jlbMain.setForeground(new Color(60,12,7));
		
		/*
		JLabel jlbNext = new JLabel("������� ����������� �������������� ��������� ������������ � ����� ����������� �������� ������");
        jlbNext.setBounds(200, 12, 600, 40);
		jlbNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jlbNext.setForeground(new Color(60,12,7));
		*/
		
		final JLabel jlbHelp = new JLabel("� ��� ���������� � ����� ������ ���������� ��������� ������� ���, ������������� ��������� � ������������ �����, �������������, �������������� ���������� �����");
        jlbHelp.setBounds(45, 615, 920, 25);
		jlbHelp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jlbHelp.setForeground(new Color(60,12,7));
		
		// ��������� ������ ��� ���������
		ListSelectionModel selModel = tableYear.getSelectionModel();
        
        selModel.addListSelectionListener(new ListSelectionListener() {               
             public void valueChanged(ListSelectionEvent e) {
             
            	 buttonDelete.setVisible(true);
            	 buttonAdd.setVisible(true);
            	 jlbHelp.setVisible(false);
             }
             
        });
		
		
		
		
		
		JScrollPane scrollPane = new JScrollPane(tableYear); // �������� ������� � ���������
		scrollPane.setBounds(0, 50, 990, 560);   //������������ ���������
		this.setLayout(null); //����� ����������
		this.add(scrollPane);	
		this.add(buttonAdd);
		this.add(buttonDelete);
		this.add(jlbMain);
		//this.add(jlbNext);
		this.add(jlbHelp);
	}
	
	public int getCountRowModel(){
		int lastValue=0;
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("YearlyShedule.out"));
			     
			Object[][] data = new Object[200][22];
				 
			data = (Object[][])ois.readObject();
					     
			for(int i=0; i< 200;i++){
					 
				if((data[i][0]!=null)&&(data[i][2]!=null)&&(data[i][3]!=null)&&(data[i][4]!=null)){
					lastValue=i;
				
				}	 	
			}
			ois.close();
		}	 
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
			return lastValue;
	}
	
	public void widthForColumns(){
		for (int i=0;i<modelYearly.getColumnCount();i++){
			TableColumn column = tableYear.getColumnModel().getColumn(i);
			switch(i){
				case 0:
					column.setMaxWidth(24);
					continue;
				case 1:
					column.setPreferredWidth(57);
					column.setMaxWidth(60);
					continue;
									
				case 2:
					column.setPreferredWidth(400);
					column.setMaxWidth(600);
					continue;
				
				case 4:
					column.setPreferredWidth(90);
					column.setMaxWidth(110);
					continue;
					
				case 5:
					column.setPreferredWidth(67);
					column.setMaxWidth(75);
					continue;
					
				case 6:
					column.setPreferredWidth(100);
					column.setMaxWidth(110);
					continue;
				
				case 7:
					column.setPreferredWidth(55);
					column.setMaxWidth(80);
					continue;
				case 8:
					column.setPreferredWidth(90);
					column.setMaxWidth(100);
					continue;
				
				case 9:
					column.setPreferredWidth(80);
					column.setMaxWidth(85);
					continue;
			}
		}
		
		// ����� ������ ������� ���������
        int a[] = {10,11,12,13,14,15,16,17,18,19,20,21};
        for (int i=0;i<a.length;i++){
        	tableYear.getColumnModel().getColumn(a[i]).setMinWidth(0);
        	tableYear.getColumnModel().getColumn(a[i]).setMaxWidth(0);
        	tableYear.getColumnModel().getColumn(a[i]).setPreferredWidth(0);
        	tableYear.getColumnModel().getColumn(a[i]).setResizable(false);
        }
        
          
		tableYear.setDefaultRenderer(Object.class, new Yearly_renderer());  // �������� ��� �������
		
		tableYear.getColumnModel().getColumn(2).setCellRenderer(new Weekly_4_ren_2col());
	
		// ������� ��������� ��� �������
		tableYear.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(getJcb3()));
		tableYear.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(getJcb4()));
		tableYear.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(getJcb9()));
	      
	}
	
	
	private JComboBox getJcb3() {
		String List3[] = {"��","���, ��"};
		jcb = new JComboBox(List3);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}
	
	private JComboBox getJcb4() {
		String List4[] = {"1 ��� � �����", "1 ��� � 3 ������", "1 ��� � 6 �������", "1 ��� � ���"};
		jcb = new JComboBox(List4);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}
	
	private JComboBox getJcb9() {
		String List9[] = {"��-2", "��-46", "��-2, ��-46",};
		jcb = new JComboBox(List9);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}
	
	public void saveDataToFile(){
		
		try{		  	  	  
			FileOutputStream fos = new FileOutputStream("YearlyShedule.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(modelYearly.dataYearly);

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
		widthForColumns();
	}
	
	public void insertMyRow(){
		modelYearly = (Yearly_model)tableYear.getModel();	// ������� ����������� ������
		
		if(tableYear.getModel().getRowCount()<=198){
			
			modelYearly.addRow(new Object[]{"0","1","2","3","4","5","6","7","8","9"});
		
			Object[][] data2 = new Object[200][22];
	
			for(int i=0; i< tableYear.getModel().getRowCount();i++){
				for(int j=0;j<22;j++){
				
					if(i<=tableYear.getSelectedRow()){
						data2[i][j]=modelYearly.getValueAt(i, j);
					}
				
					else 
						data2[i+1][j]=modelYearly.getValueAt(i, j);
				}
			}
			
			modelYearly.dataYearly=data2;
			tableYear.setModel(modelYearly);
			tableYear.repaint();
			
			saveDataToFile();
		}
	}
	
	public void deleteMyRow(){
		
		modelYearly = (Yearly_model)tableYear.getModel(); 	//������� ����������� ������
		
		Object[][] data2 = new Object[200][22];
	
		for(int i=0; i< tableYear.getModel().getRowCount();i++){
			for(int j=0;j<22;j++){
				
				if(i<tableYear.getSelectedRow()){
					data2[i][j]=modelYearly.getValueAt(i, j);
				}
				
				else {
					data2[i][j]=modelYearly.dataYearly[i+1][j];
				}
			}
		}
		
		modelYearly.dataYearly=data2;
		tableYear.setModel(modelYearly);
		tableYear.repaint();
		
		saveDataToFile();
		
	}
	
	
}
