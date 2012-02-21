package org.team3309.pid;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionJaguarImpl implements PositionJaguar, PIDSource, PIDOutput{
	
	private CANJaguar mJag;
	private Encoder mEncoder;
	private SendablePIDPositionController mPid;
	
	private boolean enabled = false;
	private int multiplier = 1;
	private static final int TOLERANCE = 10;
	
	public PositionJaguarImpl(int canId, int encoderA, int encoderB){
		try {
			mJag = new CANJaguar(canId);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mEncoder = new Encoder(encoderA, encoderB);
		mEncoder.setDistancePerPulse(2/360); //360 counts to go 2'
		mPid = new SendablePIDPositionController(.01, 0, 0, this, this);
		SmartDashboard.putData("PID", mPid);
		mPid.setScale(.25);
		//mPid.setTolerance(10);
	}
	
	public void start(){
		mEncoder.start();
		mPid.enable();
	}
	public int getAngle(){
		return mEncoder.get();
	}

	public void absolute(double pos) {
		if(!enabled)
			enable();
		mPid.setSetpoint(pos);
	}

	public void add(double delta) {
		absolute(mEncoder.get() + delta);
	}

	public void stop() {
		mEncoder.stop();
		mPid.disable();
	}

	public void pidWrite(double output) {
		if(!enabled) 
			enable();
		mJag.set(output*multiplier);
		SmartDashboard.putDouble("PositionJaguar Output", output);
	}

	public double pidGet() {
		SmartDashboard.putInt("PositionJaguar Encoder", mEncoder.get());
		return mEncoder.get();
	}
	
	public void enable(){
		mEncoder.start();
		mPid.enable();
		enabled = true;
	}
	
	public void disable(){
		mEncoder.stop();
		mPid.disable();
		enabled = false;
	}

	public void brake() {
		mPid.setSetpoint(mEncoder.get());
	}

	public void setInverted() {
		multiplier = -1;
	}

	public void waitForFinish() {
		while(true){
			int pos = mEncoder.get();
			if(Math.abs(pos - mPid.getSetpoint()) <= TOLERANCE)
				return;
		}
	}

	public void setVoltage(double x) {
		mJag.set(x);
	}

}
