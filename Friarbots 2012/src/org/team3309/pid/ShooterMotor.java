package org.team3309.pid;

import org.team3309.RobotMap;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterMotor{
	
	private CANJaguar jag1, jag2;
	
	private static final double MAX_SPEED = 500;
	
	public ShooterMotor(){
		try {
			jag1 = new CANJaguar(RobotMap.JAG_SHOOTER_1);
			jag2 = new CANJaguar(RobotMap.JAG_SHOOTER_2);
			
			jag1.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
			jag2.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
			jag1.setPID(10, 0, 0);
			jag2.setPID(10, 0, 0);
			jag1.enableControl();
			jag2.enableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void setPid(double p, double i, double d){
		try {
			jag1.setPID(p, i, d);
			jag2.setPID(p, i, d);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setVoltage(double volts){
		try{
			if(jag1.getControlMode() == CANJaguar.ControlMode.kVoltage){
				jag1.setX(volts);
				jag2.setX(volts);
			}
			else{
				double p=jag1.getP(), i=jag1.getI(), d=jag1.getD();
				jag1.changeControlMode(CANJaguar.ControlMode.kVoltage);
				jag2.changeControlMode(CANJaguar.ControlMode.kVoltage);
				jag1.setPID(p, i, d);
				jag2.setPID(p, i, d);
				jag1.enableControl();
				jag2.enableControl();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public double getVoltage(){
		double volt1 = 0;
		double volt2 = 0;
		try{
			volt1 = jag1.getOutputVoltage();
			volt2 = jag2.getOutputVoltage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return (volt1+volt2)/2;
	}

	public void setPercentVbus(double x) {
		try{
			if(jag1.getControlMode() == CANJaguar.ControlMode.kPercentVbus){
				jag1.setX(x);
				jag2.setX(x);
			}
			else{
				double p=jag1.getP(), i=jag1.getI(), d=jag1.getD();
				jag1.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				jag2.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				jag1.setPID(p, i, d);
				jag2.setPID(p, i, d);
				jag1.enableControl();
				jag2.enableControl();
				jag1.setX(x);
				jag2.setX(x);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
