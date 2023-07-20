package peaksoft.house.gadgetariumb9.entities.color;

import lombok.Getter;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CodeColor {
    private Map<String, String> colors = new HashMap<>();
    {
        colors.put("#FFFFFF", "Whit");
        colors.put("#E1E1E1", "LightGray");
        colors.put("#CCCCCC", "Gra");
        colors.put("#B2B2B2", "Gra");
        colors.put("#9C9C9C", "LightGray");
        colors.put("#686868", "Gra");
        colors.put("#343434", "Gra");
        colors.put("#000000", "Blac");
        colors.put("#FFBEBE", "Peac");
        colors.put("#CD6666", "Tomat");
        colors.put("#FF0000", "Re");
        colors.put("#E60000", "BrightRed");
        colors.put("#A80000", "DarkRed");
        colors.put("#730000", "Maroo");
        colors.put("#F57A7A", "Re");
        colors.put("#894444", "Brow");
        colors.put("#FFEBBE", "Pin");
        colors.put("#FFA77F", "BrightOrange");
        colors.put("#FF5500", "Orang");
        colors.put("#732600", "Brow");
        colors.put("#D7B09E", "Beig");
        colors.put("#CD8966", "Ta");
        colors.put("#895A44", "Brow");
        colors.put("#FFD37F", "PaleOrange");
        colors.put("#FFAA00", "BrightYellow");
        colors.put("#E69800", "Golde");
        colors.put("#A87000", "BrightOrange");
        colors.put("#734C00", "DarkOrange");
        colors.put("#D7C29E", "Beig");
        colors.put("#F5CA7A", "Yello");
        colors.put("#CDAA66", "Yello");
        colors.put("#FFFFBE", "Yello");
        colors.put("#FFFF72", "BrightYellow");
        colors.put("#E6E600", "Yello");
        colors.put("#A8A800", "BrightYellow");
        colors.put("#737300", "Oliv");
        colors.put("#CDCD66", "Yello");
        colors.put("#898944", "Yello");
        colors.put("#D1FF73", "Lim");
        colors.put("#AAFF00", "Lim");
        colors.put("#4CE600", "BrightGreen");
        colors.put("#38A800", "Gree");
        colors.put("#267300", "Gree");
        colors.put("#A5F57A", "Yello");
        colors.put("#5C8944", "Gree");
        colors.put("#BEFFE8", "Turquois");
        colors.put("#72FFE0", "Turquois");
        colors.put("#00FFC5", "Turquois");
        colors.put("#00E6A9", "BrightTurquoise");
        colors.put("#00A884", "Tea");
        colors.put("#00734C", "Gree");
        colors.put("#9ED7C2", "Aquamarin");
        colors.put("#66CDAB", "Turquois");
        colors.put("#BEE8FF", "BabyBlue");
        colors.put("#73DFFF", "Turquois");
        colors.put("#00C5FF", "Turquois");
        colors.put("#00A9E6", "BrightLight Blu");
        colors.put("#0084A8", "DarkBlue");
        colors.put("#004C73", "Blu");
        colors.put("#9EBBD7", "Aquamarin");
        colors.put("#6699CD", "Turquois");
        colors.put("#BED2FF", "Lavende");
        colors.put("#73B2FF", "BrightPurple");
        colors.put("#0071FF", "Blu");
        colors.put("#005CE6", "BrightBlue");
        colors.put("#004DA8", "DarkViolet");
        colors.put("#002673", "Blu");
        colors.put("#9EAAD7", "Aquamarin");
        colors.put("#7A8EF5", "LightBlue");
        colors.put("#172973", "DarkBlue");
        colors.put("#012E95", "DeepBlue");
        colors.put("#040097", "DeepBlue");
        colors.put("#00329A", "BrightNavy");
        colors.put("#0A29C0", "Indig");
        colors.put("#3B3AC4", "Blu");
        colors.put("#035FE4", "Blu");
        colors.put("#0370CB", "BrightBlue");
        colors.put("#E8BEFF", "Lavende");
        colors.put("#C500FF", "Purpl");
        colors.put("#A900E6", "Purpl");
        colors.put("#8400A8", "BrightViolet");
        colors.put("#4C0073", "Viole");
        colors.put("#C29ED7", "Lavende");
        colors.put("#AA66CD", "Purpl");
        colors.put("#704489", "Purpl");
        colors.put("#FFBEE8", "LightPink");
        colors.put("#FF73DF", "BrightPink");
        colors.put("#FF00C5", "Pin");
        colors.put("#A80084", "BrightFuchsia");
        colors.put("#73004C", "Viole");
        colors.put("#D69DBC", "Lavende");
        colors.put("#CD6699", "Purpl");
        colors.put("#894465", "Viole");
    }

    public String ColorName(String code) {
        return colors.getOrDefault(code, "Unknown");
    }
}
