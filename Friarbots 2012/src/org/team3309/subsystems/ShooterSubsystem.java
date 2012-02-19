package org.team3309.subsystems;

import org.team3309.commands.AutoShooterCommand;
import org.team3309.pid.SpeedJaguar;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem{
	
	private static ShooterSubsystem instance = null;
	
	private CANJaguar front, back, reloader, rotator;
	private SpeedJaguar shooterJag;
	private DigitalInput frontFeeder, backFeeder, reloaderSensor;
	
	private int ballsInElevator = 0;
	private boolean ballInReloader = false;
	
	public static final int COUNTS_TO_FEED = 0;
	public static final int COUNTS_TO_ELEVATE = 0;
	public static final int SHOOTER_SPEED = 500;
	
	private AutoShooterCommand command = new AutoShooterCommand();
	
	public static ShooterSubsystem getInstance(){
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}
	
	private ShooterSubsystem(){
		setDefaultCommand(command);
	}

	protected void initDefaultCommand() {
		
	}
	
	public int getBallsInElevator(){
		return ballsInElevator;
	}
	
	public boolean getFrontFeeder(){
		if(frontFeeder.get())
			ballsInElevator++;
		return frontFeeder.get();
	}
	
	public boolean getBackFeeder(){
		if(backFeeder.get())
			ballsInElevator++;
		return backFeeder.get();
	}
	
	public boolean ballInFeeder(){
		return getFrontFeeder() || getBackFeeder();
	}
	
	public boolean ballInReloader(){
		return reloaderSensor.get();
	}
	
	public void feedBall(){
		try {
			reloader.setX(COUNTS_TO_FEED);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void elevateBall(){
		try {
			front.setX(COUNTS_TO_ELEVATE / ballsInElevator);
			back.setX(COUNTS_TO_ELEVATE / ballsInElevator);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		ballsInElevator--;
	}
	
	public void spinUpShooter(){
		shooterJag.set(SHOOTER_SPEED);
	}
	
	public void rotateTurret(double delta){
		try {
			rotator.setX(delta);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
