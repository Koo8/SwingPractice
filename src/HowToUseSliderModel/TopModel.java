package HowToUseSliderModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Based on the source code for DefaultBoundedRangeModel,
 * this class stores its value as a double, rather than
 * an int.  The minimum value and extent are always 0.
 **/

public class TopModel implements BoundedRangeModel{
    // these two fields are copied from DefaultBoundedRangeModel
    protected ChangeEvent changeEvent = null;
    // the listeners waiting for model changes
    protected EventListenerList listenerList = new EventListenerList();

    //Initializes value, extent, minimum and maximum.
    public int max = 10000;
    public int min = 0;
    public double value = 0.0;
    public int extent = 0;
    public boolean isAdjusting = false;

    public double multiplier = 1.0;

    // constructor
    TopModel() {
        //Initializes all of the properties with default values.
    }

    public double getMultiplier(){
        return multiplier;
    }

    public void setMultiplier(double multiplier)
    {
        this.multiplier = multiplier;
        fireStateChanged();
    }

    @Override
    public int getMinimum() {
        return min;
    }

    @Override
    public void setMinimum(int newMinimum) {
        // do not want to change minimum
         //setRangeProperties(value, extent, newMinimum, max, isAdjusting);
    }

    @Override
    public int getMaximum() {
        return max;
    }

    @Override
    public void setMaximum(int newMaximum) {
        setRangeProperties(value,extent,min, newMaximum,isAdjusting);
    }

    @Override
    public int getValue() {
        return (int)getDoubleValue();
    }

    public double getDoubleValue() {
        return value;
    }

    @Override
    public void setValue(int newValue) {
       setDoubleValue((double)newValue);
    }

    public void setDoubleValue(double newValue) {
        setRangeProperties(newValue,extent, min, max, isAdjusting);
    }

    @Override
    public void setValueIsAdjusting(boolean b) {
        setRangeProperties(value, extent, min, max, b);
    }

    @Override
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    @Override
    public int getExtent() {
        return extent;
    }

    @Override
    public void setExtent(int newExtent) {
        // don't want to change extent
       // setRangeProperties(value,newExtent,min, max,isAdjusting );
    }

    @Override
    public void setRangeProperties(int value, int extent, int min, int max, boolean adjusting) {
        setRangeProperties((double)value, extent, min, max, adjusting);
    }

    public void setRangeProperties(double newValue, int nochangeextent, int nochangemin, int newMax, boolean newAdjusting) {
        if(newMax<=min) newMax = min +1;
        if(Math.round(newValue) > newMax) // this code is important, to make the max of both slider not >10000
            newValue = newMax;

        if(newValue != value) {
            value = newValue;
            fireStateChanged();
        }
        if(newMax != max) {
            System.out.println("in setRangeproperty, in newMax != max ");
            max = newMax;
            fireStateChanged();
        }
        if(isAdjusting != newAdjusting) {
            isAdjusting = newAdjusting;
            fireStateChanged();
        }
    }
    // the last 3 methods are copied from DefaultBoundedRangeModel
    @Override
    public void addChangeListener(ChangeListener x) {
        listenerList.add(ChangeListener.class, x);
    }

    @Override
    public void removeChangeListener(ChangeListener x) {
        listenerList.remove(ChangeListener.class, x);
    }
    // from DefaultBoundedRangeModel
    public void fireStateChanged(){

        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }

    }

}
