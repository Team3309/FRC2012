package org.team3309.commands;

import org.team3309.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Command;

public class BalanceCommand extends Command {

	private DriveSubsystem drive;
	private CANJaguar[] motors;
	private Gyro gyro = new Gyro(1,1);
	
	private boolean balancing = false;

	public BalanceCommand() {
		drive = DriveSubsystem.getInstance();
		requires(drive);
		motors = drive.getMotors();
	}

	protected void initialize() {
		try {
			for (CANJaguar jag : motors) {
				jag.changeControlMode(CANJaguar.ControlMode.kPosition);
				jag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
				jag.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
				jag.configEncoderCodesPerRev(360);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void execute() {
		try{
			gyro.reset();
			while(balancing){
				double theta = gyro.getAngle();
				if(Math.abs(theta - 17) < 2){
					for(CANJaguar jag : motors)
						jag.setX(36, (byte) 0x42);
				}
				if(Math.abs(theta) < 2){
					for(CANJaguar jag : motors)
						jag.setX(0, (byte) 0x42);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	protected boolean isFinished() {
		return balancing;
	}

	protected void end() {
		
	}

	protected void interrupted() {
		
	}

}
