/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.commands;

import org.team3309.OI;
import org.team3309.XboxMap;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.Gyro;
import org.team3309.subsystems.ShooterSubsystem;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Vincente Ciancio
 * 
 * Left  Joystick for X and Y movement
 * Left  Joystick Button for resetting gyro
 * Right Joystick for twist 
 */
public class XboxDrive extends Command {

	private JoystickButton driveGyroResetButton;
	public Gyro gyro;

	private DriveSubsystem drive 		= null;
	private ShooterSubsystem shooter 	= null;
	private Joystick controller 		= null;

	private boolean finished = false;
	private JoystickButton retractUbarButton;
	private JoystickButton deployUbarButton;

	public XboxDrive(int joystickID) {
		drive = DriveSubsystem.getInstance();
		requires(drive);
		controller = OI.getInstance().getJoystick(joystickID);
		gyro = Gyro.getInstance(1, 2);
		driveGyroResetButton = new JoystickButton(controller,
				XboxMap.B_LEFT_STICK);
		shooter = ShooterSubsystem.getInstance();
	}

	public Joystick getJoystick() {
		return controller;
	}

	protected void initialize() {
		driveGyroResetButton.whenPressed(ButtonCommands.driveGyroReset);
	}

	protected void execute() {
		/* Following Algorithm Corrects loose Joystick on Xbox controller with
		 * cubic functions
		 */
		
		//Drive X Axis - Left Controller
		double x = controller.getRawAxis(XboxMap.A_LEFT_X);
		if (Math.abs(x) < .05)
			x = 0;
		x -= .05;
		x = MathUtils.pow(x, 3);

		//Drive Y Axis - Left Controller
		double y = controller.getRawAxis(XboxMap.A_LEFT_Y);
		if (Math.abs(y) < .05)
			y = 0;
		y = MathUtils.pow(y, 3);
		y -= .05;

		//Twist Y Axis - Right Controller
		double twist = controller.getRawAxis(XboxMap.A_RIGHT_X);
		if (Math.abs(twist) < .05)
			twist = 0;
		twist = MathUtils.pow(twist, 3);
		twist -= .05;

		//Move Motors
		if(controller.getRawButton(XboxMap.B_LEFT_STICK))
			drive.mecanumDrive(x*.7, y*.7, twist*.7,0);
		else
			drive.mecanumDrive(x, y, twist, gyro.getAngle());
		
		
		if(controller.getRawButton(XboxMap.B_START))
			gyro.reset();
	}
	
	protected boolean isFinished() {
		return finished;
	}
	protected void end() {
	}
	protected void interrupted() {
	}
}
