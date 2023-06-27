package it.polimi.myshelfie.view.textual;

public abstract class ANSI {
    public static final String RESET = "\033[0m";
    public static final String RED_FG = "\033[31m";
    public static final String GREEN_FG = "\033[32m";
    public static final String GRAY_FG = "\033[37m";
    public static final String YELLOW_FG = "\033[93m";
    public static final String BLUE_FG = "\033[34m";
    public static final String LIGHTBLUE_FG = "\033[36m";
    public static final String PINK_FG = "\033[35m";
    public static final String BOLD = "\033[1m";
    public static final String HOME = "\033[H";
    public static final String ERASE_LINE = "\033[2K";
    public static final String CURSOR_DOWN = "\033[1B";
    public static final String CURSOR_LEFT="\033[1D";
    public static final String CLEAR_ALL="\033[H\033[0J";
}
