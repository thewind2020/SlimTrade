package com.slimtrade.gui.options.basics;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

public class AdvancedPanel extends ContainerPanel implements ISaveable, IColorable {

	private static final long serialVersionUID = 1L;

	private JButton editStashButton = new BasicButton("Edit Stash Location");
	private AdvancedRow clientRow = new AdvancedRow("Client Path");

	public AdvancedPanel() {

		this.container.add(editStashButton, gc);
		gc.gridy++;
		gc.insets.top = 10;
		this.container.add(clientRow, gc);

		clientRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = clientRow.getFileChooser().showOpenDialog(clientRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File clientFile = clientRow.getFileChooser().getSelectedFile();
					String path = clientFile.getPath();
					clientRow.setText(path);
				}
			}
		});

		editStashButton.addActionListener(e -> {
			FrameManager.windowState = WindowState.STASH_OVERLAY;
			FrameManager.hideAllFrames();
			FrameManager.stashOverlayWindow.setShow(true);
			FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
			FrameManager.stashOverlayWindow.setAlwaysOnTop(true);
			FrameManager.stashOverlayWindow.repaint();
		});

		this.load();
	}

	public void save() {
		if (clientRow.isChanged()) {
			new Thread(new Runnable() {
				public void run() {
					clientRow.setChanged(false);
//					App.saveManager.saveFile.clientPath =
//                    String path = clientRow.getText();
//					App.saveManager.putObject(clientRow.getText(), "general", "clientPath");
					App.saveManager.saveFile.clientPath = clientRow.getText();

//					App.saveManager.refreshPath();
					App.chatParser.setClientPath(clientRow.getText());

					// Restart file monitor
					App.fileMonitor.stopMonitor();
					App.chatParser.init();
					App.fileMonitor.startMonitor();

				}
			}).start();

		}
	}

	public void load() {
	    if(App.saveManager.saveFile.clientPath != null) {
            File file = new File(App.saveManager.saveFile.clientPath);
            if(file.exists() && file.isFile()) {
                clientRow.setText(App.saveManager.saveFile.clientPath);
            }
        }
	}

	@Override
	public void updateColor() {
		super.updateColor();
		this.setBackground(ColorManager.BACKGROUND);
	}
}