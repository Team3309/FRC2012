package org.team3309.subsystems;

import org.team3309.CANJaguar;
import org.team3309.RobotMap;
import org.team3309.pid.ShooterMotor;
import org.team3309.pid.SpeedJaguar;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance = null;

	private ShooterMotor shooterMotor;
	private CANJaguar rotator;
	private ElevatorSubsystem elevator = null;

	public static final int SHOOTER_SPEED 		= 500; 	//In RPM

	protected void initDefaultCommand(){}
	
	public static ShooterSubsystem getInstance(){
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}

	private ShooterSubsystem(){	
		
		elevator = ElevatorSubsystem.getInstance();
		
		try {
			rotator = new CANJaguar(RobotMap.JAG_TURRET);
			rotator.changeControlMode(CANJaguar.ControlMode.kPosition);
			rotator.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
			rotator.configEncoderCodesPerRev(16);
			rotator.setPID(70, 0, 0);
			rotator.configSoftPositionLimits(135, -135);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void manualRotate(double x){
		rotator.set(x);
	}
	
	public void shootBall(){
		if(elevator.ballAtTop()){
			elevator.shoot();
			elevator.takeOutBallInElevator();
		}
	}

	//Starts the shooter
	public void setRPM(double rpm){
		shooterMotor.setRpm(rpm);
	}

	//Rotates the turret a certain change 
	//Prevents going over max range: 270 degrees turning angle
	public void rotateTurret(double delta){
	}
	
	public void setTurretAngle(double angle){
		rotator.set(angle);
	}
	
	//gets the angle of the turret
	public double getTurretAngle(){
		return rotator.get();
	}

	//Stops the elevator
	public void brakeElev(){
		//elevJag.brake();
	}
}
