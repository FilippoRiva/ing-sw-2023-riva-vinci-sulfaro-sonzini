package it.polimi.myshelfie.view.textual;

public abstract class ANSI {
    public static String RESET = "\033[0m";
    public static String RED_FG = "\033[31m";
    public static String GREEN_FG = "\033[32m";
    public static String GRAY_FG = "\033[37m";
    public static String YELLOW_FG = "\033[93m";
    public static String BLUE_FG = "\033[34m";
    public static String LIGHTBLUE_FG = "\033[36m";
    public static String PINK_FG = "\033[35m";
    public static String RED_BG = "\033[41m";
    public static String GREEN_BG = "\033[42m";
    public static String WHITE_BG = "\033[47m";
    public static String YELLOW_BG = "\033[103m";
    public static String BLUE_BG = "\033[44m";
    public static String LIGHTBLUE_BG = "\033[46m";
    public static String PINK_BG = "\033[45m";
    public static String BOLD = "\033[1m";
    public static String HOME = "\033[H";
    public static String ERASE_LINE = "\033[2K";
    public static String CURSOR_UP = "\033[1A";
    public static String CURSOR_DOWN = "\033[1B";
    public static String CURSOR_DOWN_0 ="\033[1E";
    public static String CURSOR_UP_0 ="\033[1F";
    public static String CURSOR_RIGHT ="\033[1C";
    public static String CURSOR_LEFT="\033[1D";
    public static String CLEAR_ALL="\033[H\033[0J";
}
