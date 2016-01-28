package projectdnimoy;

import javafx.scene.paint.Color;

public interface ConstantsInterface {
    int MECH_ID = 0, WAVES_ID = 1, ENM_ID = 2;
    int width = 800, height = 600;
    String[] SUBJECTS = new String[]{"Mechanics", "Waves and Optics", "Electricity and Magnetism"};
    String[][] TOPICS = new String[][]{{"2D Ballistics", "Collisions and Momentum"},
                                        {"Geometrical Optics", "Pendulum Oscillation"},
                                        {"Forces of Point Charges", "Capacitance and Resistance"}};
    Color[] SUBJECT_COLORS = new Color[]{Color.BLUE, Color.YELLOW, Color.RED};
    String IN_CONS_TITLE = "In Construction";
    String IN_CONS_MESSAGE = "Section currently in construction. You will now be returned to the main menu.";
    String HELP_TEXT = "Help", DONE_TEXT = "Done", RESET_TEXT = "Reset", PLAY_TEXT = "Play", PAUSE_TEXT = "Pause";
}
