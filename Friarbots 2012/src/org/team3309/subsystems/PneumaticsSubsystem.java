package org.team3309.subsystems;

import org.team3309.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PneumaticsSubsystem extends Subsystem {

	private Compressor mCompressor = null;
	private DoubleSolenoid mSolenoid = null;

	private static PneumaticsSubsystem instance = null;

	public static PneumaticsSubsystem getInstance() {
		if (instance == null) {
			instance = new PneumaticsSubsystem();

			// Set default command here
		}
		return instance;
	}

	// Initialize your subsystem here
	private PneumaticsSubsystem() {
		mSolenoid = new DoubleSolenoid(RobotMap.PNEUMATIC_SOLENOID_FORWARD, RobotMap.PNEUMATIC_SOLENOID_REVERSE);
		mCompressor = new Compressor(RobotMap.PNEUMATIC_PRESSURE_SWITCH,
				RobotMap.PNEUMATIC_COMPRESSOR_RELAY);
		mCompressor.start();
	}
	
	public void deployUbar(){
		mCompressor.start();
		mSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void retractUbar(){
		mCompressor.start();
		mSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	protected void initDefaultCommand() {
		
	}
}
