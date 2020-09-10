package HowToUseSliderModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class DefaultPanel extends JPanel implements PropertyChangeListener, ChangeListener, ActionListener {
    public Unit[] array;
    public String title;
    public JFormattedTextField field;
    public JSlider slider;
    public JComboBox comboBox;
    public TopModel sliderModel;
    public LengthConverter converter;   // for retrieving individual multiplier from two different panels
    // constructor
    DefaultPanel(Unit[] array, String title, TopModel model, LengthConverter myConverter) {
        super(new BorderLayout());
        this.array = array;
        this.title = title;
        sliderModel = model;
        converter = myConverter;

       // setPreferredSize(new Dimension(300, 100));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),BorderFactory.createEmptyBorder(5,5,5,5)));

        // add JTextField, JSlider and JComboBox to the panel
        field = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        field.setValue(sliderModel.getDoubleValue()); // set value for textField from reading the slider
        field.addPropertyChangeListener(this);

        // create a slider with HowToUseSliderModel.TopModel
        slider = new JSlider(sliderModel);
        slider.addChangeListener(this);

        comboBox = new JComboBox();
        for (int i = 0; i < array.length ; i++) {
            comboBox.addItem(array[i].description);
        }
        comboBox.setSelectedIndex(0);
        sliderModel.setMultiplier(array[0].multiplier);
        comboBox.addActionListener(this);

        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.add(field);
        leftPane.add(slider);
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
       // rightPane.setPreferredSize(new Dimension(120,0));   // this line or line 36 both can make the two combobox aligned vertically
        rightPane.add(comboBox);
        rightPane.add(Box.createHorizontalStrut(120));

        add(leftPane, BorderLayout.LINE_START);
        add(rightPane, BorderLayout.LINE_END);
    }
    public double getMultiplierfromModel() {
        return sliderModel.getMultiplier();
    }

    @Override // capture textField change
    public void propertyChange(PropertyChangeEvent evt) {
        // when textField value changed, it controls the model's double value
        // "value" is the propertyName for the textFiled
        if ("value".equals(evt.getPropertyName())) {
            Number number = (Number) evt.getNewValue();
            sliderModel.setDoubleValue(number.doubleValue());
        }

    }

    @Override // slider change event
    public void stateChanged(ChangeEvent e) {
        // use model event to control textField display, the ChangeEvent e is not used
        int min = sliderModel.getMinimum();
        int max = sliderModel.getMaximum();
        System.out.println("max is stateChanged " + max);
        double value = sliderModel.getDoubleValue();

        // format the textField
        NumberFormatter formatter = (NumberFormatter) field.getFormatter();
        formatter.setMinimum(min);
        formatter.setMinimum(max);

        // set textField value
        field.setValue(value);

    }

    @Override // comboBox event
    public void actionPerformed(ActionEvent e) {
        // retrieve individual multiplier from two panels
        // this can be done only through the HowToUseSliderModel.LengthConverter class
        // get index for selection
        int index = comboBox.getSelectedIndex();
        //set multiplier
        sliderModel.setMultiplier(array[index].multiplier);
        System.out.println("in action Performed multiplier is " + sliderModel.getMultiplier());

        // use HowToUseSliderModel.LengthConverter.java for the multiplier
        converter.resetMaxOnTopSlider(false);
    }
}
