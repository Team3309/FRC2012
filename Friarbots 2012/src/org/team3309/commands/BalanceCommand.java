package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command {

	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;

	boolean finished 				= false;
	double initialAngle 			= 0;
	
	protected void initialize() {
		// TODO Auto-generated method stub
		stick = OI.getInstance().getJoystick(1);
		gyro = new Gyro(1,1);
		drive = DriveSubsystem.getInstance();
	}

	protected void execute() {
		while(initialAngle != gyro.getAngle()){
			if(Math.abs(initialAngle - gyro.getAngle()) < 2){
				drive.mecanumDrive(0, -.25, 0, 0);
			}

			else{
				drive.mecanumDrive(0, .06, 0, 0);
			}
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
