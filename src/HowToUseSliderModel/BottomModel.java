package HowToUseSliderModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BottomModel extends TopModel implements ChangeListener {

    TopModel topModel;

    // constructor
    public BottomModel(TopModel mtopModel) {

        topModel = mtopModel;
        topModel.addChangeListener(this);

    }
    @Override
    public void stateChanged(ChangeEvent e) {
          fireStateChanged();
    }

     // override methods from sliderModel

    @Override
    public int getMaximum() {
        return (int) (topModel.getMaximum()*topModel.getMultiplier()/this.getMultiplier());
    }

//    @Override
//    public void setMaximum(int newMaximum) {
//        topModel.setMaximum((int) (newMaximum*(this.getMultiplier()/topModel.getMultiplier())));
//    }

    @Override
    public int getValue() {
        return (int)getDoubleValue();
    }

    public double getDoubleValue() {
        return topModel.getDoubleValue()* topModel.getMultiplier()/this.getMultiplier();
    }

    @Override
    public void setValue(int newValue) {
        setDoubleValue((double)newValue);
    }

    public void setDoubleValue(double newValue) {
        // used when use bottom slider to control
        topModel.setDoubleValue(newValue * this.getMultiplier()
                / topModel.getMultiplier());
    }
    @Override
    public int getExtent() {
        return super.getExtent();
    }

    @Override
    public void setExtent(int newExtent) {
        super.setExtent(newExtent);
    }

//    @Override
//    public void setRangeProperties(int newValue, int nochangeextent, int nochangemin, int newMax, boolean newAdjusting) {
//        double factor = this.getMultiplier()/ topModel.getMultiplier();
//        setRangeProperties(newValue*factor, extent, min, (int) (newMax*factor),newAdjusting);
//    }
}
