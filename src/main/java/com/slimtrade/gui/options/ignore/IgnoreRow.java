package com.slimtrade.gui.options.ignore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.RemovablePanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.panels.BufferPanel;

public class IgnoreRow extends RemovablePanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final int ITEM_MAX_WIDTH = 300;

    private final IgnoreData ignoreData;

    private final Timer timer;
    private JLabel timerLabel;

    //TODO : Fix timer deleting stuff after 1m
    public IgnoreRow(IgnoreData ignoreData, AddRemovePanel parent) {
        super(parent);
        if (ignoreData.indefinite) {
            timer = null;
        } else {
            timer = new Timer(60000, this);
        }
        this.ignoreData = ignoreData;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        IconButton removeButton = new IconButton(PreloadedImage.CLOSE.getImage(), 20);
        JLabel itemLabel = new JLabel(ignoreData.getItemName());
        JPanel itemPanel = new JPanel(new GridBagLayout());
        itemPanel.setPreferredSize(new Dimension(ITEM_MAX_WIDTH, itemLabel.getPreferredSize().height));
        JLabel matchLabel = new JLabel(ignoreData.getMatchType().toString());
        if (ignoreData.getIndefinite()) {
            timerLabel = new JLabel("~");
        } else {
            timerLabel = new JLabel(ignoreData.getRemainingTime() + "m");
        }

        itemPanel.setBackground(ColorManager.CLEAR);
        itemPanel.add(itemLabel);


        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.gridy = 0;

        gc.gridx++;
        gc.gridx++;
        this.add(new BufferPanel(100, 0), gc);
        gc.gridx++;
        this.add(new BufferPanel(60, 0), gc);
        gc.gridx = 0;
        gc.gridy++;
        this.add(removeButton, gc);
        gc.insets.left = 10;
        gc.insets.right = 10;
        gc.gridx++;
        this.add(itemPanel, gc);
        gc.gridx++;
        this.add(matchLabel, gc);
        gc.gridx++;
        this.add(timerLabel, gc);
        gc.gridx++;

        if (!ignoreData.indefinite) {
            timer.start();
        }


        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markForDeletion();
            }
        });
        this.revalidate();
    }

    public IgnoreData getIgnoreData() {
        return this.ignoreData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ignoreData.indefinite) {
            timerLabel.setText("~");
            return;
        }
        int time = ignoreData.getRemainingTime();
        if (time <= 0) {
            this.dispose();
        } else {
            timerLabel.setText(time + "m");
        }
    }

    @Override
    protected void dispose() {
        System.out.println("LOCAL DISPOSE");
        super.dispose();
        timer.stop();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();

    }

}
