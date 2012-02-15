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
	int x							= 0;
	double driveUpSpeed				= .23;

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
		balancing = true;
		x=1;
	}

	protected void execute() {
		initialAngle = gyro.getAngle();
		while(balancing){
			System.out.println(Math.abs(initialAngle - gyro.getAngle()));
			if(Math.abs(gyro.getAngle()) < SmartDashboard.getDouble("Stop Angle", 14)){
				drive.mecanumDrive(0, .04, 0,0);
					System.out.println("It's Balanced!!!!");
				if(Math.abs(gyro.getAngle()) < 5){
					drive.mecanumDrive(0, .08, 0,0);
					Timer.delay(.06);
					drive.brake();
					balancing = false;
				}
			}
			else if(Math.abs(initialAngle - gyro.getAngle()) < SmartDashboard.getDouble("AngleThreshhold", 20)){
				if(x==1 && Math.abs(initialAngle - gyro.getAngle()) < 20){
				drive.mecanumDrive(0, SmartDashboard.getDouble("Forward Speed",-.25), 0, 0);
				Timer.delay(.7);
				x=2;
				}
				if(Math.abs(initialAngle - gyro.getAngle()) < 18 && x==2){
					drive.mecanumDrive(0,-.16,0,0);
					Timer.delay(.7);
					x=1;
				}
				System.out.println("Driving Up");
			}

			else{
				//if(Math.abs(initialAngle - gyro.getAngle()) > SmartDashboard.getDouble("AngleThreshhold", 20))
				drive.mecanumDrive(0, SmartDashboard.getDouble("BackwardSpeed",.24), 0,0);
				drive.mecanumDrive(0, .26, 0,0);
				
				System.out.println("Fixing");

			}
			if(stick.getRawButton(11)){
				balancing = false;
				cancel();
			}
			Timer.delay(.5);


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
