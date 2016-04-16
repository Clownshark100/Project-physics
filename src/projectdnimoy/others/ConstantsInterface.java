package projectdnimoy.others;

import javafx.scene.paint.Color;

public interface ConstantsInterface {
    byte MECH_ID = 0, WAVES_ID = 1, ENM_ID = 2;
    short width = 800, height = 600, MAX_THREAD = 290, MAX_ANGLE = 100, LENGTH_MOD = 200, STRAIGHT_ANGLE = 90;
    byte ZERO = 0, ONE = 1, HALF = 2, QUARTER = 4;
    double f=30 , pInitial = 160, lensHeight = 250, lensPosX = 275, lensPosY = 100
            ,objPostY = 120, imPosY = 240, inverted = 180, maxP = 110 ; 
    double bWidth = 200, bHeight = 50, bFont =14, exitFont = 20 ,exitPosX = 407, exitPosY = 430;       
    int RCINITIAL_THETA = 30, CANNON_FACTOR1 = 40, CANNON_FACTOR2 = 60, CANNON_POSITION = 32, CANNON_POSITION2 = 16, SMOKE_X = 27, SMOKE_Y = 80, PENDULUM_FACTOR = 10;
    
    String[] SUBJECTS = new String[]{" MECHANICS ", "WAVES AND OPTICS ", "ELECTRICITY AND\n  MAGNETISM "};
    String[][] TOPICS = new String[][]{{"2D Ballistics", "Collisions and\n Momentum"},
                                        {"Geometrical\n Optics", "Pendulum\n Oscillation"},
                                        {"Point Charges", "Magnetic\n Field"}};
    Color[] SUBJECT_COLORS = new Color[]{Color.BLUE, Color.YELLOW, Color.RED};
    String IN_CONS_TITLE = "In Construction", IN_CONS_MESSAGE = "Section currently in construction. You will now be returned to the main menu.";
    String ADIOS_MESSAGE = "Thank you for trying Project DNIMOY!", ADIOS_TITLE = "Adios, dearest user!";
    String HELP_TEXT = "Help", DONE_TEXT = "Done", RESET_TEXT = "Reset", PLAY_TEXT = "Play", PAUSE_TEXT = "Pause";
    short[] xSubPosition = new short[]{100 , 320 , 540};
    short[] ySubPosition = new short[]{10 , 130 , 260};
    short chartHeight = 200, paneWidth = width, paneHeight = height-chartHeight;
    double G = 9.80665, K = 8.987551E9;
    byte UPDATE_FRAMERATE = 25;
    short CHART_FRAMERATE = 166;
    String TIME_AXIS = "Time (s)", DIST_AXIS = "Horizontal Distance (px)", HEIGHT_AXIS = "Height (px)", ANGLE_AXIS = "Angle (degrees)", VEL_AXIS = "Velocity (px/s)";
    
   
}
