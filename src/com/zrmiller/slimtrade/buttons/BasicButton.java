package com.zrmiller.slimtrade.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.Overlay;

public class BasicButton extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 0.9;
	public static int width;
	public static int height;
	public static int borderThickness = 1;
	public static Color bgColor;
	public static Color bgColor_hover;
	public static Color borderColor;
	public static Color borderColor_hover;
	private Border border = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	private Border border_hover = BorderFactory.createLineBorder(Color.BLACK);

	public BasicButton(){
		buildButton();
	}
	
	public BasicButton(String imgPath){
		buildButton();
		this.setCustomIcon(imgPath);
	}
	
	private void buildButton(){
		this.setLayout(Overlay.flowCenter);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(border);
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				setActiveBorder(border_hover);
			}
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				setActiveBorder(border);
			}
		});
	}
	
	private void setActiveBorder(Border border){
		this.setBorder(border);
	}
	
	public void setBorderPresets(Border border, Border border_hover){
		this.border = border;
		this.border_hover = border_hover;
		this.setActiveBorder(border);
	}
	
	public void setCustomIcon(String imgPath){
		JLabel closeIcon = new JLabel();
		double imgWidth = width*imgScaling;
		double imgHeight = height*imgScaling;
		closeIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource(imgPath)).getImage().getScaledInstance((int)(imgWidth), (int)(imgHeight), Image.SCALE_SMOOTH)));
		closeIcon.setBounds(0, 0, (int)imgWidth, (int)imgHeight);
		closeIcon.setPreferredSize(new Dimension((int)imgWidth, (int)imgHeight));
		this.add(closeIcon);
	}
	
}