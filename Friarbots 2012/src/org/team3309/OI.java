/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team3309;

import edu.wpi.first.wpilibj.Joystick;
import java.util.Vector;

public class OI {
    
    private static OI instance 	= null;
    private Joystick[] joysticks = new Joystick[4];
    
    public OI() {
    }
    
    public static OI getInstance() {
    if (instance == null) 
            instance = new OI();
        return instance;
    }
    
    public Joystick getJoystick(int id){
        if (id > joysticks.length)
            throw new IllegalArgumentException("The id of the joystick requested is greater than the maximum" +
                    "number of joysticks supported");
        else{
            if(joysticks[id] == null) joysticks[id] = new Joystick(id);
            return joysticks[id];
        }
    }
}

