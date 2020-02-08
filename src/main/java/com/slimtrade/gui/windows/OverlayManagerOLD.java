//package com.slimtrade.gui.windows;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.border.Border;
//
//import com.slimtrade.App;
//import com.slimtrade.core.observing.AdvancedMouseAdapter;
//import com.slimtrade.core.observing.improved.IColorable;
//import com.slimtrade.enums.MenubarButtonLocation;
//import com.slimtrade.gui.FrameManager;
//import com.slimtrade.gui.enums.WindowState;
//import com.slimtrade.gui.messaging.AbstractMessagePanel;
//import com.slimtrade.gui.basic.BasicDialog;
//import com.slimtrade.gui.basic.BasicMovableDialog;
//import com.slimtrade.gui.enums.ExpandDirection;
//import com.slimtrade.gui.menubar.MenubarButton;
//import com.slimtrade.gui.options.ISaveable;
//import com.slimtrade.gui.overlay.OverlayInfoDialog;
//import com.slimtrade.gui.panels.BufferPanel;
//
//public class OverlayManagerOLD implements ISaveable, IColorable {
//
//	GridBagLayout gridbag = new GridBagLayout();
//
//	private BasicDialog helpDialog = new BasicDialog();
//	private BasicMovableDialog menubarDialog = new BasicMovableDialog(true);
//	private BasicMovableDialog messageDialog = new BasicMovableDialog(true);
//	private JPanel menubarExpandButton = new JPanel();
//	private JPanel menubarPanelTop = new JPanel();
//	private JPanel menubarPanelBottom = new JPanel();
//	private JButton cancelButton = new JButton("Cancel");
//	private JButton saveButton = new JButton("Save");
//
//	private final int BORDER_SIZE = 2;
//	private final int MENUBAR_BUTTON_SIZE = MenubarButton.HEIGHT - BORDER_SIZE * 2;
//
//	Border borderDefault = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, Color.BLACK);
//	Border borderScreenLock = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, Color.RED);
//
//	Border borderNW = BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, BORDER_SIZE, Color.BLACK);
//	Border borderNE = BorderFactory.createMatteBorder(0, BORDER_SIZE, BORDER_SIZE, 0, Color.BLACK);
//	Border borderSW = BorderFactory.createMatteBorder(BORDER_SIZE, 0, 0, BORDER_SIZE, Color.BLACK);
//	Border borderSE = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, 0, Color.BLACK);
//
//	Border borderNWLock = BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, BORDER_SIZE, Color.RED);
//	Border borderNELock = BorderFactory.createMatteBorder(0, BORDER_SIZE, BORDER_SIZE, 0, Color.RED);
//	Border borderSWLock = BorderFactory.createMatteBorder(BORDER_SIZE, 0, 0, BORDER_SIZE, Color.RED);
//	Border borderSELock = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, 0, Color.RED);
//
//	Color tempBGColor = new Color(100, 100, 100, 200);
//
//	JComboBox<MenubarButtonLocation> menubarCombo = new JComboBox<MenubarButtonLocation>();
//	JComboBox<ExpandDirection> msgPanelCombo = new JComboBox<ExpandDirection>();
//
//	GridBagConstraints gcLeft = new GridBagConstraints();
//	GridBagConstraints gcRight = new GridBagConstraints();
//
//	private String oldMenubarCombo;
//	private String oldMsgPanelCombo;
//
//	public OverlayManagerOLD() {
//
//		menubarExpandButton.setBackground(Color.LIGHT_GRAY);
//		menubarExpandButton.setBorder(borderNW);
//
//		helpDialog.setSize(500, 150);
//		menubarDialog.setSize(App.frameManager.menubar.getWidth(), App.frameManager.menubar.getHeight());
//		messageDialog.setSize(AbstractMessagePanel.totalWidth, AbstractMessagePanel.totalHeight);
//		menubarPanelTop.setPreferredSize(new Dimension(0, MENUBAR_BUTTON_SIZE));
//		menubarPanelBottom.setPreferredSize(new Dimension(0, MENUBAR_BUTTON_SIZE));
//		menubarExpandButton.setPreferredSize(new Dimension(MENUBAR_BUTTON_SIZE, MENUBAR_BUTTON_SIZE));
//
//		menubarDialog.setLocation(App.saveManager.saveFile.menubarX, App.saveManager.saveFile.menubarY);
//		messageDialog.setLocation(App.saveManager.saveFile.messageManagerX, App.saveManager.saveFile.messageManagerY);
//
//		helpDialog.setLayout(gridbag);
//		menubarDialog.setLayout(new BorderLayout());
//		messageDialog.setLayout(gridbag);
//		menubarPanelTop.setLayout(gridbag);
//		menubarPanelBottom.setLayout(gridbag);
//
//		helpDialog.getContentPane().setBackground(Color.LIGHT_GRAY);
//		menubarDialog.setBackground(tempBGColor);
//		messageDialog.setBackground(tempBGColor);
//		menubarPanelTop.setOpaque(false);
//		menubarPanelBottom.setOpaque(false);
//
//		menubarDialog.getRootPane().setBorder(borderDefault);
//		messageDialog.getRootPane().setBorder(borderDefault);
//
//		menubarDialog.setBorderOffset(BORDER_SIZE);
//		messageDialog.setBorderOffset(BORDER_SIZE);
//
//		GridBagConstraints gcCenter = new GridBagConstraints();
//
//		//// HERE
//		gcLeft.weightx = 1;
//		gcRight.weightx = 1;
//		gcLeft.anchor = GridBagConstraints.WEST;
//		gcRight.anchor = GridBagConstraints.EAST;
//
//		JLabel messageLabel = new JLabel("Message Panel");
//		messageDialog.add(messageLabel, gcCenter);
//
//		JLabel menubarLabel = new JLabel("Menubar");
//		menubarLabel.setHorizontalAlignment(JLabel.CENTER);
//		menubarDialog.add(menubarLabel, BorderLayout.CENTER);
//		menubarDialog.add(menubarPanelTop, BorderLayout.PAGE_START);
//		menubarDialog.add(menubarPanelBottom, BorderLayout.PAGE_END);
//		menubarPanelTop.add(menubarExpandButton, gcLeft);
//
//		// Helper Panel
//		GridBagConstraints gcHelp = new GridBagConstraints();
//		gcHelp.gridx = 0;
//		gcHelp.gridy = 0;
//		JLabel help1 = new JLabel("Layout Manager");
//		JLabel help2 = new JLabel("LEFT CLICK : move elements around the screen.");
//		JLabel help3 = new JLabel("RIGHT CLICK : toggle monitor lock, preventing items from going off screen.");
//
//		// Options
//		GridBagConstraints gcOptions = new GridBagConstraints();
//		JPanel optionsPanel = new JPanel();
//		optionsPanel.setOpaque(false);
//		optionsPanel.setLayout(gridbag);
//		gcOptions.gridx = 0;
//		gcOptions.gridy = 0;
//
//		JLabel menubarButtonLabel = new JLabel("Menubar Expand Button");
//		JLabel msgPanelExpandLabel = new JLabel("Message Panel Grow Direction");
//		int bufferSize = 10;
//
//		for(MenubarButtonLocation b : MenubarButtonLocation.values()){
//			menubarCombo.addItem(b);
//		}
//
////		menubarCombo.addItem("Top Left");
////		menubarCombo.addItem("Top Right");
////		menubarCombo.addItem("Bottom Left");
////		menubarCombo.addItem("Bottom Right");
//		menubarCombo.setSelectedItem(App.saveManager.saveFile.menubarButtonLocation);
//		updateMenubarButton();
//
//		for(ExpandDirection d : ExpandDirection.values()){
//			msgPanelCombo.addItem(d);
//		}
////		msgPanelCombo.addItem("Upwards");
////		msgPanelCombo.addItem("Downwards");
//		Dimension panelSize = menubarCombo.getPreferredSize();
//		msgPanelCombo.setPreferredSize(panelSize);
//
//		// CANCEL + SAVE BUTTONS
//		JPanel closePanel = new JPanel();
//		closePanel.setOpaque(false);
//		closePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		closePanel.add(cancelButton);
//		closePanel.add(new BufferPanel(20, 0));
//		closePanel.add(saveButton);
//
//		optionsPanel.add(menubarButtonLabel, gcOptions);
//		gcOptions.gridx = 1;
//		optionsPanel.add(new BufferPanel(bufferSize, 0), gcOptions);
//		gcOptions.gridx = 2;
//		optionsPanel.add(menubarCombo, gcOptions);
//		gcOptions.gridx = 0;
//		gcOptions.gridy = 1;
//		optionsPanel.add(msgPanelExpandLabel, gcOptions);
//		gcOptions.gridx = 1;
//		optionsPanel.add(new BufferPanel(bufferSize, 0), gcOptions);
//		gcOptions.gridx = 2;
//		optionsPanel.add(msgPanelCombo, gcOptions);
//
//		// BUILD HELP DIALOG
//		helpDialog.add(help1, gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(new BufferPanel(0, 15), gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(help2, gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(help3, gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(new BufferPanel(0, 15), gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(optionsPanel, gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(new BufferPanel(0, 15), gcHelp);
//		gcHelp.gridy++;
//		helpDialog.add(closePanel, gcHelp);
//
//		// TODO : temppp
//        helpDialog = new OverlayInfoDialog();
//
//		// TODO : Set buffer
//		Dimension pref = helpDialog.getPreferredSize();
//		helpDialog.setSize(pref.width + 20, pref.height + 20);
//		FrameManager.centerFrame(helpDialog);
//
//		load();
//
//		menubarDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e) {
//				if (e.getButton() == MouseEvent.BUTTON3) {
//					menubarDialog.toggleScreenLock();
//					if (menubarDialog.getScreenLock()) {
//						menubarDialog.getRootPane().setBorder(borderScreenLock);
//					} else {
//						menubarDialog.getRootPane().setBorder(borderDefault);
//					}
//				}
//				updateMenubarButton();
//			}
//		});
//
//		messageDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e) {
//				if (e.getButton() == MouseEvent.BUTTON3) {
//					messageDialog.toggleScreenLock();
//					if (messageDialog.getScreenLock()) {
//						messageDialog.getRootPane().setBorder(borderScreenLock);
//					} else {
//						messageDialog.getRootPane().setBorder(borderDefault);
//					}
//				}
//			}
//		});
//
//		menubarCombo.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				updateMenubarButton();
//			}
//		});
//
//		cancelButton.addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e) {
//                FrameManager.windowState = WindowState.NORMAL;
//				menubarDialog.setLocation(App.saveManager.saveFile.menubarX, App.saveManager.saveFile.menubarY);
//				messageDialog.setLocation(App.saveManager.saveFile.messageManagerX, App.saveManager.saveFile.messageManagerY);
//                load();
//				updateMenubarButton();
//				hideDialog();
//				FrameManager.showVisibleFrames();
//				FrameManager.showOptionsWindow();
//			}
//		});
//
//		// TODO : Cleanup
//		saveButton.addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e) {
//                FrameManager.windowState = WindowState.NORMAL;
//				// Save
//                // TODO : Move to save
//				App.saveManager.saveFile.menubarX = menubarDialog.getX();
//                App.saveManager.saveFile.menubarY = menubarDialog.getY();
//                App.saveManager.saveFile.messageManagerX = messageDialog.getX();
//                App.saveManager.saveFile.messageManagerY = messageDialog.getY();
//				ExpandDirection dir = (ExpandDirection)msgPanelCombo.getSelectedItem();
//				MenubarButtonLocation loc = (MenubarButtonLocation)menubarCombo.getSelectedItem();
//
//				App.saveManager.saveFile.messageExpandDirection = dir;
//                App.saveManager.saveFile.menubarButtonLocation = loc;
//				App.saveManager.saveToDisk();
//
//				// Update UI
//
//				FrameManager.menubar.setLocation(menubarDialog.getLocation());
////				FrameManager.messageManager.setLocation(messageDialog.getLocation());
//				FrameManager.menubarToggle.updateLocation();
//				FrameManager.menubar.reorder();
//
////				FrameManager.messageManager.updateLocation();
//				FrameManager.messageManager.setAnchorPoint(messageDialog.getLocation());
//				FrameManager.messageManager.setExpandDirection((ExpandDirection)msgPanelCombo.getSelectedItem());
//				FrameManager.messageManager.refreshPanelLocations();
//
//				hideDialog();
//                FrameManager.showVisibleFrames();
//                FrameManager.showOptionsWindow();
//			}
//		});
//	}
//
//	private void updateMenubarButton() {
////		String sel = (String) menubarCombo.getSelectedItem();
//		MenubarButtonLocation loc = (MenubarButtonLocation)menubarCombo.getSelectedItem() == null ? MenubarButtonLocation.NW : (MenubarButtonLocation)menubarCombo.getSelectedItem();
//
//		//TODO : Switch to enum, add proper default setting
////		if(sel == null){
////			sel = "Bottom Left";
////		}
//		switch (loc) {
//		case NW:
//			if (menubarDialog.getScreenLock()) {
//				menubarExpandButton.setBorder(borderNWLock);
//			} else {
//				menubarExpandButton.setBorder(borderNW);
//			}
//			menubarPanelTop.add(menubarExpandButton, gcLeft);
//			break;
//		case NE:
//			if (menubarDialog.getScreenLock()) {
//				menubarExpandButton.setBorder(borderNELock);
//			} else {
//				menubarExpandButton.setBorder(borderNE);
//			}
//			menubarPanelTop.add(menubarExpandButton, gcRight);
//			break;
//		case SW:
//			if (menubarDialog.getScreenLock()) {
//				menubarExpandButton.setBorder(borderSWLock);
//			} else {
//				menubarExpandButton.setBorder(borderSW);
//			}
//			menubarPanelBottom.add(menubarExpandButton, gcLeft);
//			break;
//		case SE:
//			if (menubarDialog.getScreenLock()) {
//				menubarExpandButton.setBorder(borderSELock);
//			} else {
//				menubarExpandButton.setBorder(borderSE);
//			}
//			menubarPanelBottom.add(menubarExpandButton, gcRight);
//			break;
//		}
//		menubarDialog.repaint();
//	}
//
//	public void forceToFront() {
//	    helpDialog.setAlwaysOnTop(false);
//        menubarDialog.setAlwaysOnTop(false);
//        messageDialog.setAlwaysOnTop(false);
//        helpDialog.setAlwaysOnTop(true);
//        menubarDialog.setAlwaysOnTop(true);
//        messageDialog.setAlwaysOnTop(true);
//    }
//
//	public void showDialog() {
//		helpDialog.setVisible(true);
//		menubarDialog.setVisible(true);
//		messageDialog.setVisible(true);
//	}
//
//	public void hideDialog() {
//		helpDialog.setVisible(false);
//		menubarDialog.setVisible(false);
//		messageDialog.setVisible(false);
//	}
//
//	public void save() {
//		// TODO Auto-generated method stub
//	}
//
//	public void load() {
//		MenubarButtonLocation loc = App.saveManager.saveFile.menubarButtonLocation;
//		ExpandDirection dir = App.saveManager.saveFile.messageExpandDirection;
//
//		menubarCombo.setSelectedItem(loc);
//		msgPanelCombo.setSelectedItem(dir);
//
//		updateMenubarButton();
//	}
//
//    @Override
//    public void updateColor() {
//
//    }
//}