package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;

public class CustomTextArea extends JTextArea implements IColorable {

    public CustomTextArea(int rows, int columns) {
        super(rows, columns);
        App.eventManager.addColorListener(this);
        this.updateColor();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setBorder(ColorManager.BORDER_TEXT);
    }

}