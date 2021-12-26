import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class Yearly_panel2 extends JPanel{
	JTable tableYearly;
	Yearly_panel objectYearly1;
	Yearly_model modelYearly;
	File file;
	JScrollPane scrollPaneYearly;
	
	public Yearly_panel2() {
		// TODO Auto-generated constructor stub
		
		tableYearly = new JTable();
		
		loadDataFromFile();					// Загрузим данные и создадим модель
		
		tableYearly.setCellEditor(new Weekly_4_editor()); //Установим редактор
		
		tableYearly.setTableHeader(null);					// Шапка таблицы
		ImageIcon icon3 = new ImageIcon("images/headerYearly.png");
		JLabel lab = new JLabel();
		lab.setIcon(icon3);
		lab.setBounds(50, 41, 900, 80);
		
		tableYearly.setDefaultRenderer(Object.class, new Yearly_renderer2());  // Рендерер для таблицы
		
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
				
		this.tableYearly.getModel().addTableModelListener(listenerWeekly);
		
		JLabel jlbMain = new JLabel("Годовой план-график технического обслуживания устройств СЦБ");
        jlbMain.setBounds(290, 7, 800, 20);
		jlbMain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jlbMain.setForeground(new Color(60,12,7));
		
		createLowerBar();	// Создадим тулбар
		
		scrollPaneYearly = new JScrollPane(tableYearly); // Поместим таблицу в контейнер
		scrollPaneYearly.setBounds(50, 120, 900, 490);   //Расположение календаря
		
		this.setLayout(null);
		this.add(scrollPaneYearly);
		this.add(lab);
		this.add(jlbMain);
	}
	
	public void loadDataFromFile() {
		
		objectYearly1 = new Yearly_panel();
			
		int kol=0;
		
		if(objectYearly1.getCountRowModel()<197){ 			// Определим количество строк и добавим одну для корректной работы
			kol=objectYearly1.getCountRowModel();
		}
		else {
			kol=197;
		}
			 				  
		modelYearly = new Yearly_model(kol+2,22);  // Новая модель для таблицы
		
		try{
			 if(new File("WeeklyShedule.out").exists()) {	// Проверка наличия файла	
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream("YearlyShedule.out"));
				        
				 modelYearly.dataYearly = (Object[][])ois.readObject();
				 tableYearly.repaint();
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
					
		tableYearly.setModel(modelYearly);    
		tableYearly.repaint();
		tableYearly.setDefaultRenderer(Object.class, new Yearly_renderer2());  // Рендерер для таблицы
	}
	
	// Ширина колонок	
	public void widthForColumns(){
		
		tableYearly.getColumnModel().getColumn(0).setMinWidth(24);
		tableYearly.getColumnModel().getColumn(0).setMaxWidth(24);
		tableYearly.getColumnModel().getColumn(0).setPreferredWidth(24);
		   		    	
		tableYearly.getColumnModel().getColumn(2).setMinWidth(400);
		tableYearly.getColumnModel().getColumn(2).setMaxWidth(450);
		tableYearly.getColumnModel().getColumn(2).setPreferredWidth(400);
		    	
		tableYearly.getColumnModel().getColumn(4).setMinWidth(150);
		tableYearly.getColumnModel().getColumn(4).setMaxWidth(150);
		tableYearly.getColumnModel().getColumn(4).setPreferredWidth(150);
		
		
		// Чтобы убрать колонки календаря
        int a[] = {1,3,5,6,7,8,9};
        for (int i=0;i<a.length;i++){
        	tableYearly.getColumnModel().getColumn(a[i]).setMinWidth(0);
        	tableYearly.getColumnModel().getColumn(a[i]).setMaxWidth(0);
        	tableYearly.getColumnModel().getColumn(a[i]).setPreferredWidth(0);
        	tableYearly.getColumnModel().getColumn(a[i]).setResizable(false);
        }
        
        
        tableYearly.setDefaultRenderer(Object.class, new Yearly_renderer2());  // Рендерер для таблицы
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
        jlbsel1.setBounds(16, 620, 920, 25);
		jlbsel1.setFont(fontMy);
		jlbsel1.setForeground(colorFor);
		
		
		// Метка измеритель
		final JLabel jlb2 = new JLabel("Измеритель:");
		jlb2.setBounds(120, 605, 920, 25);
		jlb2.setFont(fontMy);
		jlb2.setForeground(colorFor);
		
		// !Метка для измерителя
		final JLabel jlbsel2 = new JLabel();
		jlbsel2.setBounds(120, 620, 170, 25);
		jlbsel2.setFont(fontMy);
		jlbsel2.setForeground(colorFor);
				
		// Метка норма времени на измеритель
		final JLabel jlb3 = new JLabel("Норма времени на измеритель(мин.):");
		jlb3.setBounds(280, 605, 300, 25);
		jlb3.setFont(fontMy);
		jlb3.setForeground(colorFor);
		
		// !Метка для нормы времени на измеритель
		final JLabel jlbsel3 = new JLabel();
		jlbsel3.setBounds(280, 620, 210, 25);
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
		ListSelectionModel selModel = tableYearly.getSelectionModel();
		        
		selModel.addListSelectionListener(new ListSelectionListener() {               
			public void valueChanged(ListSelectionEvent e) {
				
				Object a1 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 3);
				if((a1!=null)&&(!a1.toString().isEmpty()))
					jlbsel1.setText(a1.toString());
				else
					jlbsel1.setText("");
				
				Object a2 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 5);
				if((a2!=null)&&(!a2.toString().isEmpty()))
					jlbsel2.setText(a2.toString());
				else
					jlbsel2.setText("");
				
				Object a3 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 6);
				if((a3!=null)&&(!a3.toString().isEmpty()))
					jlbsel3.setText(a3.toString());
				else
					jlbsel3.setText("");
				
				Object a4 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 7);
				if((a4!=null)&&(!a4.toString().isEmpty()))
					jlbsel4.setText(a4.toString());
				else
					jlbsel4.setText("");
				
				Object a5 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 8);
				if((a5!=null)&&(!a5.toString().isEmpty()))
					jlbsel5.setText(a5.toString());
				else
					jlbsel5.setText("");
				
				Object a6 = tableYearly.getModel().getValueAt(tableYearly.getSelectedRow(), 9);
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
