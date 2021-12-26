import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class Shedule_panel extends JPanel{
	JTable tableMonth;
	public JComboBox jcmb;
	JLabel jlbl;
	boolean flag=true;
	SheduleTableModel tableMonthModel;
	JTable tableWeekly;
	Weekly_4_model tableModel;
	Weekly_4 objectWeekly;
	File file;
	JScrollPane scrollPaneWeekly;
	JLabel jlbMonth;
	JButton buttonMain;
	
	public Shedule_panel() {
		
		// TODO Auto-generated constructor stub
		tableMonth = new JTable();    // Таблица из календаря исходного месяца
		
		tableMonthModel = new SheduleTableModel(); //Модель таблицы месяца
		
		tableMonth.setModel(tableMonthModel);
		
		tableMonth.setDefaultRenderer(Object.class, new SheduleTableRenderer()); // Соответственно рендерер для месяца
		
		// JComboBox для выбора месяца
		String[] months = {"Месяц","Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
		jcmb = new JComboBox(months);
		jcmb.setSelectedIndex(0);
		jcmb.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jcmb.setBounds(293, 12, 85, 25);
	
		jlbl = new JLabel("Выберите месяц для составления графика:");
		jlbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jlbl.setBounds(30, 7, 300, 30);
		
		jlbMonth = new JLabel();		
		jlbMonth.setBounds(645, 22, 140, 20);
		jlbMonth.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jlbMonth.setForeground(new Color(60,12,7));
		jlbMonth.setVisible(false);
				
		jcmb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					
				if(flag){
					jcmb.removeItemAt(0);
					flag=false;
				}

				jlbl.setVisible(false);
					
				tableMonthModel = new SheduleTableModel(jcmb.getSelectedIndex());
					
				tableMonth.setModel(tableMonthModel);
						
				jlbMonth.setText("за " + jcmb.getSelectedItem().toString().toLowerCase() + " месяц");
				jlbMonth.setVisible(true);
				buttonMain.setVisible(true);
				buttonMain.setText("Сформировать план на " + jcmb.getSelectedItem().toString().toLowerCase() + " месяц");
			}
		});
		
		
		
		tableMonth.setTableHeader(null);  // Уберём шапку таблицы
		tableMonth.setRowHeight(17);      // Зададим высоту ячеек

		this.setLayout(null);             //Уберём компоновку
		
		
		JScrollPane scrollPane = new JScrollPane(tableMonth); // Поместим таблицу в контейнер
		scrollPane.setBounds(462, 66, 513, 37);   //Расположение календаря
		
		buttonMain = new JButton();
		buttonMain.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonMain.setBounds(15, 12, 265, 25);
		buttonMain.setBackground(new Color(170,200,190));
		buttonMain.setForeground(new Color(60,10,25));
		buttonMain.setVisible(false);
		
		buttonMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub 
				
				// Проверка - напоминание изменения календаря на будущий год
				if (jcmb.getModel().getSelectedItem().toString().equals("Январь")){
					int response = JOptionPane.showConfirmDialog(null, "Возможно, вы оформляете оперативный план на будущий год! Не забудьте ввести новый календарь","Внимание!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/help.png"));
					if(JOptionPane.NO_OPTION==response){
						if(Visio.tabbedPane.getTabCount()==7){
							Visio.tabbedPane.removeTabAt(6);
							Visio.tabbedPane.setSelectedIndex(1);
						}
						return;
					}
				}
				
				
				int k=0;
				for(int i=0;i<tableMonthModel.dataShedule.length;i++){
					for(int j=0;j<28;j++){
						if((tableMonthModel.dataShedule[i][j])!=null){
							k++;
						}
					}
				}
				
				ZonePlanModel zoneModel = new ZonePlanModel(tableMonthModel.dataShedule,k);
				
				//Номер месяца
				int monthCount = jcmb.getSelectedIndex()+1;
				
				if(Visio.tabbedPane.getTabCount()==7){
					Visio.tabbedPane.removeTabAt(6);
					Visio.tabbedPane.add("Оперативный график", new ZonePlanPanel(zoneModel, monthCount));
					Visio.tabbedPane.setSelectedIndex(6);
				}
				else{
					Visio.tabbedPane.add("Оперативный график", new ZonePlanPanel(zoneModel,monthCount));
					Visio.tabbedPane.setSelectedIndex(6);
				}
				
			}	
		});
		
		
		
		/*------------------------------------------------------*/
		
		tableWeekly = new JTable();         // Таблица редактора 4-ех недельного графика
		
		loadDataFromFile();					// Загрузим данные и создадим модель
		
		tableWeekly.setCellEditor(new Weekly_4_editor()); //Установим редактор
		
	 	tableWeekly.setTableHeader(null);					// Шапка таблицы
		ImageIcon icon3 = new ImageIcon("images/headerWeekly.png");
		JLabel lab = new JLabel();
		lab.setIcon(icon3);
		lab.setBounds(0, 50, 990, 51);
	 	
     	tableWeekly.setDefaultRenderer(Object.class, new Shedule_renderer());  // Рендерер для таблицы
		
		this.widthForColumns();  // Установим размеры для колонок
			
		// Слушатель на сохранения файла
		TableModelListener listenerWeekly = new TableModelListener() {
			
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				saveDataToFile();
				
				if(Visio.tabbedPane.getTabCount()==7){
					Visio.tabbedPane.removeTabAt(6);
				}
				
			}
		};
		
		this.tableWeekly.getModel().addTableModelListener(listenerWeekly);
		

		JLabel jlbMain = new JLabel("Четырёхнедельный план технического обслуживания замедлителей, компрессоров и воздухопроводной сети");
        jlbMain.setBounds(395, 2, 570, 20);
		jlbMain.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jlbMain.setForeground(new Color(60,12,7));
		
		createLowerBar();		//Нижняя строка помошник
	
		scrollPaneWeekly = new JScrollPane(tableWeekly); // Поместим таблицу в контейнер
		scrollPaneWeekly.setBounds(0, 100, 990, 510);   //Расположение календаря
			
		this.add(scrollPane);
		this.add(scrollPaneWeekly);
		this.add(lab);
		this.add(jcmb);
		this.add(jlbl);
		this.add(jlbMain);
		this.add(jlbMonth);
		this.add(buttonMain);
	}
	
	public void componentsOff() {
		jcmb.insertItemAt("Месяц", 0);
		jcmb.setSelectedIndex(0);
		jlbl.setVisible(true);
		flag=true;
		tableMonthModel = new SheduleTableModel();
		tableMonth.setModel(tableMonthModel);
		jlbMonth.setVisible(false);
		buttonMain.setVisible(false);
	}
	
	// Ширина колонок	
	public void widthForColumns(){
		
		tableWeekly.getColumnModel().getColumn(0).setMinWidth(24);
		tableWeekly.getColumnModel().getColumn(0).setMaxWidth(24);
		tableWeekly.getColumnModel().getColumn(0).setPreferredWidth(24);
		   		    	
		tableWeekly.getColumnModel().getColumn(2).setMinWidth(350);
		tableWeekly.getColumnModel().getColumn(2).setMaxWidth(450);
		tableWeekly.getColumnModel().getColumn(2).setPreferredWidth(400);
		    	
		tableWeekly.getColumnModel().getColumn(4).setMinWidth(85);
		tableWeekly.getColumnModel().getColumn(4).setMaxWidth(85);
		tableWeekly.getColumnModel().getColumn(4).setPreferredWidth(85);
		   
		// Чтобы убрать колонки календаря
        int a[] = {1,3,5,6,7,8,9};
        for (int i=0;i<a.length;i++){
        	tableWeekly.getColumnModel().getColumn(a[i]).setMinWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setMaxWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setPreferredWidth(0);
        	tableWeekly.getColumnModel().getColumn(a[i]).setResizable(false);
        }
        
        tableWeekly.setDefaultRenderer(Object.class, new Shedule_renderer());  // Рендерер для таблицы
 	}
	
	public void saveDataToFile(){	
		try{		  	  
			  
				 FileOutputStream fos = new FileOutputStream("WeeklyShedule.out");
				 ObjectOutputStream oos = new ObjectOutputStream(fos);
				 oos.writeObject(tableModel.dataWeekly);

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
	

	public void loadDataFromFile() {
		
	objectWeekly = new Weekly_4();
		
		int kol=0;
		
		if(objectWeekly.getCountRowModel()<197){ 			// Определим количество строк и добавим одну для корректной работы
			kol=objectWeekly.getCountRowModel();
		}
		else {
			kol=197;
		}
		 				  
		tableModel = new Weekly_4_model(kol+2,38);  // Новая модель для таблицы
	
		try{
			 if(new File("WeeklyShedule.out").exists()) {	// Проверка наличия файла	
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream("WeeklyShedule.out"));
			        
				 tableModel.dataWeekly = (Object[][])ois.readObject();
				 tableWeekly.repaint();
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
				
		tableWeekly.setModel(tableModel);    
		tableWeekly.repaint();
		tableWeekly.setDefaultRenderer(Object.class, new Shedule_renderer());  // Рендерер для таблицы
	}
	
	public void createLowerBar(){
		//Создадим нижнюю панель
		Font fontMy = new Font("Tahoma", Font.PLAIN, 10);
		Color colorFor = new Color(150,150,150);
		
		// Метка исполнитель
		final JLabel jlb1 = new JLabel("Исполнитель:");
        jlb1.setBounds(16, 605, 920, 25);
		jlb1.setFont(fontMy);
		jlb1.setForeground(colorFor);
		
		// !Метка для исполнителя
		final JLabel jlbsel1 = new JLabel();
        jlbsel1.setBounds(16, 620, 120, 25);
		jlbsel1.setFont(fontMy);
		jlbsel1.setForeground(colorFor);
		
		
		// Метка измеритель
		final JLabel jlb2 = new JLabel("Измеритель:");
		jlb2.setBounds(140, 605, 920, 25);
		jlb2.setFont(fontMy);
		jlb2.setForeground(colorFor);
		
		// !Метка для измерителя
		final JLabel jlbsel2 = new JLabel();
		jlbsel2.setBounds(140, 620, 920, 25);
		jlbsel2.setFont(fontMy);
		jlbsel2.setForeground(colorFor);
				
		// Метка норма времени на измеритель
		final JLabel jlb3 = new JLabel("Норма времени на измеритель(мин.):");
		jlb3.setBounds(280, 605, 920, 25);
		jlb3.setFont(fontMy);
		jlb3.setForeground(colorFor);
		
		// !Метка для нормы времени на измеритель
		final JLabel jlbsel3 = new JLabel();
		jlbsel3.setBounds(280, 620, 920, 25);
		jlbsel3.setFont(fontMy);
		jlbsel3.setForeground(colorFor);
		
		// Метка число объектов
		final JLabel jlb4 = new JLabel("Число объектов:");
		jlb4.setBounds(490, 605, 920, 25);
		jlb4.setFont(fontMy);
		jlb4.setForeground(colorFor);
		
		// !Метка для числа объектов
		final JLabel jlbsel4 = new JLabel();
		jlbsel4.setBounds(490, 620, 920, 25);
		jlbsel4.setFont(fontMy);
		jlbsel4.setForeground(colorFor);	
				
		// Метка общие затраты на проверку
		final JLabel jlb5 = new JLabel("Общие затраты на проверки(чел. мин.):");
		jlb5.setBounds(610, 605, 920, 25);
		jlb5.setFont(fontMy);
		jlb5.setForeground(colorFor);
		
		// !Метка для общих затрат на проверку
		final JLabel jlbsel5 = new JLabel();
		jlbsel5.setBounds(610, 620, 920, 25);
		jlbsel5.setFont(fontMy);
		jlbsel5.setForeground(colorFor);
		
		// Метка оформление результатов
		final JLabel jlb6 = new JLabel("Оформление результатов:");
		jlb6.setBounds(840, 605, 920, 25);
		jlb6.setFont(fontMy);
		jlb6.setForeground(colorFor);
		
		// Метка оформление результатов
		final JLabel jlbsel6 = new JLabel();
		jlbsel6.setBounds(840, 620, 920, 25);
		jlbsel6.setFont(fontMy);
		jlbsel6.setForeground(colorFor);
				
		// Отобразим кнопки при выделении
		ListSelectionModel selModel = tableWeekly.getSelectionModel();
		        
		selModel.addListSelectionListener(new ListSelectionListener() {               
			public void valueChanged(ListSelectionEvent e) {
				
				Object a1 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 3);
				if((a1!=null)&&(!a1.toString().isEmpty()))
					jlbsel1.setText(a1.toString());
				else
					jlbsel1.setText("");
				
				Object a2 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 5);
				if((a2!=null)&&(!a2.toString().isEmpty()))
					jlbsel2.setText(a2.toString());
				else
					jlbsel2.setText("");
				
				Object a3 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 6);
				if((a3!=null)&&(!a3.toString().isEmpty()))
					jlbsel3.setText(a3.toString());
				else
					jlbsel3.setText("");
				
				Object a4 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 7);
				if((a4!=null)&&(!a4.toString().isEmpty()))
					jlbsel4.setText(a4.toString());
				else
					jlbsel4.setText("");
				
				Object a5 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 8);
				if((a5!=null)&&(!a5.toString().isEmpty()))
					jlbsel5.setText(a5.toString());
				else
					jlbsel5.setText("");
				
				Object a6 = tableWeekly.getModel().getValueAt(tableWeekly.getSelectedRow(), 9);
				if((a6!=null)&&(!a6.toString().isEmpty()))
					jlbsel6.setText(a6.toString());
				else
					jlbsel6.setText("");
				
			}            
		});
		
		
		this.add(jlb1);
		this.add(jlbsel1);
		this.add(jlb2);
		this.add(jlbsel2);
		this.add(jlb3);
		this.add(jlbsel3);
		this.add(jlb4);
		this.add(jlbsel4);
		this.add(jlb5);
		this.add(jlbsel5);
		this.add(jlb6);
		this.add(jlbsel6);
	}
}
