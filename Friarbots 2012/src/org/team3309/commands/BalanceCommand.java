package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BalanceCommand extends Command {

	private static final boolean balanced = false;
	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;

	boolean finished 				= false;
	double initialAngle 			= 0;
	boolean balancing				= false;
	
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
	}

	protected void execute() {
		balancing = true;
		initialAngle = gyro.getAngle();
		while(balancing){
			SmartDashboard.putDouble("BalanceGyro", gyro.getAngle());
			if(Math.abs(initialAngle - gyro.getAngle()) < (initialAngle - 1)){
				drive.mecanumDrive(0, -.34, 0, 0);
			}
			else{
				Timer.delay(.3);
				drive.mecanumDrive(0, .34, 0,0);
				if(Math.abs(initialAngle - gyro.getAngle()) < 2)
					balancing = false;
			}
			if(stick.getRawButton(11)){
				balancing = false;
				cancel();
			}
		}
	}

	protected boolean isFinished() {
		return balanced;
	}

	protected void end() {
		// TODO Auto-generated method stub

	}

	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}
