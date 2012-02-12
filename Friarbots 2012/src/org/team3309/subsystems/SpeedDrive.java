package org.team3309.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;

public class SpeedDrive extends RobotDrive{

	public SpeedDrive(CANJaguar frontLeftMotor,
			CANJaguar rearLeftMotor, CANJaguar frontRightMotor,
			CANJaguar rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		
	}

}
