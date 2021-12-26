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
import javax.swing.table.*;

public class Weekly_4 extends JPanel{
	JTable tableWeekly;
	Weekly_4_model modelWeekly;
	JComboBox jcb;
	File file;
	int kol;
//	int lastValue;
	public Weekly_4() {
		// TODO Auto-generated constructor stub
		
		tableWeekly = new JTable();                // Создадим таблицу
	
		if(getCountRowModel()<197){ 			// Определим количество строк и добавим одну для корректной работы
			kol=getCountRowModel();
		}
		else {
			kol=197;
		}
		 				  
		modelWeekly = new Weekly_4_model(kol+2,38);  // Новая модель для таблицы
		
		//Загрузим модель в таблицу из файла	
		try{
			 if(new File("WeeklyShedule.out").exists()) {	// Проверка наличия файла	
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream("WeeklyShedule.out"));
			        
				 modelWeekly.dataWeekly = (Object[][])ois.readObject();
					 
				 ois.close();	
			 }
			 else{
				 file = new File("WeeklyShedule.out");
			 }
		}	 
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
	    }
		
		tableWeekly.setModel(modelWeekly);	// Зададим модель для таблицы
		tableWeekly.repaint();
		
		
		tableWeekly.setCellEditor(new Weekly_4_editor()); //Установим редактор
		
		// Добавим редакторы для колонок
		tableWeekly.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(getJcb3()));
		tableWeekly.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(getJcb4()));
		tableWeekly.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(getJcb9()));
		
		tableWeekly.getTableHeader().setPreferredSize(new Dimension(100,50));  //Размеры шапки
		this.widthForColumns();  // Установим размеры для колонок
		tableWeekly.getTableHeader().setDefaultRenderer(new Weekly_4_renderer_header()); // Рендерер для шапки
		

		TableModelListener listenerWeekly = new TableModelListener() {
			
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				saveDataToFile();
				
				if(Visio.tabbedPane.getTabCount()==7){
					Visio.tabbedPane.removeTabAt(6);
				}
				
			}
		};
		
		tableWeekly.getModel().addTableModelListener(listenerWeekly);	//Добавим слушателя к таблице
		
		tableWeekly.setDefaultRenderer(Object.class, new Weekly_4_renderer());  // Рендерер для таблицы
		
		tableWeekly.getColumnModel().getColumn(2).setCellRenderer(new Weekly_4_ren_2col());
		
		// Добавить строку
		final JButton buttonAdd = new JButton("Добавить строку");
		buttonAdd.setFont(new Font("Serif", Font.PLAIN, 11));
		buttonAdd.setBounds(200, 615, 130, 25);
		buttonAdd.setVisible(false);
		buttonAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				insertMyRow();
			}
		});
		
		// Удалить строку
		final JButton buttonDelete = new JButton("Удалить строку");
		buttonDelete.setFont(new Font("Serif", Font.PLAIN, 11));
		buttonDelete.setBounds(600, 615, 130, 25);
		buttonDelete.setVisible(false);
		
		buttonDelete.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				    
				  if (tableWeekly.getSelectedRow()!=-1){ 
					  deleteMyRow();
				  }
			}
		});
		

		JLabel jlbMain = new JLabel("Четырёхнедельный план-график технического обслуживания устройств микропроцессорной техники ГАЦ АРС-ГТСС");
        jlbMain.setBounds(120, 0, 800, 20);
		jlbMain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jlbMain.setForeground(new Color(60,12,7));
		
		JLabel jlbNext = new JLabel("станции Калинковичи Калинковичской дистанции сигнализации и связи Белорусской железной дороги");
        jlbNext.setBounds(210, 12, 600, 40);
		jlbNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jlbNext.setForeground(new Color(60,12,7));
		
		final JLabel jlbHelp = new JLabel("• Для сохранения и учёта строки достаточно заполнить колонки «№», «Наименование устройств и производимых работ», «Исполнитель», «Периодичность выполнения работ»");
        jlbHelp.setBounds(45, 615, 920, 25);
		jlbHelp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jlbHelp.setForeground(new Color(60,12,7));
		
		// Отобразим кнопки при выделении
		ListSelectionModel selModel = tableWeekly.getSelectionModel();
        
        selModel.addListSelectionListener(new ListSelectionListener() {               
             public void valueChanged(ListSelectionEvent e) {
             
            	 buttonDelete.setVisible(true);
            	 buttonAdd.setVisible(true);
            	 jlbHelp.setVisible(false);
             }
             
        });
		
		JScrollPane scrollPane = new JScrollPane(tableWeekly); // Поместим таблицу в контейнер
		scrollPane.setBounds(0, 50, 990, 560);   //Расположение календаря
		this.setLayout(null); //Уберём компоновку
		this.add(buttonAdd);
		this.add(buttonDelete);
		this.add(jlbMain);
		this.add(jlbNext);
		this.add(jlbHelp);
		this.add(scrollPane);
	}
	
	public void widthForColumns(){
		for (int i=0;i<modelWeekly.getColumnCount();i++){
			TableColumn column = tableWeekly.getColumnModel().getColumn(i);
			switch(i){
				case 0:
					column.setMaxWidth(29);
					continue;
				case 1:
					column.setPreferredWidth(60);
					column.setMaxWidth(62);
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
		
		// Чтобы убрать колонки календаря
        int a[] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37};
        for (int i=0;i<a.length;i++){
        	tableWeekly.getColumnModel().getColumn(a[i]).setMinWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setMaxWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setPreferredWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setResizable(false);
        }
        
          
		tableWeekly.setDefaultRenderer(Object.class, new Weekly_4_renderer());  // Рендерер для таблицы
		
		tableWeekly.getColumnModel().getColumn(2).setCellRenderer(new Weekly_4_ren_2col());
	
		// Добавим редакторы для колонок
		tableWeekly.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(getJcb3()));
		tableWeekly.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(getJcb4()));
		tableWeekly.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(getJcb9()));
	      
	}
	
	private JComboBox getJcb3() {
		String List3[] = {"ШН","ШН, ШЦМ", "ШНС, ШН"};
		jcb = new JComboBox(List3);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}
	
	private JComboBox getJcb4() {
		String List4[] = {"ежедневно", "1 раз в неделю","1 раз в 2 недели", "1 раз в 4 недели"};
		jcb = new JComboBox(List4);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}
	
	private JComboBox getJcb9() {
		String List9[] = {"ШУ-2", "ДУ-46", "ШУ-64", "ДУ-46, ШУ-2",};
		jcb = new JComboBox(List9);
		jcb.setEditable(true);
		jcb.setFont(new Font("Serif", Font.PLAIN, 13));
		return jcb;
	}

	public void saveDataToFile(){
		
		try{		  	  	  
			FileOutputStream fos = new FileOutputStream("WeeklyShedule.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(modelWeekly.dataWeekly);

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

	public void deleteMyRow(){
		
		modelWeekly = (Weekly_4_model)tableWeekly.getModel(); 	//Возьмем обновленную модель
		
		Object[][] data2 = new Object[200][38];
	
		for(int i=0; i< tableWeekly.getModel().getRowCount();i++){
			for(int j=0;j<38;j++){
				
				if(i<tableWeekly.getSelectedRow()){
					data2[i][j]=modelWeekly.getValueAt(i, j);
				}
				
				else {
					data2[i][j]=modelWeekly.dataWeekly[i+1][j];
				}
			}
		}
		
		modelWeekly.dataWeekly=data2;
		tableWeekly.setModel(modelWeekly);
		tableWeekly.repaint();
		
		saveDataToFile();
		
	}
	
	
	public void insertMyRow(){
		modelWeekly = (Weekly_4_model)tableWeekly.getModel();	// Возьмем обновленную модель
		
		if(tableWeekly.getModel().getRowCount()<=198){
			
			modelWeekly.addRow(new Object[]{"0","1","2","3","4","5","6","7","8","9"});
		
			Object[][] data2 = new Object[200][38];
	
			for(int i=0; i< tableWeekly.getModel().getRowCount();i++){
				for(int j=0;j<38;j++){
				
					if(i<=tableWeekly.getSelectedRow()){
						data2[i][j]=modelWeekly.getValueAt(i, j);
					}
				
					else 
						data2[i+1][j]=modelWeekly.getValueAt(i, j);
				}
			}
			
			modelWeekly.dataWeekly=data2;
			tableWeekly.setModel(modelWeekly);
			tableWeekly.repaint();
			
			saveDataToFile();
		}
	}
	
	// Посчитаем количество строк в модели для отображеия
	public int getCountRowModel(){
		int lastValue=0;
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("WeeklyShedule.out"));
			     
			Object[][] data = new Object[200][38];
				 
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
	
}
	

