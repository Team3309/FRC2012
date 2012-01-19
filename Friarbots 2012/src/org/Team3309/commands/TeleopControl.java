/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Team3309.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Vincente
 */

//This is not used, it might be needed later if we add a second joystick.
public class TeleopControl extends CommandGroup {
    
    public TeleopControl() {
        // Add Commands here:
        // e.g. add(new Command1());
        //      add(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addChild()
        // e.g. add(new Command1());
        //      addChild(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        //add(new JoystickDrive(1));
    }
}
