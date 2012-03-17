package org.team3309;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Vinnie Magro
 */
public class EZ0 {
	
    private AnalogChannel ac;
    
    private double[] lastValues = new double[5];
    private int lastPos = 0;
    
    public EZ0(int channel){
    	ac = new AnalogChannel(channel);
    }
    
    public EZ0(int slot, int channel){
        ac = new AnalogChannel(slot, channel);
        for(int i=0; i<lastValues.length; i++){
        	lastValues[i] = 10000;
        }
    }
    
    public double getVoltage(){
        return ac.getVoltage();
    }
    public double getInches(){
        double voltage = ac.getVoltage();
        double mv = voltage*1000;
        double inches = mv/9.8;
        /*if(lastPos == 5)
        	lastPos = 0;
        lastValues[lastPos++] = inches;
        double avg = 0;
        for(int i=0; i<lastValues.length; i++){
        	avg += lastValues[i];
        }
        avg = avg/lastValues.length;
        return avg;*/
        return inches;
    }
}
