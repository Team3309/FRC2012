package org.Team3309.commands;

import org.Team3309.OI;
import org.Team3309.subsystems.DriveSubsystem;
import org.Team3309.subsystems.Gyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command {

	Joystick stick = null; 
	Gyro gyro = null;
	DriveSubsystem drive = null;
	boolean finished = false;

	double initialAngle = 0;

	protected void initialize() {
		// TODO Auto-generated method stub
		stick = OI.getInstance().getJoystick(1);
		gyro = Gyro.getInstance();
		drive = DriveSubsystem.getInstance();
		requires(drive);
	}

	protected void execute() {
		if(Math.abs(initialAngle - gyro.getAngle()) < 2){
			drive.mecanumDrive(0, -.25, 0, 0);
		}
		
		else{
			drive.mecanumDrive(0, .06, 0, 0);
		}
		
		if(stick.getRawButton(11)){
			finished = true;			
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
