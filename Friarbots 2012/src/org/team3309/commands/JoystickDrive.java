/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.commands;

import org.team3309.OI;
import org.team3309.RobotMap;
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

	private DriveSubsystem drive = null;
	private Joystick stick = null;

	private boolean finished = false;

	public Gyro gyro;

	public JoystickDrive(int joystickID) {
		drive = DriveSubsystem.getInstance();

		requires(drive);

		stick = OI.getInstance().getJoystick(joystickID);

		gyro = Gyro.getInstance(1, RobotMap.DRIVE_GYRO);

		driveGyroResetButton = new JoystickButton(stick, 3);
	}

	public Joystick getJoystick() {
		return stick;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveGyroResetButton.whenPressed(new Command() {
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
		// gyro.updateDesiredHeading(stick.getTwist());
		drive.mecanumDrive(-stick.getX(), -stick.getY(), stick.getTwist(),
				gyro.getAngle());
		if(stick.getRawButton(10))
			gyro.disable();
		if(stick.getRawButton(11))
			gyro.enable();
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
