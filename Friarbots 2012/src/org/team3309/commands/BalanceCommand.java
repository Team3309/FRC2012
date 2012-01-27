package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command {

	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;

	boolean finished 				= false;
	double initialAngle 			= 0;
	
	protected void initialize() {
		stick = OI.getInstance().getJoystick(1);
		gyro = new Gyro(1,1);
		drive = DriveSubsystem.getInstance();
		
		initialAngle = gyro.getAngle();
	}

	protected void execute() {
		if(Math.abs(initialAngle - gyro.getAngle()) < 2){
			drive.mecanumDrive(0, .4, 0, 0);
		}

		else{
			drive.mecanumDrive(0, -.12, 0, 0);
			Timer.delay(1);
			finished = true;
		}
		
		System.out.println(gyro.getAngle());
	}

	protected boolean isFinished() {
		return finished;
	}

	protected void end() {
		// TODO Auto-generated method stub

	}

	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}
