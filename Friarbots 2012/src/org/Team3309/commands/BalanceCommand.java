package org.Team3309.commands;

import org.Team3309.OI;
import org.Team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command {

	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;
	Button breakButton 				= null;
	JoystickDrive teleop			= null;

	boolean finished 				= false;
	double initialAngle 			= 0;
	
	protected void initialize() {
		// TODO Auto-generated method stub
		stick = OI.getInstance().getJoystick(1);
		breakButton = stick.getButton(11);
		gyro = new Gyro(1,1);
		drive = DriveSubsystem.getInstance();
		teleop = new JoystickDrive(1);
	}

	protected void execute() {
		while(initialAngle != gyro.getAngle()){
			if(Math.abs(initialAngle - gyro.getAngle()) < 2){
				drive.mecanumDrive(0, -.25, 0, 0);
			}

			else{
				drive.mecanumDrive(0, .06, 0, 0);
			}
			breakButton.whenPressed(teleop);
		}
	}

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finished;
	}

	protected void end() {
		// TODO Auto-generated method stub

	}

	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}
