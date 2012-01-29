/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.Gyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 * @author Vincente
 */
public class JoystickDrive extends Command {

	private JoystickButton driveGyroResetButton;
	private JoystickButton balanceGyroResetButton;
	
	
	private DriveSubsystem drive 	= null;
	private Joystick stick 			= null;
	private boolean finished		= false;
	
	public Gyro gyro;
	public Gyro bgyro;

	public JoystickDrive(int joystickID) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		drive = DriveSubsystem.getInstance();
		requires(drive);
		//requires(drive);
		stick = OI.getInstance().getJoystick(joystickID);
		gyro = Gyro.getInstance(1, 2);
		bgyro = Gyro.getInstance(1, 1);
		driveGyroResetButton = new JoystickButton(stick, 3);
		balanceGyroResetButton = new JoystickButton(stick, 5);
	}
	
	public Joystick getJoystick(){
		return stick;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveGyroResetButton.whenPressed(new Command(){

			protected void initialize() {
				
			}

			protected void execute() {
				gyro.reset();
			}

			protected boolean isFinished() {
				return true;
			}

			protected void end() {
				
			}

			protected void interrupted() {
				
			}
			
		});
		balanceGyroResetButton.whenPressed(new Command(){
			protected void initialized() {
			}
			protected void execute() {
				bgyro.reset();
			}
			protected void end(){
			}
			protected void interrupted(){
			}
			protected void isFinsihed(){
			}
			protected void initialize() {
			}
			protected boolean isFinished() {
				return false;
			}
			
		});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//gyro.updateDesiredHeading(stick.getTwist());
		drive.mecanumDrive(stick.getX(), stick.getY(), stick.getTwist(), gyro.getAngle());

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
