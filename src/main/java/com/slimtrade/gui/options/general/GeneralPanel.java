package com.slimtrade.gui.options.general;

import com.slimtrade.gui.components.SectionHeader;
import com.slimtrade.gui.options.toggle.ToggleOptionsPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import java.awt.*;

public class GeneralPanel extends ContainerPanel {

    private static final long serialVersionUID = 1L;

    private BasicsPanel basicsPanel;
    private MessageSettingsPanel messageSettingsPanel;
    public HistorySettingsPanel historyPanel;
    private AudioPanel audioPanel;
    private AdvancedPanel advancedPanel;
    private ToggleOptionsPanel toggleOptionsPanel;

    private final int smallGap = 4;
    private final int largeGap = 15;

    public GeneralPanel() {
        SectionHeader basicsHeader = new SectionHeader("Basics");
        SectionHeader messagingHeader = new SectionHeader("Message Popups");
        SectionHeader historyHeader = new SectionHeader("History");
        SectionHeader audioHeader = new SectionHeader("Audio");
        SectionHeader disableOptionsHeader = new SectionHeader("Toggle Features");
        SectionHeader clientHeader = new SectionHeader("Path of Exile");

        basicsPanel = new BasicsPanel();
        messageSettingsPanel = new MessageSettingsPanel();
        historyPanel = new HistorySettingsPanel();
        audioPanel = new AudioPanel();
        toggleOptionsPanel = new ToggleOptionsPanel();
        advancedPanel = new AdvancedPanel();

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets.bottom = smallGap;
        container.add(basicsHeader, gc);
        gc.gridy++;
        gc.insets.bottom = largeGap;
        container.add(basicsPanel, gc);
        gc.gridy++;

        gc.insets.bottom = smallGap;
        container.add(messagingHeader, gc);
        gc.gridy++;
        gc.insets.bottom = largeGap;
        container.add(messageSettingsPanel, gc);
        gc.gridy++;

        gc.insets.bottom = smallGap;
        container.add(historyHeader, gc);
        gc.gridy++;
        gc.insets.bottom = largeGap;
        container.add(historyPanel, gc);
        gc.gridy++;

        gc.insets.bottom = smallGap;
        container.add(audioHeader, gc);
        gc.insets.bottom = largeGap;
        gc.gridy++;
        container.add(audioPanel, gc);
        gc.gridy++;

        gc.insets.bottom = smallGap;
        container.add(disableOptionsHeader, gc);
        gc.insets.bottom = largeGap;
        gc.gridy++;
        container.add(toggleOptionsPanel, gc);
        gc.gridy++;

        gc.insets.bottom = smallGap;
        container.add(clientHeader, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(advancedPanel, gc);
        gc.gridy++;

    }

    public String getClientPath() {
        return advancedPanel.getClientText();
    }

}
