package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel implements IColorable {

    public CustomLabel() {
        this(true);
    }

    public CustomLabel(boolean bold) {
        super();
        buildLabel(bold);
    }

    public CustomLabel(String text) {
        this(text, true);
    }

    public CustomLabel(String text, boolean bold) {
        super(text);
        buildLabel(bold);
    }

    private void buildLabel(boolean bold) {
        if (!bold) {
            setBold(false);
        }
    }

    public void setBold(boolean state) {
        Font curFont = getFont();
        if (state) {
            setFont(curFont.deriveFont(curFont.getStyle() | Font.BOLD));
        } else {
            setFont(curFont.deriveFont(curFont.getStyle() & ~Font.BOLD));
        }
    }

    @Override
    public JToolTip createToolTip() {
        return new CustomToolTip(this);
    }

    @Override
    public void updateColor() {
        this.setForeground(ColorManager.TEXT);
        createToolTip();
    }


}
