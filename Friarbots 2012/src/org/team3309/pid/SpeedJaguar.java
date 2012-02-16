package org.team3309.pid;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedJaguar implements SpeedController, PIDSource, PIDOutput, Runnable{
	
	private CANJaguar mJaguar = null;
	private SendablePIDController mController = null;
	private Encoder mEncoder = null;
	
	private static final double sMaxSpeed = 500;
	private int canId = 0;
	
	private boolean enabled = false;
	
	private Thread mThread;
	
	private int lastCount = 0;
	private double mRPM = 0;
	
	private static final int REVOLUTION = 360; //encoder ticks per revolution
	private static final int DELTA_T	= 100; //100ms between each sample
	
	public SpeedJaguar(int canId, int aChannel, int bChannel){
		this.canId = canId;
		try {
			mJaguar = new CANJaguar(canId);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mController = new SendablePIDController(10, 0.1, 0, this, this);
		mController.setInputRange(-sMaxSpeed, sMaxSpeed);
		mController.setOutputRange(-sMaxSpeed, sMaxSpeed);
		mEncoder = new Encoder(aChannel, bChannel);
		mEncoder.setDistancePerPulse(2.0 / 360.0); //360 counts to go 2'
		mEncoder.start();
		SmartDashboard.putData("Jag"+canId+" PID", mController);
		
		mThread = new Thread(this, "SpeedJaguar"+canId);
	}

	public void pidWrite(double output) {
		try {
			mJaguar.setX(output / sMaxSpeed);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		SmartDashboard.putDouble("speedJagSet", output / sMaxSpeed);
	}

	public double pidGet() {
		System.out.println("returning "+mRPM+" for pidGet");
		return mRPM;
	}

	public void disable() {
		try {
			mJaguar.disableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mController.disable();
		mEncoder.stop();
	}

	public double get() {
		return mRPM;
	}

	public void set(double x) {
		if(!enabled)
			enable();
		mController.setSetpoint(x);
	}

	public void set(double x, byte arg1) {
		if(!enabled)
			enable();
		mController.setSetpoint(x);
	}
	
	public void enable(){
		mController.enable();
		mEncoder.start();
		try {
			mJaguar.enableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		enabled = true;
		mThread.start();
	}

	public void run() {
		while(true){
			if(enabled){
				int curCount = mEncoder.get();
				SmartDashboard.putInt("EncoderCount", curCount);
				double revms = (60000*Math.abs(curCount-lastCount)/REVOLUTION)/DELTA_T;
				SmartDashboard.putInt("DeltaCount", Math.abs(curCount-lastCount));
				SmartDashboard.putDouble("r/ms", revms);
				mRPM = revms; //60000*rev/ms = rev/min
				SmartDashboard.putDouble("SpeedJagRPM", mRPM);
				try {
					Thread.sleep(DELTA_T);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lastCount = curCount;
			}
		}
	}

}
