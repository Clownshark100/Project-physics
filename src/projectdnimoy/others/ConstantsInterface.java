package projectdnimoy.others;

import javafx.scene.paint.Color;

public interface ConstantsInterface {
    int MECH_ID = 0, WAVES_ID = 1, ENM_ID = 2;
    int width = 800, height = 600;
    
    String[] SUBJECTS = new String[]{" MECHANICS ", "WAVES AND OPTICS ", "ELECTRICITY AND\n  MAGNETISM "};
    String[][] TOPICS = new String[][]{{"2D Ballistics", "Collisions and\n Momentum"},
                                        {"Geometrical\n Optics", "Pendulum\n Oscillation"},
                                        {"Point Charges", "Capacitance and\n Resistance"}};
    Color[] SUBJECT_COLORS = new Color[]{Color.BLUE, Color.YELLOW, Color.RED};
    String IN_CONS_TITLE = "In Construction", IN_CONS_MESSAGE = "Section currently in construction. You will now be returned to the main menu.";
    String ADIOS_MESSAGE = "Thank you for trying Project DNIMOY!", ADIOS_TITLE = "Goodbye, dearest user!";
    String HELP_TEXT = "Help", DONE_TEXT = "Done", RESET_TEXT = "Reset", PLAY_TEXT = "Play", PAUSE_TEXT = "Pause";
    double[] xSubPosition = new double[]{100 , 320 , 540};
    double[] ySubPosition = new double[]{10 , 130 , 260};
    int paneWidth = width, paneHeight = height-200;
    double G = 9.80665, K = 8.987551E9;
    
   
}
