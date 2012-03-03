package org.team3309.commands;

import org.team3309.OI;
import org.team3309.RobotMap;
import org.team3309.pid.PositionJaguar;
import org.team3309.pid.PositionJaguarImpl;
import org.team3309.pid.SpeedJaguar;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.PneumaticsSubsystem;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command{

	private boolean balanced = false;

	Joystick stick 					= null; 
	edu.wpi.first.wpilibj.Gyro gyro = null;
	DriveSubsystem drive 			= null;
	SpeedJaguar sdrive				= null;
	PositionJaguar pDrive1			= null;
	PositionJaguar pDrive2			= null;
	PositionJaguar pDrive3			= null;
	PositionJaguar pDrive4			= null;	

	double initialAngle 			= 0;
	int x							= 0;

	boolean startBalance			= false;
	boolean balancing				= false;
	boolean finished 				= false;
	double  tempAngle				= 0;
	int y 							= 0;
	
	
	private static final double TOLERANCE = 5;
	private static final double INCHES_TO_MOVE = inchToRev(1);
	private static final double BALANCED_ANGLE = 0;

	public BalanceCommand(){
		super();
		stick 			= OI.getInstance().getJoystick(1);
		gyro 			= new Gyro(1,1);
		drive 			= DriveSubsystem.getInstance();

		pDrive1 		= new PositionJaguarImpl(RobotMap.JAG_BACK_LEFT, RobotMap.ENCODER_BACK_LEFT_A, RobotMap.ENCODER_BACK_LEFT_B);
		pDrive2			= new PositionJaguarImpl(RobotMap.JAG_BACK_RIGHT, RobotMap.ENCODER_BACK_RIGHT_A, RobotMap.ENCODER_BACK_RIGHT_B);
		pDrive3			= new PositionJaguarImpl(RobotMap.JAG_FRONT_LEFT, RobotMap.ENCODER_FRONT_LEFT_A, RobotMap.ENCODER_FRONT_LEFT_B);
		pDrive4			= new PositionJaguarImpl(RobotMap.JAG_FRONT_RIGHT, RobotMap.ENCODER_FRONT_RIGHT_A, RobotMap.ENCODER_FRONT_RIGHT_B);

		//requires(drive);
	}

	protected void initialize() {
		initialAngle = gyro.getAngle();
		finished = false;
		startBalance = true;
	}

	protected void execute() {
		if(Math.abs(gyro.getAngle() - BALANCED_ANGLE) < TOLERANCE){
			balanced = true;
			System.out.println("Balanced!");
			brake();
			return;
		}
		else{ //not balanced
			System.out.print("Moving forward "+INCHES_TO_MOVE+" rev");
			drive(INCHES_TO_MOVE);
			waitForFinish();
			System.out.println(" finished moving");
		}
		
	}
	protected boolean isFinished() {
		return balanced;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
	
	private double getAdjustedAngle(){
		return gyro.getAngle() - initialAngle;
	}

	private void drive(double d){
		pDrive1.add(-d);
		pDrive2.add(d);
		pDrive3.add(-d);
		pDrive4.add(d);
	}

	private void brake(){
		pDrive1.brake();
		pDrive2.brake();
		pDrive3.brake();
		pDrive4.brake();
	}

	private void waitForFinish(){
		pDrive1.waitForFinish();
		pDrive2.waitForFinish();
		pDrive3.waitForFinish();
		pDrive4.waitForFinish();
	}

	private static double inchToRev(double inches){
		double revs = inches/(8*Math.PI*360);
		return revs;
	}

}
