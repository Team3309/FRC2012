/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3309;


import org.team3309.commands.BalanceCommand;
import org.team3309.commands.JoystickDrive;
import org.team3309.commands.XboxDrive;
import org.team3309.pid.SpeedJaguar;
import org.team3309.properties.Properties;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.PneumaticsSubsystem;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
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

	//Declare Subsystems
	//DriveSubsystem drive;
	PneumaticsSubsystem pneumatics;

	//Declare Commands
	Command autonomousCommand;    
	BalanceCommand balanceCommand;
	//JoystickDrive driveCommand;
	//XboxDrive driveCommand;

	//Declare Buttons
	JoystickButton balanceButton;
	JoystickButton balanceCancelButton;

	
	SpeedJaguar speedJag = null;

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

		// initialize all subsystems here.
		//drive = DriveSubsystem.getInstance();
		//		pneumatics = PneumaticsSubsystem.getInstance();
		//		pneumatics.start();

		//initialize commands
		balanceCommand = new BalanceCommand();
		
		//driveCommand = new JoystickDrive(1);
		//driveCommand = new XboxDrive(1);

		speedJag = new SpeedJaguar(RobotMap.JAG_BACK_LEFT, RobotMap.ENCODER_BACK_LEFT_A, RobotMap.ENCODER_BACK_LEFT_B);
	}

	public void disabledInit(){
		balanceCommand.cancel();
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
		//driveCommand.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}

