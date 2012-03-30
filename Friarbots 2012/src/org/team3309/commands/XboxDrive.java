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
	private double angle = 0;
	
	public static final float fourtyFiveDegreesInRadians = (float) Math.toRadians(45);
	public static final float oneHundredThirtyFiveDegreesInRadians = (float) Math.toRadians(135);

	public XboxDrive(int joystickID) {
		drive = DriveSubsystem.getInstance();
		requires(drive);
		controller = OI.getInstance().getJoystick(joystickID);
		gyro = Gyro.getInstance(1, 2);
		driveGyroResetButton = new JoystickButton(controller,
				XboxMap.B_Y);
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
		x = MathUtils.pow(x, 3);
		x -= .05;
		
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
		twist = twist*-1;
		
		if (x!= 0 && y != 0) {
			double theta = MathUtils.atan(y / x);
			double scale = 1; //Use 1 if something goes wrong
			if (Math.abs(theta) >= fourtyFiveDegreesInRadians && Math.abs(theta) <= oneHundredThirtyFiveDegreesInRadians) {
				scale = Math.abs(1 / Math.sin(theta));
			} else {
				scale = Math.abs(1 / Math.cos(theta));
			}
			x *= scale;
			y *= scale;
		}

		
		//Magnitude
		double magnitude = controller.getMagnitude();
		if(magnitude < .05)
			magnitude = 0;
		magnitude = MathUtils.pow(magnitude, 3);
		magnitude -= .05;
		magnitude = magnitude * -1;
		
		//Angle
		angle = -(gyro.getAngle() - controller.getDirectionDegrees());
		
		
		//Move Motors
		if(controller.getRawButton(XboxMap.B_LEFT_STICK))
			drive.mecanumDrivePolar(magnitude,
					angle, twist);
			//drive.mecanumDrive(x*.7, y*.7, twist*.7,0);
		else
			drive.mecanumDrivePolar(magnitude,
					angle, twist);
		
			//drive.mecanumDrive(x, y, twist, gyro.getAngle());
		
		
		
		
		//System.out.println("X: " + x + "\tY: " + y);
			
		if(controller.getRawButton(XboxMap.B_START)){
			gyro.reset();
			System.out.println("Fixing Gyro");
		}
	}
	
	protected boolean isFinished() {
		return finished;
	}
	protected void end() {
	}
	protected void interrupted() {
	}
}
