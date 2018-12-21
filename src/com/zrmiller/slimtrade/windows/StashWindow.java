package com.zrmiller.slimtrade.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.panels.GridPanel;

public class StashWindow extends BasicMenuWindow{

	private static final long serialVersionUID = 1L;
	//STATICS
	private static Point windowPos = new Point(0, 0);
//	private static int windowWidth = 250;
//	private static int windowHeight = 250;
	private static Dimension windowSize;
	private static Point gridPos;
	private static int gridWidth;
	private static int gridHeight;
	private static Dimension gridSize;
	
	//SIZES
	
	
	private int infoPanelHeight = 32;
	private int buttonWidth = 80;
	private int buttonHeight = 20;
	private int buttonSpacingX = 20;
	private int buttonMarginTop = (infoPanelHeight-buttonHeight)/2;
	

	//RESIZING
	private int snapSize = 1;
	private int startingX;
	private int startingY;
	private int startingContainerWidth;
	private int startingContainerHeight;
	private int startingGridWidth;
	private int startingGridHeight;
	private BasicPanel bottomContainer;
	
	//GRID
	//todo remove static
	public static GridPanel grid;
	private int bufferThin = 5;
	private int bufferThick = 12;
	

//	private static int width = gridWidth+bufferThin+bufferThick;
//	private static int height = gridHeight+bufferThin+bufferThick+infoPanelHeight;
	
	//TODO : Add saving
	//TODO : right and bottom edges of grids don't shows
	//TODO : Snap to min/max sizes
	//TODO : Hide stash overlay then fix

	public StashWindow(){
		super("Stash Overlay", windowSize.width, windowSize.height);
//		this.setVisible(false);
		this.setLocation(windowPos);
		this.setMinimumSize(new Dimension(200,200));
		this.setSnapSize(snapSize);
		gridWidth = windowSize.width-bufferThin-bufferThick;
		gridHeight = windowSize.height-bufferThin-bufferThick-infoPanelHeight;
//		this.isOptimizedDrawingEnabled();
		
		container.setLayout(new BorderLayout());
		//TODO : Move clear background to BasicMenuWindow?
		container.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		container.setBounds(0, 0, windowSize.width, windowSize.height);
		
		grid = new GridPanel(gridWidth, gridHeight);
		grid.setBackground(new Color(1.0f,1.0f,1.0f,0.3f));
		grid.setLineColor(Color.GREEN);
//		grid.setSnapSize(snapSize);
		container.add(grid, BorderLayout.CENTER);
		
		BasicPanel topSpacer = new BasicPanel(gridWidth+bufferThick+bufferThin, bufferThin);
		container.add(topSpacer, BorderLayout.PAGE_START);
		
		BasicPanel leftSpacer = new BasicPanel(bufferThin, gridHeight);
		container.add(leftSpacer, BorderLayout.LINE_START);
		
		BasicPanel rightPullBar = new BasicPanel(bufferThick, gridHeight);
		rightPullBar.setBackground(Color.YELLOW);
		Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		rightPullBar.setBorder(b);
		container.add(rightPullBar, BorderLayout.LINE_END);
		
		//BOTTOM
		
		BasicPanel botLeftSpacer = new BasicPanel(bufferThin, bufferThick);
		BasicPanel bottomPullBar = new BasicPanel(gridWidth, bufferThick);
		BasicPanel botRightSpacer = new BasicPanel(bufferThick, bufferThick);
		BasicPanel infoPanel = new BasicPanel(windowSize.width, infoPanelHeight);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, buttonSpacingX, buttonMarginTop));
		JButton resetButton = new JButton("Reset");
		infoPanel.add(resetButton);
		resetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		infoPanel.add(saveButton);
		
		bottomContainer = new BasicPanel(windowSize.width, bufferThick+infoPanelHeight);
		
//		bottomPullBar.setBackground(Color.YELLOW);
		bottomPullBar.setBorder(b);
		
		bottomContainer.setLayout(new BorderLayout());
//		bottomContainer.add(botLeftSpacer, BorderLayout.LINE_START);
		bottomContainer.add(bottomPullBar, BorderLayout.PAGE_START);
//		bottomContainer.add(botRightSpacer, BorderLayout.LINE_END);
		bottomContainer.add(infoPanel, BorderLayout.PAGE_END);
		container.add(bottomContainer, BorderLayout.PAGE_END);
		
		
		//Width Adjust
		rightPullBar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	startingX = e.getXOnScreen();
		    	startingContainerWidth = container.getWidth();
		    	startingContainerHeight = container.getHeight();
		    	startingGridWidth = grid.getWidth();
		    	startingGridHeight = grid.getHeight();
		    }
		});
		
		rightPullBar.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	int dis = startingX-e.getXOnScreen();
		    	resizeStashWindow(startingContainerWidth-dis, startingContainerHeight);
		    }
		});
		
		//Height Adjust
		bottomPullBar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	startingY = e.getYOnScreen();
		    	startingContainerWidth = container.getWidth();
		    	startingContainerHeight = container.getHeight();
		    	startingGridWidth = grid.getWidth();
		    	startingGridHeight = grid.getHeight();
		    }
		});
		
		bottomPullBar.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	int dis = startingY-e.getYOnScreen();
//		    	adjustSize(0, dis);
		    	resizeStashWindow(startingContainerWidth, startingContainerHeight-dis);
		    }
		});
		
		//Save Button
		resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				reset();
			}
		});
		
		//Save Button
		saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				try {
					saveDataLocally();
					ObjectOutputStream stash = new ObjectOutputStream(new FileOutputStream("stash.pref"));
					stash.writeObject(StashWindow.windowPos);
					stash.writeObject(StashWindow.windowSize);
					stash.writeObject(StashWindow.gridPos);
					stash.writeObject(StashWindow.gridSize);
					stash.close();
					Overlay.stashHelperContainer.updateBounds();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		});
	}
	
	//Set Window Defaults
	public static void setDefaultWinPos(Point pos){
		StashWindow.windowPos = pos;
	}
	
	public static void setDefaultWinSize(Dimension size){
		StashWindow.windowSize = size;
	}
	
//	public static void setDefaultWindowSize(int width, int height){
//		StashWindow.windowSize = new Dimension(width, height);
//	}
	
	//Get Window Data
	public static Point getWinPos(){
		return StashWindow.windowPos;
	}
	
	public static Dimension getWinSize(){
		return StashWindow.windowSize;
	}
	
//	private Dimension getCurrentWindowSize(){
//		return container.getSize();
//	}
	
	//Set Grid Defaults
	public static void setDefaultGridPos(Point pos){
		StashWindow.gridPos = pos;
	}
	
	public static void setDefaultGridSize(Dimension size){
		StashWindow.gridSize = size;
	}
	
//	public void setDefaultGridSize(int width, int height){
//		StashWindow.gridSize = new Dimension(width, height);
//	}
	
	public static Point getGridPos(){
		return StashWindow.gridPos;
	}
	
	public static Dimension getGridSize(){
		return StashWindow.gridSize;
	}
	
	
	
	private void saveDataLocally(){
		setDefaultWinPos(this.getLocation());
		setDefaultWinSize(container.getSize());
		setDefaultGridPos(grid.getLocation());
		setDefaultGridSize(grid.getSize());
	}
	
	public void reset(){
		this.setLocation(windowPos);
		this.resizeStashWindow(windowSize.width, windowSize.height);
	}
	
	private Point getWindowLocation(){
		return this.getLocation();
	}
	
	public static void setwindowPosition(Point p){
		StashWindow.windowPos = p;
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	//TODO : SWITCH TO WINDOW SIZE
//	public static void setGridSize(int width, int height){
//		StashWindow.gridWidth = width;
//		StashWindow.gridHeight = height;
//	}
	
//	private void setWindowSize(int width, int height){
//		if(startingContainerWidth-width<this.getMinimumSize().getWidth() || startingContainerHeight-height<this.getMinimumSize().getHeight()){
//			return;
//		}
//		gridWidth = width-bufferThin-bufferThick;
//		gridHeight = height-bufferThin-bufferThick-infoPanelHeight;
//		grid.resizeGrid(gridWidth, gridHeight);
//	}
	
	//TODO : Add snapsize?
	public void adjustSize(int w, int h){
		if(startingContainerWidth-w<this.getMinimumSize().getWidth() || startingContainerHeight-h<this.getMinimumSize().getHeight()){
			return;
		}
		this.resizeWindow(startingContainerWidth-w, startingContainerHeight-h);
		grid.resizeGrid(startingGridWidth-w, startingGridHeight-h);
	}
	
	public void resizeStashWindow(int width, int height){
		if(width<this.getMinimumSize().getWidth() || height<this.getMinimumSize().getHeight()){
			return;
		}
		int gridWidth = width-bufferThin-bufferThick;
		int gridHeight = height-bufferThin-bufferThick-infoPanelHeight;
		this.resizeWindow(width, height);
		grid.resizeGrid(gridWidth, gridHeight);
	}
}
