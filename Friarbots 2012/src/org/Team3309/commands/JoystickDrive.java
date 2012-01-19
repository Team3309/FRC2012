/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Team3309.commands;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.Team3309.*;
import org.Team3309.subsystems.DriveSubsystem;
import org.Team3309.subsystems.Gyro;
/**
 *
 * @author Vincente
 */
public class JoystickDrive extends Command {

	private DriveSubsystem drive 	= null;
	private Joystick stick 			= null;
	private boolean finished		= false;
	Accelerometer ac 				= null;
	Gyro gyro 						= null;
	boolean balancing 				= false;
	double initialAngle 			= 0;

	public JoystickDrive(int joystickID) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		drive = DriveSubsystem.getInstance();
		requires(drive);
		stick = OI.getInstance().getJoystick(joystickID);
		gyro = Gyro.getInstance();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drive.mecanumDrive(stick.getX(), stick.getY(), stick.getTwist());
		
		//if button 12 is hit, it will balance
		if(stick.getRawButton(12)){
			BalanceCommand balance = new BalanceCommand();
			balance.start();
		}
	}


	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
