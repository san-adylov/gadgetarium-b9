package peaksoft.house.gadgetariumb9.models.color;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class CodeColor {
    private final Map<String, String> colors = new HashMap<>();

    {
        colors.put("#FFFFFF", "White");
        colors.put("#E1E1E1", "LightGray");
        colors.put("#CCCCCC", "Gray");
        colors.put("#B2B2B2", "Gray");
        colors.put("#9C9C9C", "LightGray");
        colors.put("#686868", "Gray");
        colors.put("#343434", "Gray");
        colors.put("#000000", "Black");
        colors.put("#FFBEBE", "Peach");
        colors.put("#CD6666", "Tomato");
        colors.put("#FF0000", "Red");
        colors.put("#E60000", "BrightRed");
        colors.put("#A80000", "DarkRed");
        colors.put("#730000", "Maroon");
        colors.put("#F57A7A", "Red");
        colors.put("#894444", "Brown");
        colors.put("#FFEBBE", "Pink");
        colors.put("#FFA77F", "BrightOrange");
        colors.put("#FF5500", "Orange");
        colors.put("#732600", "Brown");
        colors.put("#D7B09E", "Beige");
        colors.put("#CD8966", "Tan");
        colors.put("#895A44", "Brown");
        colors.put("#FFD37F", "PaleOrange");
        colors.put("#FFAA00", "BrightYellow");
        colors.put("#E69800", "Gold");
        colors.put("#A87000", "BrightOrange");
        colors.put("#734C00", "DarkOrange");
        colors.put("#D7C29E", "Beige");
        colors.put("#F5CA7A", "Yellow");
        colors.put("#CDAA66", "Yellow");
        colors.put("#FFFFBE", "Yellow");
        colors.put("#FFFF72", "BrightYellow");
        colors.put("#E6E600", "Yellow");
        colors.put("#A8A800", "BrightYellow");
        colors.put("#737300", "Olive");
        colors.put("#CDCD66", "Yellow");
        colors.put("#898944", "Yellow");
        colors.put("#D1FF73", "Lime");
        colors.put("#AAFF00", "Lime");
        colors.put("#4CE600", "BrightGreen");
        colors.put("#38A800", "Green");
        colors.put("#267300", "Green");
        colors.put("#A5F57A", "Yellow");
        colors.put("#5C8944", "Green");
        colors.put("#BEFFE8", "Turquoise");
        colors.put("#72FFE0", "Turquoise");
        colors.put("#00FFC5", "Turquoise");
        colors.put("#00E6A9", "BrightTurquoise");
        colors.put("#00A884", "Teal");
        colors.put("#00734C", "Green");
        colors.put("#9ED7C2", "Aquamarine");
        colors.put("#66CDAB", "Turquoise");
        colors.put("#BEE8FF", "BabyBlue");
        colors.put("#73DFFF", "Turquoise");
        colors.put("#00C5FF", "Turquoise");
        colors.put("#00A9E6", "BrightLightBlue");
        colors.put("#0084A8", "DarkBlue");
        colors.put("#004C73", "Blue");
        colors.put("#9EBBD7", "Aquamarine");
        colors.put("#6699CD", "Turquoise");
        colors.put("#BED2FF", "Lavender");
        colors.put("#73B2FF", "BrightPurple");
        colors.put("#0071FF", "Blue");
        colors.put("#005CE6", "BrightBlue");
        colors.put("#004DA8", "DarkViolet");
        colors.put("#002673", "Blue");
        colors.put("#9EAAD7", "Aquamarine");
        colors.put("#7A8EF5", "LightBlue");
        colors.put("#172973", "DarkBlue");
        colors.put("#012E95", "DeepBlue");
        colors.put("#040097", "DeepBlue");
        colors.put("#00329A", "BrightNavy");
        colors.put("#0A29C0", "Indigo");
        colors.put("#3B3AC4", "Blue");
        colors.put("#035FE4", "Blue");
        colors.put("#0370CB", "BrightBlue");
        colors.put("#E8BEFF", "Lavender");
        colors.put("#C500FF", "Purple");
        colors.put("#A900E6", "Purple");
        colors.put("#8400A8", "BrightViolet");
        colors.put("#4C0073", "Violet");
        colors.put("#C29ED7", "Lavender");
        colors.put("#AA66CD", "Purple");
        colors.put("#704489", "Purple");
        colors.put("#FFBEE8", "LightPink");
        colors.put("#FF73DF", "BrightPink");
        colors.put("#FF00C5", "Pink");
        colors.put("#A80084", "BrightFuchsia");
        colors.put("#73004C", "Violet");
        colors.put("#D69DBC", "Lavender");
        colors.put("#CD6699", "Purple");
        colors.put("#894465", "Violet");
    }

    public List<String> ColorName(String code) {
        return Collections.singletonList(colors.getOrDefault(code, "Unknown"));
    }
}
