package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.Overlay;

public class BasicIcon extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 1;
	public static int width;
	public static int height;
	public Color bgColor;

	public BasicIcon(String imgPath, int width, int height){
		BasicIcon.width = width;
		BasicIcon.height = height;
		buildIcon();
		this.setCustomIcon(imgPath);
	}
	
	public BasicIcon(String imgPath){
		buildIcon();
		this.setCustomIcon(imgPath);
	}
	
	private void buildIcon(){
		this.setLayout(Overlay.flowCenter);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(this.bgColor);
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