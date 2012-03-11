/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3309;

import org.team3309.commands.ButtonCommands;
import org.team3309.commands.JoystickDrive;
import org.team3309.properties.Properties;
import org.team3309.subsystems.ElevatorSubsystem;
import org.team3309.subsystems.PneumaticsSubsystem;
import org.team3309.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class IterativeTemplate extends IterativeRobot {

	Joystick stick;
	Joystick shooterStick;
	
	// Declare Subsystems
	// DriveSubsystem drive;
	PneumaticsSubsystem pneumatics;

	// Declare Commands
	Command autonomousCommand;
	
	JoystickDrive driveCommand;
	
	// Declare Buttons
	JoystickButton deployUbarButton;
	JoystickButton retractUbarButton;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		// instantiate the command used for the autonomous period

		// create the instance of the operator interface class
		// this will associate all the buttons with the appropriate commands
		OI.getInstance();
		Properties.getInstance();
		PneumaticsSubsystem.getInstance();

		// initialize all subsystems here.
		// drive = DriveSubsystem.getInstance();

		stick = OI.getInstance().getJoystick(1);
		shooterStick = OI.getInstance().getJoystick(2);
		
		// initialize commands
		driveCommand = new JoystickDrive(1);
		
		//Buttons for joystick
		deployUbarButton = new JoystickButton(stick, 6);
		retractUbarButton = new JoystickButton(stick, 4);	
	}

	public void disabledInit() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {		
		System.out.println("In teleop Init");
		driveCommand.start();
		System.out.println("Started Drive");
		deployUbarButton.whenPressed(ButtonCommands.deployUbar);
		retractUbarButton.whenPressed(ButtonCommands.retractUbar);	
		System.out.println("Started Ubar Commands");
		ButtonCommands.manualTurret.start();
		System.out.println("Started Manual Turret Command");
		//ButtonCommands.autoElevate.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//System.out.println("teleopPeriodic");
		//ShooterSubsystem.getInstance().setRPM(500);
		ShooterSubsystem.getInstance().setPercentVbus(shooterStick.getY());
		if(shooterStick.getRawButton(6))
			ElevatorSubsystem.getInstance().manualElevate(1);
		else if(shooterStick.getRawButton(7))
			ElevatorSubsystem.getInstance().manualElevate(-1);
		else
			ElevatorSubsystem.getInstance().manualElevate(0);
	}
}
