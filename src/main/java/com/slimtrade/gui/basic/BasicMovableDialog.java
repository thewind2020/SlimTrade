package main.java.com.slimtrade.gui.basic;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.java.com.slimtrade.core.utility.TradeUtility;

public class BasicMovableDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	protected int offsetX;
	protected int offsetY;
	protected boolean screenLock = false;
	protected boolean mouseDown = false;
//	private JPanel moverPanel;
	
	public BasicMovableDialog(){
		createListeners((JPanel) this.getContentPane());
	}
	
	public BasicMovableDialog(boolean createListeners){
		if(createListeners){
			createListeners((JPanel) this.getContentPane());
		}
	}
	
	public void createListeners(JPanel p){
		p.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		    	if(e.getButton() == MouseEvent.BUTTON1){
		    		offsetX = e.getX();
		    		offsetY = e.getY();
		    		mouseDown = true;
		    		runWindowMover();
		    	}
		    }
		});
		
		p.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					mouseDown = false;
				}
			}
		});
	}
	
	public void setScreenLock(boolean state){
		screenLock = state;
	}
	
	private void moveWindow(Point p){
		this.setLocation(p);
	}
	
	private int getDialogWidth(){
		return this.getWidth();
	}
	
	private int getDialogHeight(){
		return this.getHeight();
	}
	
	private void runWindowMover(){
		new Thread(){
			public void run(){
				while(mouseDown){
					int targetX = MouseInfo.getPointerInfo().getLocation().x-offsetX;
					int targetY = MouseInfo.getPointerInfo().getLocation().y-offsetY;
					if(screenLock){
						if(targetX<0) targetX = 0;
						if(targetX>TradeUtility.screenSize.width-getDialogWidth()) targetX = TradeUtility.screenSize.width-getDialogWidth();
						if(targetY<0) targetY = 0;
						if(targetY>TradeUtility.screenSize.height-getDialogHeight()) targetY = TradeUtility.screenSize.height-getDialogHeight();
					}
					moveWindow(new Point(targetX, targetY));
				}
			}
		}.start();
	}
	

}
