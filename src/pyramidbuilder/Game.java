package pyramidbuilder; 

/* ============================================================================
  _____                           _     _  _____                      
 |  __ \                         (_)   | |/ ____|                     
 | |__) |   _ _ __ __ _ _ __ ___  _  __| | |  __  __ _ _ __ ___   ___ 
 |  ___/ | | | '__/ _` | '_ ` _ \| |/ _` | | |_ |/ _` | '_ ` _ \ / _ \
 | |   | |_| | | | (_| | | | | | | | (_| | |__| | (_| | | | | | |  __/
 |_|    \__, |_|  \__,_|_| |_| |_|_|\__,_|\_____|\__,_|_| |_| |_|\___|
         __/ |   by: Lee C. (Carbolemons) 2021                                                     
        |___/                                                                                                                
// references:
// https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797 // for ANSI Escape code knowledge and information
// https://www.asciiart.eu/space/aliens // for cute alien art

============================================================================ */

import java.util.Random;

import lcurses.LCurses;

import java.util.ArrayList;

public class Game extends PyramidBuilder{

    public static void game() throws Exception {
        System.out.print(ANSI.RST + " " + ANSI.CLR);          // Clear console
        
        ArrayList<Object> game_map = init_game(mode_y, mode_x); // initialize arrays for render coordinate matching
        
        char[][] map = (char[][]) game_map.get(0);          // retrieve generated board chars
        ANSI[][] color_map = (ANSI[][]) game_map.get(1);    // retrieve colors
        ANSI[][] colorbg_map = (ANSI[][]) game_map.get(2);    // retrieve colors
        int prev_color = 1;

        int ufo_x = mode_x/2 - 7, ufo_y = mode_y/2 - 12; // player coordinates
        int old_x = ufo_x, old_y = ufo_y;

        render_frame(map, color_map, colorbg_map); // render out the canvas

        render_player(old_x, old_y, ufo_x, ufo_y); // render the initial player
        render_interface(1, mode_y, ufo_x, ufo_y); // render the initial console window

        render_textbox( // generate help text box
            new String[] {
                "#===== "+ANSI.YLW+"PyramidGame :D"+ANSI.WHT+" ======#",
                "# // Movement commands      #",
                "# "+ANSI.YLW+"l"+ANSI.WHT+" - move ("+ANSI.YLW+"l"+ANSI.WHT+")eft           #",
                "# "+ANSI.YLW+"r"+ANSI.WHT+" - move ("+ANSI.YLW+"r"+ANSI.WHT+")ight          #",
                "# "+ANSI.YLW+"u"+ANSI.WHT+" - move ("+ANSI.YLW+"u"+ANSI.WHT+")p             #",
                "# "+ANSI.YLW+"d"+ANSI.WHT+" - move ("+ANSI.YLW+"d"+ANSI.WHT+")own           #",
                "# // Special commands       #",
                "# "+ANSI.MAG+"p"+ANSI.WHT+" - Create a ("+ANSI.MAG+"P"+ANSI.WHT+")yramid    #",
                "# "+ANSI.RED+"e"+ANSI.WHT+" - "+ANSI.RED+"(e)xit"+ANSI.WHT+"                #",
                "#   ===================     #",
                "# Example:                  #",
                "# "+ANSI.YLW+"input>> rrrrddddp         "+ANSI.WHT+"#",
                "# "+ANSI.YLW_I+"This means - go Right 4,  "+ANSI.WHT+"#",
                "# "+ANSI.YLW_I+"go down 4, print pyramid. "+ANSI.WHT+"#",
                "#### "+ANSI.RED+"[ENTER] to continue"+ANSI.WHT+" ####",
            },      // lines              rows
            mode_x/2 - (29/2), mode_y/2 - (14/2)); // center screen

        // GAME LOOP ====

        while(true) { 
            /* // System.in debug
                LCurses.draw("" + System.in.read() + " :", ANSI.WHT, 1, ufo_y);
                ufo_y++;
            }
            */
            
            render_interface(1, mode_y, ufo_x, ufo_y); // Draw the input screen

            switch(System.in.read()){ // Input algos
                case 117: // 117 : u
                case 56: //  56 : 8
                    old_y = ufo_y;
                    ufo_y = (ufo_y < 1) ? 0 : ufo_y - 1;
                    break;
                case 108: // 108 : l
                case 52 : // 52 : 4
                    old_x = ufo_x;
                    ufo_x = (ufo_x < 1) ? 0 : ufo_x - 1;
                    break;
                case 100: // 100 : d
                case 53:  // 53 : 5
                    old_y = ufo_y;
                    ufo_y = (ufo_y > mode_y - (p_sprite_h + 1)) ? mode_y - p_sprite_h : ufo_y + 1;
                    break;
                case 114: // 114 : r
                case 54: // 54 : 6
                    old_x = ufo_x;
                    ufo_x = (ufo_x > mode_x - (p_sprite_w + 1)) ? mode_x - p_sprite_w : ufo_x + 1;
                    break;
                case 112: // 112 : p
                    game_map = add_pyramid((mode_y - (ufo_y + 4)), ufo_x + 6, game_map, prev_color);
                    map = (char[][]) game_map.get(0);
                    color_map = (ANSI[][]) game_map.get(1);
                    colorbg_map = (ANSI[][]) game_map.get(2);
                    prev_color = (int) game_map.get(3);
                    break;
                case 101: // e exit
                    return;
                case 63: // ? help
                    render_textbox(
                        new String[] {
                            "#=====   "+ANSI.YLW+"Fun Mode :D"+ANSI.WHT+"   =====#",
                            "# // Movement commands      #",
                            "# "+ANSI.YLW+"l"+ANSI.WHT+" - move ("+ANSI.YLW+"l"+ANSI.WHT+")eft           #",
                            "# "+ANSI.YLW+"r"+ANSI.WHT+" - move ("+ANSI.YLW+"r"+ANSI.WHT+")ight          #",
                            "# "+ANSI.YLW+"u"+ANSI.WHT+" - move ("+ANSI.YLW+"u"+ANSI.WHT+")p             #",
                            "# "+ANSI.YLW+"d"+ANSI.WHT+" - move ("+ANSI.YLW+"d"+ANSI.WHT+")own           #",
                            "# // Special commands       #",
                            "# "+ANSI.MAG+"p"+ANSI.WHT+" - Create a ("+ANSI.MAG+"P"+ANSI.WHT+")yramid    #",
                            "# "+ANSI.RED+"e"+ANSI.WHT+" - "+ANSI.RED+"(e)xit"+ANSI.WHT+"                #",
                            "#   ===================     #",
                            "# Example:                  #",
                            "# "+ANSI.YLW+"input>> rrrrddddp         "+ANSI.WHT+"#",
                            "# "+ANSI.YLW_I+"This means - go Right 4,  "+ANSI.WHT+"#",
                            "# "+ANSI.YLW_I+"go down 4, print pyramid. "+ANSI.WHT+"#",
                            "#### "+ANSI.RED+"[ENTER] to continue"+ANSI.WHT+" ####",
                        },
                        7, 7);
                        if (System.in.read() == 0) { // wait for input
                            System.out.print(ANSI.CLR); // clear screen
                            System.out.print(ANSI.ESC.toString() + ANSI.C_V);
                            break; // exit to main
                        }
                    break;
                default:
                    break;
            }
            render_frame(map, color_map, colorbg_map); // render next frame
            render_player(old_x, old_y, ufo_x, ufo_y); // draw player after
            old_x = ufo_x; // reset artifact coordinates
            old_y = ufo_y;
        }
    }

    private static ArrayList<Object> add_pyramid(int index, int pos_x, ArrayList<Object> game_map, int prev_color){
        System.out.print(ANSI.ESC + "?25l"); // Set invisible cursor

        ANSI[][] colors = { // valid enscription blocks
            {ANSI.YLW_I,ANSI.YLW_I,ANSI.YLW_I,ANSI.YLW},
            {ANSI.CYN_I,ANSI.CYN_I,ANSI.CYN_I,ANSI.CYN},
            {ANSI.RED_I,ANSI.RED_I,ANSI.RED_I,ANSI.RED},
            {ANSI.GRN_I,ANSI.GRN_I,ANSI.GRN_I,ANSI.GRN},
            {ANSI.MAG_I,ANSI.MAG_I,ANSI.MAG_I,ANSI.MAG}
        };   // Colors to be used in the pyramid
        ANSI[][] colorsbg = { // valid color background blocks
            {ANSI.YLW_BG,ANSI.YLW_BG,ANSI.YLW_BG,ANSI.YLW_BG_I},
            {ANSI.CYN_BG,ANSI.CYN_BG,ANSI.CYN_BG,ANSI.CYN_BG_I},
            {ANSI.RED_BG,ANSI.RED_BG,ANSI.RED_BG,ANSI.RED_BG_I},
            {ANSI.GRN_BG,ANSI.GRN_BG,ANSI.GRN_BG,ANSI.GRN_BG_I},
            {ANSI.MAG_BG,ANSI.MAG_BG,ANSI.MAG_BG,ANSI.MAG_BG_I}
        };   // Colors to be used in the pyramid
        
        
        int color_flavor_int = 0; // decide the color, cannot be previous color
        while(color_flavor_int == prev_color) {
            color_flavor_int = new Random().nextInt(colors.length);
        }
        int color_flavor = color_flavor_int;

        char[] letters = {'#','#','#','*','*','^','&','@'};  // Characters to be used in the pyramid
        
        char[][] map = (char[][]) game_map.get(0);          // retrieve generated board chars
        ANSI[][] color_map = (ANSI[][]) game_map.get(1);    // retrieve colors
        ANSI[][] colorbg_map = (ANSI[][]) game_map.get(2);    // retrieve colors

        for(int y = index; y>= 0; y--){ // Height begins from the bottom up, to index, the entered height
            for(int x = 0; x<= pos_x + index; x++){ // x begins from left up to console window maximum
                // Pick a character to be added to the pyramid
                char chr = (x <= (index-y) || x >= (index+y)) ? '\'' : letters[new Random().nextInt(letters.length)];
                if (chr != '\''){ // If a valid pyramid block
                    int color = new Random().nextInt(colors[color_flavor].length); // 75% chance to be ANSI YELLOW. will generate a number between 1 and 4 and pick that from the color array
                    
                    int coord_x = (pos_x - index) + x;
                    int coord_y = (mode_y - index) + y - 1;

                    // If the resulting block is out of bounds, skip placing it in the map
                    if (coord_x < 0 || coord_y < 0) continue;
                    if (coord_x > mode_x - 1 || coord_y > mode_y - 1) continue;

                    map[coord_y][coord_x] = chr; // assign map symbol and color
                    color_map[coord_y][coord_x] = colors[color_flavor][color];
                    colorbg_map[coord_y][coord_x] = colorsbg[color_flavor][color];

                } else continue; // if not a valid pyramid block, continue iterating from the top
            }
        }

        return new ArrayList<Object>() { // Return both color and ascii map
            {
            add(map);
            add(color_map);
            add(colorbg_map);
            add(color_flavor);
            }
        };
    }

    public static ArrayList<Object> init_game(int init_h, int init_w){
        
        int start_height = 0;  // where the console begins drawing the scene
        int cloud_height = 25; // Y level to begin clouds
        char[] sky_bits = {'*',',','.','*','~'};    // valid cloud symbols
        ANSI[] sky_color = {ANSI.BLU_BG_I, ANSI.BLU_BG, ANSI.BLK_BG_I, ANSI.BLK_BG}; // valid colors

        int sky_color_decision = new Random().nextInt(sky_color.length); // choose sky color

        char[][] map = new char[init_h][init_w]; // initialize array for coordinate matching
        ANSI[][] color_map = new ANSI[init_h][init_w]; // initialize array for coordinate matching
        ANSI[][] colorbg_map = new ANSI[init_h][init_w];

        for(int y = start_height; y<= init_h - 1; y++){ // begin writing scene to game map
            for(int x = 0; x<= init_w - 1; x++){ 
                if(y < cloud_height) { // clouds are only allowed to be placed above y=25
                    int sky_bit_decision = new Random().nextInt(sky_bits.length * 12); // one twelfth of the time, should draw a cloud or star particle
                    if (sky_bit_decision < sky_bits.length) { // if a valid particle
                        map[y][x] = sky_bits[sky_bit_decision]; // assign map symbol and color
                        color_map[y][x] = sky_color[sky_color_decision];
                        colorbg_map[y][x] = sky_color[sky_color_decision];
                    } else {
                        map[y][x] = ' '; // else assign blank map symbol and default color
                        color_map[y][x] = sky_color[sky_color_decision];
                        colorbg_map[y][x] = sky_color[sky_color_decision];
                    }
                } else {
                    map[y][x] = ' '; // else assign blank map symbol and default color
                    color_map[y][x] = sky_color[sky_color_decision];
                    colorbg_map[y][x] = sky_color[sky_color_decision];
                }
            }
        }

        return new ArrayList<Object>() { // Return both color and ascii map
            {
            add(map);
            add(color_map);
            add(colorbg_map);
            }
        };
    }

    private static void render_frame(char[][] map, ANSI[][] color_map, ANSI[][] colorbg_map){
        System.out.print(ANSI.ESC + "?25l"); // Set invisible cursor

        for(int y = mode_y; y>= 1; y--){ // draw out the game map to defined screen bounds
            for(int x = 1; x<= mode_x; x++){ // render out the character
                LCurses.draw(color_map[y - 1][x - 1].toString() + colorbg_map[y - 1][x - 1].toString() + map[y - 1][x - 1] + "", ANSI.WHT, x, y);
            }
        }
    }

    private static void render_interface(int pos_x, int pos_y, int ivar_pos_x, int ivar_pos_y) { // render the ufo input screen
        int tbox_height = 3; // how many lines is the text box
        LCurses.draw(
            ANSI.YLW +
            "=== FUN MODE ====================" + ANSI.ESC + ANSI._ND +
            "=   INFO: POS X: " + String.format("%03d", ivar_pos_x) + ", Y: " + String.format("%02d", ivar_pos_y) + "     =" + ANSI.ESC + ANSI._ND +
            ANSI.MAG_I + "> Pyramid will be " + (mode_y - (ivar_pos_y + 4)) + " blocks tall." + ANSI.ESC + ANSI._ND + ANSI.YLW +
            "input>>                          " + ANSI.ESC + 25 + ANSI._LT + ANSI.ESC + ANSI.C_V + ANSI.YLW,
            ANSI.YLW,
            ANSI.BLK_BG,
            pos_x,
            pos_y - tbox_height); // Render text box
    }

    private static void render_textbox(String[] words, int x, int y) throws Exception { // TEXT BOXES MUST BE EQUAL SIZE COLUMNS PER ROW
        System.out.print(ANSI.ESC.toString() + ANSI.C_H); // Hide the cursor to render out screen

        for(int ydraw = 0; ydraw < words.length; ydraw++){ // while less than the amount of rows
            for(int xdraw = 0; xdraw < words[0].length(); xdraw++){ // and less than the amount of columns
                LCurses.draw(words[ydraw], ANSI.RST, x, y + ydraw);
            }
        }
        if (System.in.read() == 0) { // wait for input
            System.out.print(ANSI.CLR); // clear screen
            System.out.print(ANSI.ESC.toString() + ANSI.C_V); // reveal the cursor
            return; // exit to main
        }
    }

    // Player sprite info. Used for the render_player and bounding box stuff
    public static int p_sprite_w = 13;
    public static int p_sprite_h = 3;
    public static int p_sprite_normal = 6;

    private static void render_player(int old_x, int old_y, int new_x, int new_y){ // player render method
        System.out.print(ANSI.ESC + "?25l"); // Set invisible cursor
        LCurses.pos(new_x, new_y);
        LCurses.draw(" ___/"+ANSI.GRN_I+"o_o"+ANSI.WHT+"\\___ ", ANSI.WHT, new_x + 1, new_y + 1);
        LCurses.draw("/   '---'   \\", ANSI.WHT, new_x + 1 , new_y + 2);
        LCurses.draw("'--_______--'", ANSI.WHT, new_x + 1, new_y + 3);
    }

    // render interface

    public static void game_draw_test() throws Exception { // array population for drawing test
        int[][] space = new int[mode_y][mode_x];
        for(int y = 1; y<= mode_y; y++){ // begin drawing below the text box
            for(int x = 1; x<= mode_x; x++){ // x begins from left up to console window maximum
                space[y - 1][x - 1] = new Random().nextInt(9);
            }
        }
        ANSI[] sky_color = {ANSI.BLU_BG_I, ANSI.BLU_BG, ANSI.BLK_BG_I, ANSI.BLK_BG};
        for(int y = mode_y; y>= 1; y--){
            for(int x = 1; x<= mode_x; x++){
                int sky_color_decision = new Random().nextInt(sky_color.length);
                LCurses.draw(space[y - 1][x - 1] + "", sky_color[sky_color_decision], x, y);
            }
        }
        LCurses.draw("// Press [ENTER] to continue >> ", ANSI.RED, 1, 4); // Signify that the program has completed
        if (System.in.read() == 0) { // wait for input
            System.out.print(ANSI.CLR); // clear screen
            return; // exit to main
        }
    }
}