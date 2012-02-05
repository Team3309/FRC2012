/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3309;


import org.team3309.commands.BalanceCommand;
import org.team3309.commands.JoystickDrive;
import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
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

	Command autonomousCommand;    
	BalanceCommand balanceCommand;
	JoystickDrive driveCommand;
	DriveSubsystem drive;

	JoystickButton balanceButton;
	JoystickButton balanceCancelButton;
	

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

		balanceButton = new JoystickButton(OI.getInstance().getJoystick(1), 12);
		balanceCancelButton = new JoystickButton(OI.getInstance().getJoystick(1), 11);

		driveCommand = new JoystickDrive(1);
		drive = DriveSubsystem.getInstance();
		balanceCommand = new BalanceCommand();
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
		driveCommand.start();
		balanceButton.whenPressed(balanceCommand);

		balanceCancelButton.whenPressed(new Command(){

			protected void initialize() {

			}

			protected void execute() {
				balanceCommand.cancel();
			}

			protected boolean isFinished() {
				return true;
			}

			protected void end() {

			}

			protected void interrupted() {

			}

		});
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		/*
		if(!balancing){
			driveCommand.start();
		}
		//System.out.println(gyro.getAngle());
		if(joystick.getRawButton(12)){
			balancing = true;
			initialAngle = gyro.getAngle();
		}
		while(balancing){
			//System.out.println(gyro.getAngle());
			if(Math.abs(initialAngle - gyro.getAngle()) < (initialAngle - 1)){
				drive.mecanumDrive(0, -.34, 0, 0);
			}
			else{
				Timer.delay(.3);
				drive.mecanumDrive(0, .34, 0,0);
				if(Math.abs(initialAngle - gyro.getAngle()) < 2)
					balancing = false;
			}
			if(joystick.getRawButton(11))
				balancing = false;	 
		}*/
	}
}

