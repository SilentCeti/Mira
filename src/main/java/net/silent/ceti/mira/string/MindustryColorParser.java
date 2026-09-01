package net.silent.ceti.mira.string;

import java.util.Map;

public class MindustryColorParser {
    private static final String RESET      = "\u001B[0m";

    private static final String BOLD = "\u001B[1m";
    private static final String DIM = "\u001B[2m";
    private static final String ITALIC = "\u001B[3m";
    private static final String UNDERLINE = "\u001B[4m";
    private static final String BLINK = "\u001B[5m";
    private static final String REVERSE = "\u001B[7m";
    private static final String HIDDEN = "\u001B[8m";
    private static final String STRIKETHROUGH = "\u001B[9m";

    private static final String WHITE      = "\u001B[38;2;255;255;255m";
    private static final String LIGHTGRAY  = "\u001B[38;2;191;191;191m";
    private static final String GRAY       = "\u001B[38;2;127;127;127m";
    private static final String DARKGRAY   = "\u001B[38;2;63;63;63m";
    private static final String BLACK      = "\u001B[38;2;0;0;0m";

    private static final String BLUE       = "\u001B[38;2;0;0;255m";
    private static final String NAVY       = "\u001B[38;2;0;0;127m";
    private static final String ROYAL      = "\u001B[38;2;65;105;225m";
    private static final String SLATE      = "\u001B[38;2;112;0;144m";
    private static final String SKY        = "\u001B[38;2;135;206;235m";

    private static final String CYAN       = "\u001B[38;2;0;255;255m";
    private static final String TEAL       = "\u001B[38;2;0;127;127m";

    private static final String GREEN      = "\u001B[38;2;0;255;0m";
    private static final String ACID       = "\u001B[38;2;127;255;0m";
    private static final String LIME       = "\u001B[38;2;50;205;50m";
    private static final String FOREST     = "\u001B[38;2;34;139;34m";
    private static final String OLIVE      = "\u001B[38;2;107;142;35m";

    private static final String YELLOW     = "\u001B[38;2;255;255;0m";
    private static final String GOLD       = "\u001B[38;2;255;215;0m";
    private static final String GOLDENROD  = "\u001B[38;2;218;165;32m";

    private static final String ORANGE     = "\u001B[38;2;255;165;0m";
    private static final String BROWN      = "\u001B[38;2;139;69;19m";
    private static final String TAN        = "\u001B[38;2;210;180;140m";
    private static final String BRICK      = "\u001B[38;2;178;34;34m";

    private static final String RED        = "\u001B[38;2;255;0;0m";
    private static final String SCARLET    = "\u001B[38;2;255;52;28m";
    private static final String CORAL      = "\u001B[38;2;255;127;80m";
    private static final String SALMON     = "\u001B[38;2;250;128;114m";

    private static final String PINK       = "\u001B[38;2;255;105;180m";
    private static final String MAGENTA    = "\u001B[38;2;255;0;255m";
    private static final String PURPLE     = "\u001B[38;2;128;0;255m";
    private static final String VIOLET     = "\u001B[38;2;238;130;238m";
    private static final String MAROON     = "\u001B[38;2;176;48;96m";

    private static final Map<String, String> COLORS = Map.ofEntries(
            Map.entry("[r]", RESET),

            Map.entry("[b]", BOLD),
            Map.entry("[i]", ITALIC),
            Map.entry("[u]", UNDERLINE),
            Map.entry("[s]", STRIKETHROUGH),
            Map.entry("[d]", DIM),
            Map.entry("[bl]", BLINK),
            Map.entry("[re]", REVERSE),
            Map.entry("[hi]", HIDDEN),
            Map.entry("[st]", STRIKETHROUGH),

            Map.entry("[white]", WHITE),
            Map.entry("[lightgray]", LIGHTGRAY),
            Map.entry("[gray]", GRAY),
            Map.entry("[darkgray]", DARKGRAY),
            Map.entry("[black]", BLACK),

            Map.entry("[blue]", BLUE),
            Map.entry("[navy]", NAVY),
            Map.entry("[royal]", ROYAL),
            Map.entry("[slate]", SLATE),
            Map.entry("[sky]", SKY),

            Map.entry("[cyan]", CYAN),
            Map.entry("[teal]", TEAL),

            Map.entry("[green]", GREEN),
            Map.entry("[acid]", ACID),
            Map.entry("[lime]", LIME),
            Map.entry("[forest]", FOREST),
            Map.entry("[olive]", OLIVE),

            Map.entry("[yellow]", YELLOW),
            Map.entry("[gold]", GOLD),
            Map.entry("[goldenrod]", GOLDENROD),

            Map.entry("[orange]", ORANGE),
            Map.entry("[brown]", BROWN),
            Map.entry("[tan]", TAN),
            Map.entry("[brick]", BRICK),

            Map.entry("[red]", RED),
            Map.entry("[scarlet]", SCARLET),
            Map.entry("[coral]", CORAL),
            Map.entry("[salmon]", SALMON),

            Map.entry("[pink]", PINK),
            Map.entry("[magenta]", MAGENTA),
            Map.entry("[purple]", PURPLE),
            Map.entry("[violet]", VIOLET),
            Map.entry("[maroon]", MAROON)
    );

    public static String toAnsi(String string) {
        for (Map.Entry<String, String> entry : COLORS.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }

        return string;
    }
}
