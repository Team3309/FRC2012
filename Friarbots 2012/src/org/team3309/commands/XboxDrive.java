/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.commands;

import org.team3309.OI;
import org.team3309.XboxMap;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.Gyro;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 * @author Vincente!
 */
public class XboxDrive extends Command {

	private JoystickButton driveGyroResetButton;
	private JoystickButton balanceGyroResetButton;
	private JoystickButton balanceButton;
	private JoystickButton balanceCancelButton;
	
	public Gyro gyro;
	public Gyro bgyro;
	
	private BalanceCommand balanceCommand;
	
	private DriveSubsystem drive 	= null;
	private Joystick controller 	= null;
	
	private boolean finished		= false;
	
	public XboxDrive(int joystickID) {
		drive = DriveSubsystem.getInstance();
		
		requires(drive);
		
		controller = OI.getInstance().getJoystick(joystickID);
		
		gyro = Gyro.getInstance(1, 2);
		bgyro = Gyro.getInstance(1, 1);
		
		driveGyroResetButton = new JoystickButton(controller, XboxMap.B_LEFT_STICK);
		balanceGyroResetButton = new JoystickButton(controller, XboxMap.B_RIGHT_STICK);
		
		balanceButton = new JoystickButton(controller, XboxMap.B_START);
		balanceCancelButton	= new JoystickButton(controller, XboxMap.B_BACK);
		
		balanceCommand = new BalanceCommand();
	}
	
	public Joystick getJoystick(){
		return controller;
	}

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
			protected void execute() {
				bgyro.reset();
			}
			protected void end(){
			}
			protected void interrupted(){
			}
			protected void initialize() {
			}
			protected boolean isFinished() {
				return false;
			}
		});
		
		balanceButton.whenPressed(balanceCommand);
		balanceCancelButton.whenPressed(new Command(){
			protected void end() {
			}
			protected void execute() {
				balanceCommand.cancel();
			}
			protected void initialize() {
			}
			protected void interrupted() {
			}
			protected boolean isFinished() {
				return false;
			}
		});
	}

	protected void execute() {
		//gyro.updateDesiredHeading(stick.getTwist());
		
		//Following Algorithm Corrects loose Joystick on Xbox controller with cubic functions
		
		//Move left and right using X Axis of Left Stick, x axis
		double x = controller.getRawAxis(XboxMap.A_LEFT_X);
		if(Math.abs(x) < .05)
			x = 0;
		x-=.05;
		x = MathUtils.pow(x, 3);
		
		//Move forward and back using Y Axis of Left stick, y axis
		double y = controller.getRawAxis(XboxMap.A_LEFT_Y);
		if(Math.abs(y) < .05)
			y = 0;
		y = MathUtils.pow(y, 3);
		y-=.05;
		
		//Twist using right stick, x axis
		double twist = controller.getRawAxis(XboxMap.A_RIGHT_X);
		if(Math.abs(twist) < .05)
			twist = 0;
		twist = MathUtils.pow(twist, 3);
		twist-=.05;
		
		drive.mecanumDrive(x, y, twist, gyro.getAngle());
	}

	protected boolean isFinished() {
		return finished;
	}
	protected void end() {
	}
	protected void interrupted() {
	}
}
