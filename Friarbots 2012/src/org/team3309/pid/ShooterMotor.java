package org.team3309.pid;

import org.team3309.RobotMap;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class ShooterMotor{
	
	//private SpeedJaguar jag1;
	private CANJaguar jag2, jag1;
	
	public ShooterMotor(){
		//jag1 = new SpeedJaguar(RobotMap.JAG_SHOOTER_1, RobotMap.ENCODER_SHOOTER_A, RobotMap.ENCODER_SHOOTER_B);
		try {
			jag1 = new CANJaguar(RobotMap.JAG_SHOOTER_1);
			jag2 = new CANJaguar(RobotMap.JAG_SHOOTER_2);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void setRpm(double rpm){
		jag1.set(rpm);
		//jag2.set(jag1.getVoltageSet());
		//System.out.println(rpm+"\t"+jag1.getVoltageSet());
	}

	public void setPercentVbus(double x) {
		jag1.set(x);
		jag2.set(x);
	}

}
