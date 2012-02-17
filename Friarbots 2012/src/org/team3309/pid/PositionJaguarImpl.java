package org.team3309.pid;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class PositionJaguarImpl implements PositionJaguar, PIDSource, PIDOutput{
	
	private CANJaguar mJag;
	private Encoder mEncoder;
	private PIDController mPid;
	
	public PositionJaguarImpl(int canId, int encoderA, int encoderB){
		try {
			mJag = new CANJaguar(canId);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mEncoder = new Encoder(encoderA, encoderB);
		mEncoder.setDistancePerPulse(2/360); //360 counts to go 2'
		mPid = new PIDController(.1, 0, 0, this, this);
		mPid.setOutputRange(-.25, .25);
	}
	
	public void start(){
		mEncoder.start();
		mPid.enable();
	}

	public void absolute(double pos) {
		mPid.setSetpoint(pos);
	}

	public void add(double delta) {
		mPid.setSetpoint(mEncoder.get() + delta);
	}

	public void stop() {
		mEncoder.stop();
		mPid.disable();
	}

	public void pidWrite(double output) {
		mJag.set(output);
	}

	public double pidGet() {
		return mEncoder.get();
	}

}
