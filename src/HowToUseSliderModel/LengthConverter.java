package HowToUseSliderModel;

import javax.swing.*;
import java.awt.*;

/**
 * This app makes a converter,
 * two JTextField that show the value of JSlider
 * two JComboBox that show imperial and metric length units
 * drag one slider will change the other slider value
 * highlight: use one model to control both sliders value
 * The important thing for this program is ensuring that only one model
 * controls the value of the data, by deferring to the top slider's model.
 * The bottom slider's model forwards all data queries to the top slider's model .
 * Each text field is kept in sync with its slider, and vice versa,
 * by event handlers that listen for changes in value.
 * Care is taken to ensure that the top slider's model has the final say about
 * what distance is displayed.
 */

/**
 * One problem not solved, the displayed value of textfield may have many digits after decimal
 * compare with oracle model tutorial converter.java for solution
 */

public class LengthConverter extends JPanel {
    public Unit[] metricUnit, imperialUnit;
    public DefaultPanel topPanel, bottompanel;
    public TopModel topModel;

    //constructor
    public LengthConverter() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        metricUnit = new Unit[3];
        metricUnit[0] = new Unit("Centermeters", 0.01);
        metricUnit[1] = new Unit("Meters", 1);
        metricUnit[2] = new Unit("Kilometers", 1000.0);

        imperialUnit = new Unit[4];
        imperialUnit[0] = new Unit("Inches", 0.0254);
        imperialUnit[1] = new Unit("Feet", 0.305);
        imperialUnit[2] = new Unit("Yards", 0.914);
        imperialUnit[3] = new Unit("Miles", 1613.0);

        topModel = new TopModel();

        topPanel = new DefaultPanel(metricUnit, "Metric System", topModel, this);
        bottompanel = new DefaultPanel(imperialUnit, "U.S.System", new BottomModel(topModel), this);

        add(Box.createVerticalStrut(5));
        add(topPanel, BorderLayout.PAGE_START);
        add(Box.createVerticalStrut(5));
        add(bottompanel, BorderLayout.CENTER);
        setOpaque(true);

        resetMaxOnTopSlider(true);
    }

    public void resetMaxOnTopSlider(boolean knobToMax) {
        int maximum = 10000;

        // when bottom slider max is over 10000
        double topMultiplier = topPanel.getMultiplierfromModel(); // multiplier is set through the comboBox event in actionPerformed
        double bottomMultiplier = bottompanel.getMultiplierfromModel();
        System.out.println("in resestMax... top multiplier is "+ topMultiplier + ", bottom multiplier is " + bottomMultiplier);

        if(topMultiplier > bottomMultiplier) {
            maximum = (int) (maximum * bottomMultiplier/topMultiplier);
        }

        topModel.setMaximum(maximum); // bottomModel maximum is set by sliderModel.getMaxium

        if (knobToMax) {
            topModel.setDoubleValue(maximum);
        }
    }

    public static void GUI() {
        //initLookAndFeel(); // check Converter.java from oracle for setting up look and feel
        //https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#Converter
        JFrame frame = new JFrame("Length Converter DEMO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.setContentPane(panel);
        panel.add(new LengthConverter());
        frame.pack();
        frame.setVisible(true);
    }

//    private static void initLookAndFeel() {
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LengthConverter::GUI);
    }
}
