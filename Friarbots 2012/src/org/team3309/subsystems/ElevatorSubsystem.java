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
	
	private boolean ballAtBot						= false;
	private boolean ballAtTop						= false;
	private static final boolean TOP_SENSOR_ON 		= false;
	private static final boolean FEEDER_SENSOR_ON 	= false;
		
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
	
	public boolean ballAtBot(){
		return feederSensor.get() == FEEDER_SENSOR_ON;
	}

	public boolean ballAtTop(){
		return topSensor.get() == TOP_SENSOR_ON;
	}

	public void elevateBall(){
		while(!ballAtTop()){
			elevJag.set(-.75);
		}
		if(ballAtTop()){
			elevJag.set(0);
		}
	}

	public void manualElevate(double d){
		elevJag.set(d);
	}
	public void shoot(){
		//TODO WRITE METHOD!!
	}
	protected void initDefaultCommand(){}
}
