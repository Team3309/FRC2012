/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3309;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.team3309.commands.BalanceCommand;
import org.team3309.commands.JoystickDrive;
import org.team3309.commands.TeleopControl;
import org.team3309.subsystems.DriveSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class IterativeTemplate extends IterativeRobot {
	
    Command autonomousCommand;    
    Command balanceCommand;
    Command driveCommand;
    
    JoystickButton balanceButton;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period

        // create the instance of the operator interface class
        // this will associate all the buttons with the appropriate commands
        OI.getInstance();

        // initialize all subsystems here.
        DriveSubsystem.getInstance();
        
        balanceButton = new JoystickButton(OI.getInstance().getJoystick(1), 1);
        
        driveCommand = new JoystickDrive(1);
        balanceCommand = new BalanceCommand();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	balanceButton.whenPressed(balanceCommand);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
    }
}
