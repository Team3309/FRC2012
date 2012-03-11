package org.team3309.subsystems;

import org.team3309.CANJaguar;
import org.team3309.RobotMap;
import org.team3309.pid.SpeedJaguar;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance = null;

	private SpeedJaguar shooterJag1, shooterJag2;
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
		Encoder shooterEncoder = new Encoder(RobotMap.ENCODER_SHOOTER_A, RobotMap.ENCODER_SHOOTER_B);
		shooterJag1 	= new SpeedJaguar(RobotMap.JAG_SHOOTER_1, shooterEncoder);
		shooterJag2		= new SpeedJaguar(RobotMap.JAG_SHOOTER_2, shooterEncoder);
		try {
			rotator			= new CANJaguar(RobotMap.JAG_TURRET);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elevator = ElevatorSubsystem.getInstance();
		
		try {
			rotator = new CANJaguar(RobotMap.JAG_TURRET);
			rotator.changeControlMode(CANJaguar.ControlMode.kPosition);
			rotator.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
			rotator.configEncoderCodesPerRev(16);
			rotator.setPID(100, 0, 0);
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
		shooterJag1.set(rpm);
		shooterJag2.set(rpm);
	}

	//Rotates the turret a certain change 
	//Prevents going over max range: 270 degrees turning angle
	public void rotateTurret(double delta){
		if(rotator.get()<135 && rotator.get()>-135)
			rotator.set(rotator.get() + delta);
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
