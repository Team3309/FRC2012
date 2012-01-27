/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.commands;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

import org.team3309.*;
import org.team3309.subsystems.DriveSubsystem;
/**
 *
 * @author Vincente
 */
public class JoystickDrive extends Command {

	private DriveSubsystem drive 	= null;
	private Joystick stick 			= null;
	private boolean finished		= false;

	public JoystickDrive(int joystickID) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		drive = DriveSubsystem.getInstance();
		//requires(drive);
		stick = OI.getInstance().getJoystick(joystickID);
	}
	
	public Joystick getJoystick(){
		return stick;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drive.mecanumDrive(stick.getX(), stick.getY(), stick.getTwist());

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
