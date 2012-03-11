package org.team3309.subsystems;

import org.team3309.RobotMap;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {
	private CANJaguar elevJag;
	private DigitalInput feederSensor,topSensor;
	
	private static ElevatorSubsystem instance = null;
	
	private int ballsInElevator 					= 0;
	private boolean ballAtTop						= false;
	private static final boolean TOP_SENSOR_ON 		= false;
	private static final boolean FEEDER_SENSOR_ON 	= false;
	
	public static final int COUNTS_TO_ELEVATE 	= 0;	//How many encoder counts in an elevator cycle
	
	public static ElevatorSubsystem getInstance(){
		if(instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}
	
	private ElevatorSubsystem(){
		feederSensor = new DigitalInput(RobotMap.DIGITALINPUT_FEEDER);
		topSensor	= new DigitalInput(RobotMap.DIGITALINPUT_TOP_SENSOR);
		try{
			elevJag			= new CANJaguar(RobotMap.JAG_ELEVATOR);
		}catch(CANTimeoutException e){
			e.printStackTrace();
		}
	}
	
	//Gets the number of balls in the elevator
	public int getBallsInElevator(){
		return ballsInElevator;
	}

	//If there is a ball in the front feeder, it adds one to the ball count
	public boolean ballInFeeder(){
		if(feederSensor.get() == FEEDER_SENSOR_ON){
			System.out.println("ball in feeder");
			ballsInElevator++;
		}
		return feederSensor.get() == FEEDER_SENSOR_ON;
	}

	//Checks to see whether there is a ball at top
	public boolean ballAtTop(){
		return topSensor.get() == TOP_SENSOR_ON;
	}

	//Moves the ball until it hits the top of the elevator 
	public void elevateBall(){
		while(!ballAtTop()){
			elevJag.set(-.75);
		}
		if(ballAtTop()){
			elevJag.set(0);
		}
	}
	
	public void takeOutBallInElevator(){
		ballsInElevator--;
	}
	public void manualElevate(double d){
		//Insert Voltage Control for Elevate For Manual Override
		elevJag.set(d);
	}
	public void shoot(){
		//TODO WRITE METHOD!!
	}
	protected void initDefaultCommand(){}
}
