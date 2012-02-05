package org.team3309;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedJaguar implements SpeedController, PIDSource, PIDOutput{
	
	private CANJaguar mJaguar = null;
	private SendablePIDController mController = null;
	private Encoder mEncoder = null;
	
	private double m_maxSpeed = 10;
	private int canId = 0;
	
	public SpeedJaguar(int canId, int aChannel, int bChannel){
		this.canId = canId;
		try {
			mJaguar = new CANJaguar(canId);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mController = new SendablePIDController(0.3, 0.0001, 0, this, this);
		mController.setInputRange(-m_maxSpeed, m_maxSpeed);
		mController.setOutputRange(-m_maxSpeed, m_maxSpeed);
		mEncoder = new Encoder(aChannel, bChannel);
		mEncoder.setDistancePerPulse(2.0 / 360.0); //360 counts to go 2'
		mEncoder.start();
		SmartDashboard.putData("Jag"+canId+" PID", mController);
		mController.enable();
	}

	public void pidWrite(double output) {
		try {
			mJaguar.setX(output / m_maxSpeed);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		SmartDashboard.putDouble("speedJagSet", output / m_maxSpeed);
	}

	public double pidGet() {
		SmartDashboard.putDouble("speedJagFPS", mEncoder.getRate());
		SmartDashboard.putDouble("encoderRaw", mEncoder.getRaw());
		System.out.println(mEncoder.getRate());
		return mEncoder.getRate();
	}

	public void disable() {
		try {
			mJaguar.disableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		//mController.disable();
		//mEncoder.stop();
	}

	public double get() {
		return mEncoder.getRate();
	}

	public void set(double x) {
		if(!mController.isEnable())
			mController.enable();
		mController.setSetpoint(x);
	}

	public void set(double x, byte arg1) {
		if(!mController.isEnable())
			mController.enable();
		mController.setSetpoint(x);
	}

}
