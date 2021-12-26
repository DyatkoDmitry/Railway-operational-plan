import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.imageio.*;
import javax.swing.*;

import java.awt.event.*;

public class Input_panel extends JPanel{
	
	BufferedImage img;
	Timer tm;
	static int i;
	
	public Input_panel() {
		// TODO Auto-generated constructor stub
		
		this.setLayout(null);
		
		JButton button = new JButton("  Далее  ");
		button.setBounds(450, 500, 100, 35);
		
		JButton button1 = new JButton();
		button1.setBounds(400, 600, 15, 15);
		
		JButton button2 = new JButton();
		button2.setBounds(490, 600, 15, 15);

		JButton button3 = new JButton();
		button3.setBounds(590, 600, 15, 15);
		
		i=1;
		try{
     		img = ImageIO.read(new File("images/pic" + i + ".png"));
     	}
     	catch (IOException ex) {
     		System.out.println("Image not found!");	
     	}
		
		
		ActionListener tmrAction = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				i++;
				if (i==4){
					i=1;
				}
			 	
		     	try{
		     		img = ImageIO.read(new File("images/pic" + i + ".png"));
		     	}
		     	catch (IOException ex) {
		     		System.out.println("Image not found!");	
		     	}
		    
			}
		};
		
		tm = new Timer(2000, tmrAction);
		tm.start();
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Visio.tabbedPane.setSelectedIndex(1);
			}
		});
		
		//this.paintComponent(getGraphics());
		
		button1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event){
				tm.stop();
				try{
		     		img = ImageIO.read(new File("images/pic1.png"));
		     	}
		     	catch (IOException ex) {
		     		System.out.println("Image not found!");	
		     	}
				
			}
			
			public void mouseExited(MouseEvent event){
				tm.start();
			}
		});
		
		
		button2.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event){
				tm.stop();
				try{
		     		img = ImageIO.read(new File("images/pic2.png"));
		     	}
		     	catch (IOException ex) {
		     		System.out.println("Image not found!");	
		     	}
				
			}
			
			public void mouseExited(MouseEvent event){
				tm.start();
			}
		});
		
		button3.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event){
				tm.stop();
				try{
		     		img = ImageIO.read(new File("images/pic3.png"));
		     	}
		     	catch (IOException ex) {
		     		System.out.println("Image not found!");	
		     	}
				
			}
			
			public void mouseExited(MouseEvent event){
				tm.start();
			}
		});
			
		this.add(button);
		this.add(button1);
		this.add(button2);
		this.add(button3);
   	}
	
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		
		g.drawImage(img,0,0,null);
		this.repaint();
	}
}