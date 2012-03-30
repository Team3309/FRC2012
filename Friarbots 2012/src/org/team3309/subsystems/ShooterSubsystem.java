package org.team3309.subsystems;

import org.team3309.RobotMap;
import org.team3309.pid.ShooterMotor;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem{

	private static ShooterSubsystem instance 	= null;

	private ShooterMotor shooterMotor;
	private CANJaguar rotator;
	private ElevatorSubsystem elevator 			= null;

	public static final int SHOOTER_SPEED 		= 500; 	//In RPM

	protected void initDefaultCommand(){}

	public static ShooterSubsystem getInstance(){
		if(instance == null)
			instance = new ShooterSubsystem();
		return instance;
	}

	private ShooterSubsystem(){	
		shooterMotor = new ShooterMotor();
		//shooterMotor.setPid(100000, 100, 0);
		//elevator = ElevatorSubsystem.getInstance();

		try {
			rotator = new CANJaguar(RobotMap.JAG_TURRET);
			rotator.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
			rotator.configEncoderCodesPerRev(16);
			rotator.changeControlMode(CANJaguar.ControlMode.kPosition);
			rotator.setPID(10,0, 0); //original = 70 -didn't work with vision
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
		try {
			rotator.setX(x);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			setTurretAngle(rotator.getPosition() + delta);
		}catch(CANTimeoutException e){
			e.printStackTrace();
		}
	}

	public void setTurretAngle(double angle){
		try {
			System.out.println("Setting turret to "+angle+"\tCurrently at "+rotator.getPosition());
			rotator.setX(angle);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//gets the angle of the turret
	public double getTurretAngle(){
		try {
			return rotator.getPosition();
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}

	public void setVoltage(double d) {
		shooterMotor.setVoltage(d);
	}

	public void setTurretPID(double p, double i, double d){
		try{
			rotator.setPID(p, i, d);
			rotator.configMaxOutputVoltage(4.2);
			rotator.enableControl(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
