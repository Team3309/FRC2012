package org.team3309.pid;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class PositionJaguarImpl implements PositionJaguar{
	
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
	}
	
	public void start(){
		mEncoder.start();
	}

	public void absolute(double pos) {
		
	}

	public void add(double delta) {
		
	}

	public void stop() {
		
	}

}
