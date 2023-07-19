package peaksoft.house.gadgetariumb9.color;

import lombok.Getter;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CodeColor {
    private Map<String, String> colorMap = new HashMap<>();
    {
        colorMap.put("#FFFFFF", "Whit");
        colorMap.put("#E1E1E1", "LightGray");
        colorMap.put("#CCCCCC", "Gra");
        colorMap.put("#B2B2B2", "Gra");
        colorMap.put("#9C9C9C", "LightGray");
        colorMap.put("#686868", "Gra");
        colorMap.put("#343434", "Gra");
        colorMap.put("#000000", "Blac");
        colorMap.put("#FFBEBE", "Peac");
        colorMap.put("#CD6666", "Tomat");
        colorMap.put("#FF0000", "Re");
        colorMap.put("#E60000", "BrightRed");
        colorMap.put("#A80000", "DarkRed");
        colorMap.put("#730000", "Maroo");
        colorMap.put("#F57A7A", "Re");
        colorMap.put("#894444", "Brow");
        colorMap.put("#FFEBBE", "Pin");
        colorMap.put("#FFA77F", "BrightOrange");
        colorMap.put("#FF5500", "Orang");
        colorMap.put("#732600", "Brow");
        colorMap.put("#D7B09E", "Beig");
        colorMap.put("#CD8966", "Ta");
        colorMap.put("#895A44", "Brow");
        colorMap.put("#FFD37F", "PaleOrange");
        colorMap.put("#FFAA00", "BrightYellow");
        colorMap.put("#E69800", "Golde");
        colorMap.put("#A87000", "BrightOrange");
        colorMap.put("#734C00", "DarkOrange");
        colorMap.put("#D7C29E", "Beig");
        colorMap.put("#F5CA7A", "Yello");
        colorMap.put("#CDAA66", "Yello");
        colorMap.put("#FFFFBE", "Yello");
        colorMap.put("#FFFF72", "BrightYellow");
        colorMap.put("#E6E600", "Yello");
        colorMap.put("#A8A800", "BrightYellow");
        colorMap.put("#737300", "Oliv");
        colorMap.put("#CDCD66", "Yello");
        colorMap.put("#898944", "Yello");
        colorMap.put("#D1FF73", "Lim");
        colorMap.put("#AAFF00", "Lim");
        colorMap.put("#4CE600", "BrightGreen");
        colorMap.put("#38A800", "Gree");
        colorMap.put("#267300", "Gree");
        colorMap.put("#A5F57A", "Yello");
        colorMap.put("#5C8944", "Gree");
        colorMap.put("#BEFFE8", "Turquois");
        colorMap.put("#72FFE0", "Turquois");
        colorMap.put("#00FFC5", "Turquois");
        colorMap.put("#00E6A9", "BrightTurquoise");
        colorMap.put("#00A884", "Tea");
        colorMap.put("#00734C", "Gree");
        colorMap.put("#9ED7C2", "Aquamarin");
        colorMap.put("#66CDAB", "Turquois");
        colorMap.put("#BEE8FF", "BabyBlue");
        colorMap.put("#73DFFF", "Turquois");
        colorMap.put("#00C5FF", "Turquois");
        colorMap.put("#00A9E6", "BrightLight Blu");
        colorMap.put("#0084A8", "DarkBlue");
        colorMap.put("#004C73", "Blu");
        colorMap.put("#9EBBD7", "Aquamarin");
        colorMap.put("#6699CD", "Turquois");
        colorMap.put("#BED2FF", "Lavende");
        colorMap.put("#73B2FF", "BrightPurple");
        colorMap.put("#0071FF", "Blu");
        colorMap.put("#005CE6", "BrightBlue");
        colorMap.put("#004DA8", "DarkViolet");
        colorMap.put("#002673", "Blu");
        colorMap.put("#9EAAD7", "Aquamarin");
        colorMap.put("#7A8EF5", "LightBlue");
        colorMap.put("#172973", "DarkBlue");
        colorMap.put("#012E95", "DeepBlue");
        colorMap.put("#040097", "DeepBlue");
        colorMap.put("#00329A", "BrightNavy");
        colorMap.put("#0A29C0", "Indig");
        colorMap.put("#3B3AC4", "Blu");
        colorMap.put("#035FE4", "Blu");
        colorMap.put("#0370CB", "BrightBlue");
        colorMap.put("#E8BEFF", "Lavende");
        colorMap.put("#C500FF", "Purpl");
        colorMap.put("#A900E6", "Purpl");
        colorMap.put("#8400A8", "BrightViolet");
        colorMap.put("#4C0073", "Viole");
        colorMap.put("#C29ED7", "Lavende");
        colorMap.put("#AA66CD", "Purpl");
        colorMap.put("#704489", "Purpl");
        colorMap.put("#FFBEE8", "LightPink");
        colorMap.put("#FF73DF", "BrightPink");
        colorMap.put("#FF00C5", "Pin");
        colorMap.put("#A80084", "BrightFuchsia");
        colorMap.put("#73004C", "Viole");
        colorMap.put("#D69DBC", "Lavende");
        colorMap.put("#CD6699", "Purpl");
        colorMap.put("#894465", "Viole");
}

    public String getColorName(String hexCode) {
        return colorMap.getOrDefault(hexCode, "Unknown");
    }
}
