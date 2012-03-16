package org.team3309;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Vinnie Magro
 */
public class EZ0 {
    private AnalogChannel ac;
    
    public EZ0(int channel){
    	ac = new AnalogChannel(channel);
    }
    
    public EZ0(int slot, int channel){
        ac = new AnalogChannel(slot, channel);
    }
    
    public double getVoltage(){
        return ac.getVoltage();
    }
    public double getInches(){
        double voltage = ac.getVoltage();
        double mv = voltage*1000;
        double inches = mv/9.8;
        return inches;
    }
}
