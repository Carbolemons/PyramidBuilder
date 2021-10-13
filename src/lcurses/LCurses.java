package lcurses; 

/* ============================================================================
  _      _____                         
 | |    / ____| by: Lee C. (Carbolemons) 2021                     
 | |   | |    _   _ _ __ ___  ___  ___ 
 | |   | |   | | | | '__/ __|/ _ \/ __|
 | |___| |___| |_| | |  \__ \  __/\__ \
 |______\_____\__,_|_|  |___/\___||___/
                                                         
// This 'interface' is for managing color codes and ANSI cursor positioning. Nothing too fancy at all however. 

// https://en.wikipedia.org/wiki/ANSI_escape_code                       -- Explanation of ANSI
// https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797        -- ANSI Escape codes

============================================================================ */

public interface LCurses { // DCurses, as, "Dylan Curses" a primitive text """drawing""" system

    // Draw function overloaded handling multiple ways to draw ANSI text to screen
    public static void draw(String out, ANSI color, ANSI bg, int x, int y) { // String out, ANSI color, ANSI bg, int x, int y
        String pos = ANSI.ESC.toString() + y + ";" + x +"f";
        System.out.print(color.toString() + bg.toString() + pos + out);
    }

    public static void draw(String out, ANSI color, int x, int y) { // String out, ANSI color, int x, int y
        String pos = ANSI.ESC.toString() + y + ";" + x +"f";
        System.out.print(ANSI.RST + color.toString() + pos + out);
    }

    public static void draw(String out, ANSI color, ANSI bg) { // String out, ANSI color, ANSI bg
        System.out.print(ANSI.RST + color.toString() + bg.toString() + out);
    }

    public static void draw(String out, ANSI color) { // String out, ANSI color, ANSI bg
        System.out.print(ANSI.RST + color.toString() + out);
    }

    public static void draw(String out) { // Draw function overloaded handling multiple ways to draw ANSI text to screen
        draw(out, ANSI.RST);
    }

    public static void move(ANSI direction,int amount){
        String rel = ANSI.ESC.toString() + amount + direction;
        System.out.print(rel);
    }

    public static void pos(int x, int y){
        String pos = ANSI.ESC.toString() + y + ";" + x +"f";
        System.out.print(pos);
    }

    // Enumerator for ANSI values/strings
    public enum ANSI { // ansi color codes
        // Special
        ESC("\033["),
        RST("\033[0m"),
        CLR("\033[H\033[2J"),
        _UP("A"), // Up
        _DN("B"), // Down
        _RT("C"), // Right
        _LT("D"), // Left
        _ND("E"), // Next Down
        _LD("F"), // Last Up
        C_V("?25h"), // Cursor Visible
        C_H("?25l"), // Cursor Hide
        C_S("7"), // save cursor
        C_R("8"), // restore cursor
        // Standard ANSI Colors
        BLK("\033[0;30m"),
        RED("\033[0;31m"),
        GRN("\033[0;32m"),
        YLW("\033[0;33m"),
        BLU("\033[0;34m"),
        MAG("\033[0;35m"),
        CYN("\033[0;36m"),
        WHT("\033[0;37m"),
        // Bold Colors
        BLK_B("\033[1;30m"),
        RED_B("\033[1;31m"),
        GRN_B("\033[1;32m"),
        YLW_B("\033[1;33m"),
        BLU_B("\033[1;34m"),
        MAG_B("\033[1;35m"),
        CYN_B("\033[1;36m"),
        WHT_B("\033[1;37m"),
        // Standard High Intensity
        BLK_I("\033[0;90m"),
        RED_I("\033[0;91m"),
        GRN_I("\033[0;92m"),
        YLW_I("\033[0;93m"),
        BLU_I("\033[0;94m"),
        MAG_I("\033[0;95m"),
        CYN_I("\033[0;96m"),
        WHT_I("\033[0;97m"),
        // Bold High Intensity
        BLK_B_I("\033[1;90m"),
        RED_B_I("\033[1;91m"),
        GRN_B_I("\033[1;92m"),
        YLW_B_I("\033[1;93m"),
        BLU_B_I("\033[1;94m"),
        MAG_B_I("\033[1;95m"),
        CYN_B_I("\033[1;96m"),
        WHT_B_I("\033[1;97m"),
        // Background
        BLK_BG("\033[40m"),
        RED_BG("\033[41m"),
        GRN_BG("\033[42m"),
        YLW_BG("\033[43m"),
        BLU_BG("\033[44m"),
        MAG_BG("\033[45m"),
        CYN_BG("\033[46m"),
        WHT_BG("\033[47m"),
        // Background High Intensity
        BLK_BG_I("\033[0;100m"),
        RED_BG_I("\033[0;101m"),
        GRN_BG_I("\033[0;102m"),
        YLW_BG_I("\033[0;103m"),
        BLU_BG_I("\033[0;104m"),
        MAG_BG_I("\033[0;105m"),
        CYN_BG_I("\033[0;106m"),
        WHT_BG_I("\033[0;107m");

        public final String codelabel;

        private ANSI(String codelabel) {
            this.codelabel = codelabel;
        }

        @Override
        public String toString() { // allow formatting of ANSI enum type to string. just in case
            return codelabel;
        }
    }
}