package org.team3309.subsystems;

import org.team3309.RobotMap;
import org.team3309.pid.ShooterMotor;
import org.team3309.pid.SpeedJaguar;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance 	= null;

	private ShooterMotor shooterMotor;
	private CANJaguar rotator;
	private ElevatorSubsystem elevator 			= null;

	public static final int SHOOTER_SPEED 		= 500; 	//In RPM

	private static final double turretOffest = 0;
	
	protected void initDefaultCommand(){}
	
	public static ShooterSubsystem getInstance(){
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}

	private ShooterSubsystem(){	
		shooterMotor = new ShooterMotor();
		//shooterMotor.setPid(100000, 100, 0);
		elevator = ElevatorSubsystem.getInstance();
		
		try {
			rotator = new CANJaguar(RobotMap.JAG_TURRET);
			rotator.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
			rotator.configEncoderCodesPerRev(16);
			rotator.changeControlMode(CANJaguar.ControlMode.kPosition);
			rotator.setPID(70, 0, 0);
			rotator.configMaxOutputVoltage(4.2);
			rotator.enableControl(0);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void setPid(double p, double i, double d){
		shooterMotor.setPid(p, i, d);
	}
	
	public void manualRotate(double x){
		rotator.set(x);
	}
	
	public void shootBall(){
		if(elevator.ballAtTop()){
			elevator.shoot();
		}
	}

	public double getVoltage(){
		return shooterMotor.getVoltage();
	}
	
	public void setPercentVbus(double x){
		shooterMotor.setPercentVbus(x);
	}

	//Rotates the turret a certain change 
	//Prevents going over max range: 270 degrees turning angle
	public void rotateTurret(double delta){
		try{
			
			if((rotator.getForwardLimitOK() && delta < 0) || (!rotator.getForwardLimitOK() || delta < 0))
				setTurretAngle(rotator.get() + delta);
			else if((rotator.getReverseLimitOK() && delta > 0) || (!rotator.getReverseLimitOK() || delta > 0))
				setTurretAngle(rotator.get() + delta);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setTurretAngle(double angle){
		System.out.println("Setting turret to "+angle);
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

	public void setVoltage(double d) {
		shooterMotor.setVoltage(d);
	}
}
