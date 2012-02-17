package org.team3309.commands;

import org.team3309.OI;
import org.team3309.PositonInterface;
import org.team3309.SpeedJaguar;
import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command{

	private static final boolean balanced = false;
	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;
	SpeedJaguar sdrive				= null;

	double initialAngle 			= 0;
	int x							= 0;
	double driveUpSpeed				= .23;
	
	boolean startBalance			= false;
	boolean balancing				= false;
	boolean finished 				= false;

	public BalanceCommand(){
		super();
		stick = OI.getInstance().getJoystick(1);
		gyro = new Gyro(1,1);
		drive = DriveSubsystem.getInstance();
		requires(drive);
	}

	protected void initialize() {
		//initialAngle = gyro.getAngle();
		finished = false;
		startBalance = true;
	}

	protected void execute() {
		
		while(startBalance){
			driveFoward(2);
			if(gyro.getAngle() > 16){
				startBalance = false;
				balancing = true;
			}
		}
		
		while(balancing){
			if(Math.abs(initialAngle - gyro.getAngle()) < 2){
				driveFoward(1.5);
			}
			else{
				driveBackward(1);
			}
		}
	}

	protected boolean isFinished() {
		return balanced;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
