import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.rtf.RTFEditorKit;

public class ZonePlanPanel extends JPanel{
	
	JTable tablePlan;
	ZonePlanModel modelPlan;
	Yearly_panel yearlyPanel;
	int monthKol;
	static JLabel label[];
	int countL;
	JTextField jtxStation;
	JTextField jtxShop;
	
	public ZonePlanPanel(ZonePlanModel zpm, int monthCount) {
		// TODO Auto-generated constructor stub
		monthKol = monthCount;
		tablePlan = new JTable();
		tablePlan.setCellEditor(new Weekly_4_editor()); //Установим редактор
		tablePlan.setModel(zpm);
		tablePlan.setDefaultRenderer(Object.class, new ZonePlanRenderer());
		
		widthForColumns();
		this.setLayout(null);
		
		createLeftBar();	// Создадим левую панель
		
		JButton button = new JButton("Составить оперативный план");
		button.setBounds(14, 99, 211, 35);
		button.setFont(new Font("Tahoma",Font.PLAIN,13));
		button.setBackground(new Color(170,200,190));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				createReport();
				JOptionPane.showMessageDialog(null, "Отчет лежит в корневой папке", "Внимание!", JOptionPane.WARNING_MESSAGE, new ImageIcon("images/attIcon.png"));
			}
		});
		
		createYearlyPanel();
		
		//Слушатель для годовой таблицы
		TableModelListener listener = new TableModelListener() {
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				
				colorForLabels();
				
			}
		};
		tablePlan.getModel().addTableModelListener(listener);
		
		
		String[] sMonth = {"","январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"};
		
		String s = "на " + sMonth[monthCount] + " месяц";
		
		Font fon = new Font("Verdana", Font.PLAIN, 15);
		Color con = new Color(60,12,7);
		
		JLabel	jlbMain = new JLabel("Оперативный план-график");
		jlbMain.setBounds(15, 12, 300, 30);
		jlbMain.setFont(fon);
		jlbMain.setForeground(con);
		
		JLabel	jlbMain2 = new JLabel(s);
		jlbMain2.setBounds(55,33, 200, 30);
		jlbMain2.setFont(fon);
		jlbMain2.setForeground(con);
		
		
		JScrollPane jsm = new JScrollPane(tablePlan);
		jsm.setBounds(240, 2, 486, 650);
		this.add(jsm);
		this.add(button);
		this.add(jlbMain);
		this.add(jlbMain2);
	}
	
	private void widthForColumns(){
		
		TableColumn column0 = tablePlan.getColumnModel().getColumn(0);
		TableColumn column1 = tablePlan.getColumnModel().getColumn(1);
		TableColumn column2 = tablePlan.getColumnModel().getColumn(2);
			
		column0.setMaxWidth(25);
		column1.setPreferredWidth(29);
		
		column1.setPreferredWidth(340);
		column1.setMaxWidth(350);
				
		column2.setPreferredWidth(103);
		column2.setMaxWidth(110);	
	}
	
	//Создадим оперативный график
	private void createReport(){
		
		String station;			// Название станции 
		String shop;			// Номер цеха
		
		// Зададим значения полям название станции и номер цеха
		if(jtxStation.getText().trim().length()==0){
			station="____________________________________";
		}
		else{
			station=jtxStation.getText();
		}
				
		if(jtxShop.getText().trim().length()==0){
			shop="____";
		}
		else{
			shop=jtxShop.getText();
		}
		
		String[] sMonth = {"","январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"};
		
		 try{
		        BufferedReader br = new BufferedReader(new InputStreamReader(new
		                FileInputStream("_report1.dat"), "Cp1251"));
		        
		        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new
		                FileOutputStream("Оперативный план на " + sMonth[monthKol] + " месяц замедлители.html.rtf"), "Cp1251"));
		        
		        String strRead = null;
		       
			        
		        while( (strRead = br.readLine()) != null) {		// Построчно считываем шаблон
		        	
		        	if(strRead.contains("H4 align")){
		        		bw.write("<H4 align='center' style='font-size:16px;'>Оперативный план");
		        		bw.newLine();
		        		bw.write("<p align='center' style='font-size:12px; font-weight:lighter'>работ на " + sMonth[monthKol] + " месяц " + Calendar.getInstance().get(Calendar.YEAR) + " г. станции " + station + " Цеха №" + shop);
		        		bw.newLine();
		        		bw.write("<p align='center' style='font-size:11px; font-weight:lighter'>Калинковичская дистанция сигнализации и связи Белорусcкой Ж.Д.</p>");
		        		bw.newLine();	
		        		continue;
		        	}
		        	
		        	
		        	if(strRead.contains("table border=")){		// Найдем начало таблицы
		        		bw.write(strRead);
		        		bw.newLine();
		        		bw.write("<tr><td class='topHeader' rowspan='2' class='verticalText'>Число месяца</td><td class='topHeader' colspan='2'>Выполняемые работы</td><td class='topHeader' rowspan='2'>Невыполненные работы по техническому обслуживанию</td><td class='topHeader' rowspan='2'>Вынужденные изменения в плане</td><td class='topHeader' rowspan='2'>Исполнитель</td><td class='topHeader' rowspan='2'>Подпись</td></tr>");
		        		bw.newLine();
		        		bw.write("<tr><td class='topHeader'>По четырёхнедельному графику</td><td class='topHeader' class='verticalText' width='10px'>По годовому графику</td></tr>");
		        		bw.newLine();
		        		for(int i=0;i<tablePlan.getModel().getRowCount();i++){	// Добавим нужное число строк для графика
		        			
		        			bw.write("<tr><td width='20' align='center'>" + tablePlan.getModel().getValueAt(i, 0).toString() + "</td><td width='360'>" + tablePlan.getModel().getValueAt(i, 1).toString() + "</td><td  width='70' align='center'>" + tablePlan.getModel().getValueAt(i, 2).toString() + "</td><td>   </td><td>   </td><td>   </td><td>   </td></tr>");
		        			bw.newLine();
		        			
		        		}
		        		bw.write("</table>");
		        		bw.newLine();
		        		bw.write("<p align='right' class='textDown'>«_____» ____________________ 20____ г.  Составил ШНС-08_____________________</p>");
		        		bw.newLine();
		        		continue;
		        	}
		        	   	
		        	bw.write(strRead);
		            bw.newLine();
		            
		            
		        }
		        
		        bw.close();
		        br.close();
		        
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
		
	private void createYearlyPanel(){
		
		//Создадим объект класса годовая панель
		yearlyPanel = new Yearly_panel();
		
		// Фонт для годовой панели
		Font myFont = new Font("Tahoma", Font.PLAIN, 11);
		
		// Главная метка
		JLabel labelYearlyMain = new JLabel("Годовые пункты графика:");
		labelYearlyMain.setBounds(790, 1, 160, 20);
		labelYearlyMain.setFont(myFont);
		this.add(labelYearlyMain);
		
		countL=0;
		String str = "";
		
		for(int i=0;i<=yearlyPanel.kol;i++){
			//Выбирем только цифры
			Object a = yearlyPanel.modelYearly.dataYearly[i][9+monthKol];  //Элемент таблицы годовой график
			
			if((a==null)||(a.toString().isEmpty())){	//Проверка на пустоту
				continue;
			}
			str+=a.toString() + ":";
			
			countL++;
		}
		
		String[] strYearly = str.split(":");	// Массив пунктов
		
		ZonePlanPanel.label = new JLabel[countL];
		for(int i=0;i<countL;i++){
			ZonePlanPanel.label[i] = new JLabel();
			ZonePlanPanel.label[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
			ZonePlanPanel.label[i].setText(strYearly[i]);
			ZonePlanPanel.label[i].setForeground(new Color(53,73,93));
			ZonePlanPanel.label[i].setToolTipText(getToolTextYearly(strYearly[i]));
			
			if(i<=30){
				ZonePlanPanel.label[i].setBounds(780, 16*(i+1), 160, 20);
			}
			if((i>30)&&(i<60)){
				ZonePlanPanel.label[i].setBounds(800, 16*((i-30)+1), 160, 20);
			}
			if((i>60)&&(i<90)){
				ZonePlanPanel.label[i].setBounds(820, 16*((i-60)+1), 160, 20);
			}
			if((i>90)&&(i<120)){
				ZonePlanPanel.label[i].setBounds(840, 16*((i-90)+1), 160, 20);
			}
			if((i>120)&&(i<150)){
				ZonePlanPanel.label[i].setBounds(860, 16*((i-120)+1), 160, 20);
			}
			if((i>150)&&(i<180)){
				ZonePlanPanel.label[i].setBounds(880, 16*((i-150)+1), 160, 20);
			}
			
			this.add(label[i]);
		}
	}
	
	
	private void colorForLabels(){			//Установим цвет для меток годовой панели
		
		// Покрасим все метки в изначальный цвет, в случае удаления пункта из таблицы
		for(int k=0;k<countL;k++){
			ZonePlanPanel.label[k].setForeground(new Color(53,73,93));
		}
		
		// Переберем все непустые ячейки 3-ей колонки
		for(int i=0;i<tablePlan.getModel().getRowCount();i++){
			Object a = tablePlan.getModel().getValueAt(i, 2);
			
			if((a==null)||(a.toString().isEmpty())){	//Проверка на пустоту
				continue;
			}
			
			// Загоним значение ячейки в массив
			String[] str = a.toString().split(",");
			
			// Перебор и сравнение всех меток для каждого элемента массива str
			for(int x=0;x<str.length;x++){
				for(int j=0;j<countL;j++){
					if(ZonePlanPanel.label[j].getText().equals(str[x].trim())){
						ZonePlanPanel.label[j].setForeground(new Color(204,204,190));
					}
				}
			}
		}
		
	}
	
	private String getToolTextYearly(String s){		// Подсказка для каждой метки
	
		for(int i=0;i<=yearlyPanel.kol;i++){
			Object a = yearlyPanel.tableYear.getModel().getValueAt(i, 9+monthKol);
			
			if((a==null)||(a.toString().isEmpty())){	//Проверка на пустоту
				continue;
			}
			
			if(s.trim().equals(a.toString().trim())){
				return(yearlyPanel.tableYear.getModel().getValueAt(i, 2).toString());
			}
		}
		return  "";
	}
	
	public void createLeftBar(){
		//Создадим левую панель
		JLabel	jlbStation = new JLabel("Станция:");
		jlbStation.setBounds(14, 245, 211, 30);
		jlbStation.setFont(new Font("Tahoma",Font.PLAIN,13));
				
		JLabel	jlbShop = new JLabel("Цех №:");
		jlbShop.setBounds(14, 320, 211, 30);
		jlbShop.setFont(new Font("Tahoma",Font.PLAIN,13));
				
		jtxStation = new JTextField(10);
		jtxStation.setText("Замедлители, компресссора и воздухопроводная сеть");
		jtxStation.setBounds(14, 270, 211, 23);
				
		jtxShop = new JTextField(10);
		jtxShop.setText("08");
		jtxShop.setBounds(14, 345, 211, 23);
		
		this.add(jlbStation);
		this.add(jlbShop);
		this.add(jtxShop);
		this.add(jtxStation);	
	}
}
