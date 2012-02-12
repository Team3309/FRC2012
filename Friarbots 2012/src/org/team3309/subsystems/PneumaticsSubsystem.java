package org.team3309.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PneumaticsSubsystem extends Subsystem {

    private static Compressor compressor = null;
    
    private static PneumaticsSubsystem instance = null;
    
    public static PneumaticsSubsystem getInstance() {
        if (instance == null) {
            instance = new PneumaticsSubsystem();

            // Set default command here
//            compressor = new edu.wpi.first.wpilibj.Compressor(4, 7, 4, 1);
            //compressor.start();
        }
        return instance;
    }

    // Initialize your subsystem here
    private PneumaticsSubsystem() {
    }
    
    public void start(){
//        compressor.start();
    }
    
    public void stop(){
//        compressor.stop();
    }

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}
}
