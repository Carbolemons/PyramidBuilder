package pyramidbuilder; 

/* ============================================================================
  _____                           _     _ ____        _ _     _           
 |  __ \                         (_)   | |  _ \      (_) |   | |          
 | |__) |   _ _ __ __ _ _ __ ___  _  __| | |_) |_   _ _| | __| | ___ _ __ 
 |  ___/ | | | '__/ _` | '_ ` _ \| |/ _` |  _ <| | | | | |/ _` |/ _ \ '__|
 | |   | |_| | | | (_| | | | | | | | (_| | |_) | |_| | | | (_| |  __/ |   
 |_|    \__, |_|  \__,_|_| |_| |_|_|\__,_|____/ \__,_|_|_|\__,_|\___|_|   
         __/ |   by: Lee C. (Carbolemons)   2021                                                       
        |___/                                                             
// references:
// https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797 // for ANSI Escape code knowledge and information
// https://www.asciiart.eu/space/aliens // for cute alien art

============================================================================ */

import java.util.Random;
import java.util.Scanner;

import lcurses.LCurses;

public class PyramidBuilder implements LCurses{

    public static final int frames_ms = 5; // How quickly between each draw for certain scenes. Some draws are instant. Mostly for aesthetic reasons
    public static final int mode_y = 48; // Console lines / Height
    public static final int mode_x = 128; // Console columns / Width

    public static void main(String[] args) throws Exception {
        System.out.print(ANSI.ESC + "=19h"); // Set screen mode
        System.out.print(ANSI.ESC + "?25l"); // Set invisible cursor
        System.out.print(ANSI.RST + " " + ANSI.CLR);          // Clear console

        // == MAIN LOOP ==

        while(true) {
            System.out.print(ANSI.CLR); // give the screen a good ol fashioned wipe
            System.out.printf( // simple splash
                ANSI.YLW +
                "================================================================================\n"+
                "//                                                                              \n"+ ANSI.YLW +
                "// "+ANSI.YLW_I+"/\\ P Y R A M I D    B U I L D E R /\\"+ANSI.YLW+"             \n"+
                ANSI.WHT +
                "//        ___    * ~   ,    .\n//    ___/o_o\\___    ~ , . ~     ,     .\n//   /   '---'   \\.     ~  . ,    .    ~\n//   '--_______--'  ~  . *\n"+
                ANSI.YLW +
                "//                                                                              \n"+
                "================================================================================\n"+
                ANSI.GRN_I+"Alien Worker: "+ANSI.WHT+"'o Gracious Leader, shall we build "+ANSI.MAG_B_I+"The Pyramid"+ANSI.WHT+"?\n"+ANSI.YLW+
                "--------------------------------------------------------------------------------\n"+
                ANSI.GRN_I+"1) Bring forth "+ANSI.MAG_B_I+"The Pyramid"+ANSI.GRN_I+"!\n"+
                "2) PyramidGAME! :D *~ -_ *, ^ ~\n"+
                ANSI.RED+"3) Let's leave this place. (Quit)\n"+
                ANSI.WHT + ">> " + 
                ANSI.YLW + ANSI.ESC + "?25h" + ANSI.ESC + "s"
                );
            draw_scene(17); // draw a sky/cloud scene for the splash
            Scanner mode_input = new Scanner(System.in);
            Scanner index_input = new Scanner(System.in);

            switch (mode_input.nextInt()){
                case 1: // Standard
                    System.out.print(ANSI.CLR);
                    System.out.printf(
                    ANSI.RED_I + "// Build/Height limit is 48!\n"+
                    ANSI.GRN_I+"Alien Worker: 'o Gracious Leader, how tall must we build "+ANSI.MAG_B_I+"The Pyramid"+ANSI.GRN_I+" for these Humans?\n"+ANSI.WHT+
                    ">> "+ANSI.BLU_I + ANSI.ESC + "s");
                    draw_scene(4); // draw the sky box
                    int index = index_input.nextInt();
                    instant_pyramid((index > 48) ? 48 : (index < 1) ? 1 : index); // if the inputted value is above the max height, or below the floor then bring it back
                    break;
                case 2: // Extra
                    System.out.print(ANSI.CLR);
                    //System.out.printf("!! UNDER CONSTRUCTION !!\n");
                    Game.game();
                    break; // Quit
                case 4:
                    draw_test();
                    break;
                default:
                    mode_input.close();
                    index_input.close();
                return;
            }
        }
    }

    private static void instant_pyramid(int index) throws Exception{
        System.out.print(ANSI.ESC + "?25l"); // Set invisible cursor

        ANSI[] colors = {ANSI.YLW_I,ANSI.YLW_I,ANSI.YLW_I,ANSI.YLW};   // Colors to be used in the pyramid
        char[] letters = {'#','#','#','*','*','^','&','@'};  // Characters to be used in the pyramid

        // Draw a cute little UFO over the pyramid tip, unless the pyramid is too high then move it to the side
        LCurses.draw(" ___/"+ANSI.GRN_I+"o_o"+ANSI.WHT+"\\___ ", ANSI.WHT, ((index > 42) ? 5 : ((mode_x)/2) - 6), (((index > 42) ? 6 : (mode_y - index) - 4)));
        LCurses.draw("/   '---'   \\", ANSI.WHT, ((index > 42) ? 5 : ((mode_x)/2) - 6), (((index > 42) ? 7 : (mode_y - index) - 3)));
        LCurses.draw("'--_______--'", ANSI.WHT, ((index > 42) ? 5 : ((mode_x)/2) - 6), (((index > 42) ? 8 : (mode_y - index) - 2)));

        for(int y = index; y>= 1; y--){ // Height begins from the bottom up, to index, the entered height
            for(int x = 1; x<= mode_x; x++){ // x begins from left up to console window maximum
                // Pick a character to be added to the pyramid
                String chr = (x <= (index-y) || x >= (index+y)) ? null : String.valueOf(letters[new Random().nextInt(letters.length)]); // If x is less or greater than the input plus or minus the current y level, then skip placing a pyramid block
                
                if (chr != null){ // If a valid pyramid block
                    //LCurses.draw("POS X: " + String.format("%03d", x) + ", Y: " + String.format("%02d", y), ANSI.WHT, 1, 3);
                    int color = new Random().nextInt(colors.length); // 75% chance to be ANSI YELLOW. will generate a number between 1 and 4 and pick that from the color array
                
                    LCurses.draw(chr, colors[color], (colors[color] != ANSI.YLW) ? ANSI.YLW_BG : ANSI.YLW_BG_I, x + ((mode_x)/2) - index, (mode_y - index) + y); // draw the pyramid block with a background color opposite of its main color, at the center bottom of the screen
                
                    Thread.sleep(frames_ms);
                
                } else continue; // if not a valid pyramid block, continue iterating from the top
            }
        }

        LCurses.draw("// Press [ENTER] to continue >> ", ANSI.RED, 1, 4); // Signify that the program has completed

        if (System.in.read() == 0) { // wait for input
            System.out.print(ANSI.CLR); // clear screen
            return; // exit to main
        }
    }

    public static void draw_scene(int start_height) throws Exception {

        char[] sky_bits = {'*',',','.','*','~'};    // valid
        ANSI[] sky_color = {ANSI.BLU_BG_I, ANSI.BLU_BG, ANSI.BLK_BG_I, ANSI.BLK_BG};
        
        int sky_color_decision = new Random().nextInt(sky_color.length);

        for(int y = start_height; y<= mode_y; y++){ // begin drawing below the text box
            for(int x = 1; x<= mode_x; x++){ // x begins from left up to console window maximum

                if(y < 25) { // clouds are only allowed to be placed above y=25 on the console
                    
                    int sky_bit_decision = new Random().nextInt(sky_bits.length * 12); // one twelfth of the time, should draw a cloud or star particle
                    
                    if (sky_bit_decision < sky_bits.length) { // if a valid particle
                        LCurses.draw(sky_color[sky_color_decision] + String.valueOf(sky_bits[sky_bit_decision]), ANSI.WHT, x, y); // draw the cloud particle
                    
                    } else {
                        LCurses.draw(" ",ANSI.BLU_I,sky_color[sky_color_decision], x, y); // else draw sky blocks
                    
                    }
                } else {
                    LCurses.draw(" ",ANSI.BLU_I,sky_color[sky_color_decision], x, y);

                }

            }
        }
        System.out.print(ANSI.ESC + "u" + ANSI.RST + ANSI.YLW); // reset cursor to text box
    }

    private static void draw_test() throws Exception{
        System.out.print(ANSI.ESC + "?25l");
        while(true){
            ANSI[] colors = {ANSI.RED,ANSI.GRN,ANSI.YLW,ANSI.BLU,ANSI.MAG,ANSI.CYN};
            char[] letters = {'R','G','Y','B','M','C'};
            for(int y = mode_y; y>= 1; y--){
                for(int x = 1; x<= mode_x; x++){
                    int colorint = new Random().nextInt(letters.length);
                    String chr = (y == 1 || y == mode_y || x == 1 || x == mode_x) ? "#" : String.valueOf(letters[colorint]);
                    ANSI col = (y == 1 || y == mode_y || x == 1 || x == mode_x) ? ANSI.WHT : colors[new Random().nextInt(colors.length)];
                    LCurses.draw("POS X: " + String.format("%03d", x) + ", Y: " + String.format("%02d", y), ANSI.WHT, 1, 1);
                    LCurses.draw(chr, col, x, y);
                    Thread.sleep(5);
                }
            }
            System.out.print(ANSI.CLR);
        }
    }
}