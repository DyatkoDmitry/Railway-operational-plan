import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabExpander;

import jdk.internal.dynalink.beans.StaticClass;

public class Visio {
	Shedule_panel panel5;
	Weekly_4 panel3;
	static JTabbedPane tabbedPane;
	Yearly_panel panel4;
	Yearly_panel2 panel6;
	
	Visio(){
		
		// Создание контейнера JFrame
		JFrame frame = new JFrame("Программа-помощник для составления оперативного плана работ по тех. обслуживанию");
		
		// Исходные размеры фрейма
		frame.setSize(1000,700);
		
		//Завершение программы при закрытии окна пользователем
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Размещение фрейма в центре экрана
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new BorderLayout());
		
		frame.setResizable(false);
		
		Font myFont = new Font ("Verdana", Font.PLAIN,11);
		
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(myFont);
						
		Input_panel panel1 = new Input_panel();
		
		Calendar_panel panel2 = new Calendar_panel();
		
		panel3 = new Weekly_4();
		
		panel5 = new Shedule_panel();
	
		panel4 = new Yearly_panel();
		
		panel6 = new Yearly_panel2();
		
				
		tabbedPane.addTab("Вход", panel1);
		tabbedPane.addTab("Календарь", panel2);
	
		
				
		tabbedPane.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
		
				if(!panel5.flag){
					panel5.componentsOff();
				}
				
				Weekly_4_model.editFlag=true;
				
				int selIndex = tabbedPane.getSelectedIndex();
				
				if(selIndex==2){
					Weekly_4_model wm5 = (Weekly_4_model)panel5.tableWeekly.getModel();
					Weekly_4_model.editFlag=true;
					panel3.tableWeekly.setModel(wm5);
					panel3.widthForColumns();	
				}
					
				if(selIndex==3){
					Weekly_4_model wm3 = (Weekly_4_model)panel3.tableWeekly.getModel();
					Weekly_4_model.editFlag=false;
					panel5.tableWeekly.setModel(wm3);
					panel5.widthForColumns();
				}
				
				Yearly_model.editFlag2=true;
				
				if(selIndex==4){
					Yearly_model ym6 = (Yearly_model)panel6.tableYearly.getModel();
					Yearly_model.editFlag2=true;
					panel4.tableYear.setModel(ym6);
					panel4.widthForColumns();	
				}
				
				if(selIndex==5){
					Yearly_model ym4 = (Yearly_model)panel4.tableYear.getModel();
					Yearly_model.editFlag2=false;
					panel6.tableYearly.setModel(ym4);
					panel6.widthForColumns();	
				}
				
			}
		});
		
		
		
		tabbedPane.addTab(" Редактор четырёхн-го графика", panel3);
		tabbedPane.addTab(" Четырёхн-ный график", panel5);
		tabbedPane.addTab(" Редактор годового графика", panel4);
		tabbedPane.addTab(" Годовой график", panel6);
		
		frame.getContentPane().add(tabbedPane);
		frame.setVisible(true);
	}
	
}
