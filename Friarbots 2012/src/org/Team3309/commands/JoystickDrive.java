/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Team3309.commands;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

import org.Team3309.*;
import org.Team3309.subsystems.DriveSubsystem;
/**
 *
 * @author Vincente
 */
public class JoystickDrive extends Command {

	private DriveSubsystem drive 	= null;
	private Joystick stick 			= null;
	private boolean finished		= false;
	Accelerometer ac 				= null;
	BalanceCommand balance			= null;
	Button balanceButton			= null;
	boolean balancing 				= false;
	double initialAngle 			= 0;

	public JoystickDrive(int joystickID) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		drive = DriveSubsystem.getInstance();
		//requires(drive);
		stick = OI.getInstance().getJoystick(joystickID);
		balanceButton = stick.getRawButton(12);
		balance = new BalanceCommand();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drive.mecanumDrive(stick.getX(), stick.getY(), stick.getTwist());
		
		//if button 12 is hit, it will balance
		balanceButton.whenPressed(balance);
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
