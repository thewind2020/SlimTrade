package com.slimtrade.core.saving;

import com.slimtrade.core.References;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.elements.SoundElement;
import com.slimtrade.enums.*;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.options.cheatsheet.CheatSheetData;
import com.slimtrade.gui.options.ignore.IgnoreData;
import com.slimtrade.gui.options.stashsearch.StashSearchData;

import java.util.ArrayList;
import java.util.Date;

public class SaveFile {

    //Version
    public String versionNumber = References.getAppVersion();

    // Basics
    public String characterName = "";
    public boolean showGuildName = false;
    public boolean colorBlindMode = false;
    public ColorTheme colorTheme = ColorTheme.SOLARIZED_LIGHT;
    public QuickPasteSetting quickPasteSetting = QuickPasteSetting.DISABLED;
    public HotkeyData quickPasteHotkey = null;

    // Messaging
    public boolean collapseExcessiveMessages = false;
    public int messageCountBeforeCollapse = 3;
    public boolean fadeAfterDuration = false;
    public double secondsBeforeFading = 2;
    public int fadeOpacityPercent = 50;

    // History
    public TimeStyle timeStyle = TimeStyle.H12;
    public DateStyle dateStyle = DateStyle.DDMMYY;
    public OrderType orderType = OrderType.NEW_FIRST;
    public int historyLimit = 25;

    // Disable Features
    public boolean enableIncomingTrades = true;
    public boolean enableOutgoingTrades = true;
    public boolean enableItemHighlighter = true;
    public boolean enableMenubar = true;

    // Audio
    public SoundElement incomingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement outgoingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement scannerMessageSound = new SoundElement(Sound.PING2, 50);
    public SoundElement playerJoinedSound = new SoundElement(Sound.BLIP1, 50);

    // Stash Search
    public HotkeyData stashSearchHotkey = null;
    public ArrayList<StashSearchData> stashSearchData = new ArrayList<>();

    // Cheat Sheets
    public ArrayList<CheatSheetData> cheatSheetData = new ArrayList<>();

    // Client
    public String clientPath = null;

    public ArrayList<StashTab> stashTabs = new ArrayList<>();
    public ArrayList<IgnoreData> ignoreData = new ArrayList<>();
    // Custom Macros
    public MacroButton[] incomingMacros = {
            new MacroButton(ButtonRow.TOP, "Hi, are you still interested in my {item} listed for {price}?", "", CustomIcons.REFRESH, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/invite {player}", "", CustomIcons.INVITE, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/tradewith {player}", "", CustomIcons.CART, null, false),
            new MacroButton(ButtonRow.BOTTOM, "thanks", "", CustomIcons.THUMB, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/kick {player}", "", CustomIcons.LEAVE, null, true),
    };
    public MacroButton[] outgoingMacros = {
            new MacroButton(ButtonRow.TOP, "{message}", "", CustomIcons.REFRESH, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/hideout {player}", "", CustomIcons.WARP, null, false),
            new MacroButton(ButtonRow.BOTTOM, "thanks", "", CustomIcons.THUMB, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/kick {self}", "", CustomIcons.LEAVE, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/hideout", "", CustomIcons.HOME, null, true),
    };

    // Hotkeys
    public HotkeyData optionsHotkey = null;
    public HotkeyData historyHotkey = null;
    public HotkeyData chatScannerHotkey = null;
    public HotkeyData closeTradeHotkey = null;
    public HotkeyData remainingHotkey = null;
    public HotkeyData hideoutHotkey = null;
    public HotkeyData leavePartyHotkey = null;
    public HotkeyData betrayalHotkey = null;
    public HotkeyData dndHotkey = null;

    public SaveFile() {
        // Stash Search Defaults
        stashSearchData.add(new StashSearchData("map", "maps", StashTabColor.FIVE));
        stashSearchData.add(new StashSearchData("div", "divination card", StashTabColor.TWENTYFIVE));
        stashSearchData.add(new StashSearchData("sac", "sacrifice at", StashTabColor.SEVEN));
        stashSearchData.add(new StashSearchData("ess", "essence", StashTabColor.FIFTEEN));
        stashSearchData.add(new StashSearchData("$$$", "currency", StashTabColor.TWENTYTWO));
        stashSearchData.add(new StashSearchData("headhunter", "headhunter", StashTabColor.ZERO));
    }

    // TODO : Should move this to somewhere more logical
    public static int dateDifference(Date d1, Date d2) {
        try {
            long diff = d2.getTime() - d1.getTime();
            if (diff < 0) {
                return -1;
            }

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

            diffMinutes += diffHours * 60;
            if (diffSeconds > 30) {
                diffMinutes++;
            }
            return (int) diffMinutes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}