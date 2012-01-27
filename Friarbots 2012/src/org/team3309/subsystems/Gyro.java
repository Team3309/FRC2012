/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author vmagro
 */
public class Gyro extends Subsystem {

    private static Gyro instance 				= null;
    private edu.wpi.first.wpilibj.Gyro gyro;
    private final double MAX_OFFSET 			= 4; //max offset //previously 8.28
    private final double MAX_SPEED 				= 0.5;
    private final double MIN_SPEED 				= 0.25;

    private double desiredHeading 				= 0;
    private double joyStickTwist;
    
    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro(1,1);
        }
        return instance;
    }

    // Initialize your subsystem here
    private Gyro(int slot, int channel) {
        gyro = new edu.wpi.first.wpilibj.Gyro(slot, channel);
    }
    
    private double convertAngle(double a){
        double m = a%360;
        if(m<0){
            return m+360;
        }else{
            return m;
        }
    }
    public double getAngle(){
        return convertAngle(gyro.getAngle());
    }
    public double getConvertedAngle(){
        return convertAngle(gyro.getAngle());
    }
    public void reset(){
        desiredHeading = 0;
        gyro.reset();
    }

    /**
     *
     * @param twist - Joystick twist value - not cubed!
     * @return
     */
    public double getTwistRate(){
        double actualHeading = gyro.getAngle();
        actualHeading=convertAngle(actualHeading);
        double distance = distanceBetweenAngles(desiredHeading, actualHeading);
        if(Math.abs(distance) >= 10){
            double twistRate = ((MAX_SPEED-MIN_SPEED)/180)*Math.abs(distance)+MIN_SPEED;
            //double twistRate = MIN_SPEED;
            return -twistRate*sign(distance);
        }
        else{return 0;}
    }

    public void setDesiredHeading(double desiredheading){
        this.desiredHeading = desiredheading;
    }

    public void updateDesiredHeading(double joystickTwist){
        desiredHeading+=joystickTwist*joystickTwist*joystickTwist*(MAX_OFFSET);
        desiredHeading=convertAngle(desiredHeading);
    }
    /**
     * @param a
     * @param d
     * @return
     */
    public double distanceBetweenAngles(double a, double d){

        // rotate both so that actual is at 0
        double aRot = 0;
        double dRot = convertAngle(d - a);
        if(dRot < 180){
            //System.out.println("turn clockwise");
            return dRot;
        } else {
            //System.out.println("turn counter clockwise");
            return dRot - 360;
        }

//        a = convertAngle(a);
//        d = convertAngle(d);
//        double d1 = d-a;
//        double d2 = a-d;
//        int d1Sign = 1;
//        int d2Sign = 1;
//        if(d1<0){
//            d1Sign = -1; //negative
//        }
//        if(d2<0){
//            d2Sign = -1; //negative
//        }
//        d1 = convertAngle(d1);
//        d2 = convertAngle(d2);
//        if(d1<d2){
//            return d1*d1Sign;
//        }
//        else{
//            return d2*d2Sign;
//        }
    }

    private double sign(double x){
        if(x == 0){
            return 0.0;
        }

        if ( x > 0) {
            return 1.0;
        } else {
            return -1.0;
        }
    }

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}