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

	private JoystickButton gyroResetButton;
	
	private DriveSubsystem drive 	= null;
	private Joystick stick 			= null;
	private boolean finished		= false;
	
	private Gyro gyro;

	public JoystickDrive(int joystickID) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		drive = DriveSubsystem.getInstance();
		//requires(drive);
		stick = OI.getInstance().getJoystick(joystickID);
		gyro = Gyro.getInstance(1, 2);
		
		gyroResetButton = new JoystickButton(stick, 3);
	}
	
	public Joystick getJoystick(){
		return stick;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		gyroResetButton.whenPressed(new Command(){

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
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		gyro.updateDesiredHeading(stick.getTwist());
		drive.mecanumDrive(stick.getX(), stick.getY(), gyro.getTwistRate(), gyro.getAngle());

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
