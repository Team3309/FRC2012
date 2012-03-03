package org.team3309.subsystems;

import org.team3309.RobotMap;
import org.team3309.commands.AutoShooterCommand;
import org.team3309.pid.PositionJaguarImpl;
import org.team3309.pid.SpeedJaguar;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance = null;

	private SpeedJaguar shooterJag;
	//private PositionJaguarImpl rotator;
	private CANJaguar rotator, elevJag;

	private DigitalInput feederSensor, topSensor;

	private int ballsInElevator 				= 0;
	private boolean ballAtTop	 				= false;

	public static final int COUNTS_TO_ELEVATE 	= 0;	//How many encoder counts in an elevator cycle
	public static final int SHOOTER_SPEED 		= 500; 	//In RPM

	//private AutoShooterCommand command 			= new AutoShooterCommand();
	
	private static final boolean TOP_SENSOR_ON = true;
	private static final boolean FEEDER_SENSOR_ON = false;
	
	public static ShooterSubsystem getInstance(){
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}

	private ShooterSubsystem(){
		//setDefaultCommand(command);
		feederSensor = new DigitalInput(RobotMap.DIGITALINPUT_FEEDER);
		topSensor	= new DigitalInput(RobotMap.DIGITALINPUT_TOP_SENSOR);

		//shooterJag 	= new SpeedJaguar(RobotMap.JAG_SHOOTER, RobotMap.ENCODER_SHOOTER_A, RobotMap.ENCODER_SHOOTER_B);
		//rotator		= new PositionJaguarImpl(RobotMap.JAG_TURRET, RobotMap.ENCODER_TURRET_A, RobotMap.ENCODER_TURRET_B);
		try {
			elevJag			= new CANJaguar(RobotMap.JAG_ELEVATOR);
			rotator			= new CANJaguar(RobotMap.JAG_TURRET);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void initDefaultCommand() {

	}

	//Gets the number of balls in the elevator
	public int getBallsInElevator(){
		return ballsInElevator;
	}

	//If there i a ball in the front feeder, it adds one to the ball count
	public boolean ballInFeeder(){
		if(feederSensor.get() == FEEDER_SENSOR_ON){
			//System.out.println("ball in feeder");
			ballsInElevator++;
		}
		return feederSensor.get() == FEEDER_SENSOR_ON;
	}

	//Checks to see whether there is a ball ready to shoot
	public boolean ballAtTop(){
		return topSensor.get() == TOP_SENSOR_ON;
	}

	//Moves the ball until it hits the top of the elevator
	//position control
	public void elevateBall(){
		while(!ballAtTop()){
			//System.out.println("no ball at top");
			elevJag.set(-.75);
		}
		if(ballAtTop()){
			System.out.println("ball at top, stopping");
			elevJag.set(0);
		}
	}
	
	public void manualElevate(double d){
		//Insert Voltage Control for Elevate For Manual Override
		elevJag.set(d);
	}
	
	public void manualRotate(double x){
		rotator.set(x);
	}

	/*
	 * Moves the ball into the shooter
	 * decreases the ballsInElevator count
	 */
	public void shootBall(){
		if(ballAtTop){
			//elevJag.add(COUNTS_TO_ELEVATE / ballsInElevator);
			ballsInElevator--;
		}
	}

	//Starts the shooter
	public void spinUpShooter(){
		shooterJag.set(SHOOTER_SPEED);
	}

	//Rotates the turret a certain change 
	//Prevents going over max range
	public void rotateTurret(double delta){
//		if(rotator.getAngle()<135 && rotator.getAngle()>-135)
//			rotator.add(delta);
	}
	
	//gets the angle of the turret
	public int getTurretAngle(){
//		return rotator.getAngle();
		return 0;
	}

	//Stops the elevator
	public void brakeElev(){
		//elevJag.brake();
	}
}
