package subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import actions.isGonnaCrashAh;
import constants.Const;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import objects.Action;

public class drive extends Action {
	public TalonSRX bL, fL, bR, fR;
	double leftSide,rightSide;
	isGonnaCrashAh gonnaCrashL, gonnaCrashR;
	AnalogInput distSensL, distSensR;

	public drive() {
		bL = new TalonSRX(Const.motorBL);
		fL = new TalonSRX(Const.motorBR);
		bR = new TalonSRX(Const.motorFL);
		fR = new TalonSRX(Const.motorFR);
		bR.setInverted(true);
		fR.setInverted(true);
//		gonnaCrashL = new isGonnaCrashAh(Const.dSensL);
	//	gonnaCrashR = new isGonnaCrashAh(Const.dSensR);
		// setInverted is used to enumerate backward motors
		// usually both motors on one side will be inverted
	}

	

	/** Stops all motors */
	public void stopMotors() {
		//bL.set(ControlMode.PercentOutput, 0.0);
		fL.set(ControlMode.PercentOutput, 0.0);
		//bR.set(ControlMode.PercentOutput, 0.0);
		fR.set(ControlMode.PercentOutput, 0.0);
	}

	/** Moves the robot linearly based on joystick movement */
	public void tankDrive(Joystick ljoy, Joystick rjoy) {
		// halves speed if near wall / isGonnaCrash
		// sets speed to the joystick value input times .5 if near wall, else times 1
		if (Math.abs(ljoy.getY()) > Const.ledzone) {
			bL.set(ControlMode.PercentOutput, ljoy.getY() * ((gonnaCrashL.isNearWall()) ? .5 : 1));
			fL.follow(bL);
		}
		if (Math.abs(rjoy.getY()) > Const.redzone) {
			bR.set(ControlMode.PercentOutput, rjoy.getY() * ((gonnaCrashR.isNearWall()) ? .5 : 1));
			fR.follow(bR);
		}
	}
	
	/** Moves the robot linearly based on joystick movement */
	public void tankDrive(XboxController x) {
		// halves speed if near wall / isGonnaCrash
		// sets speed to the joystick value input times .5 if near wall, else times 1
		if (Math.abs(x.getY(Hand.kLeft)) > Const.ledzone) {
			bL.set(ControlMode.PercentOutput, x.getY(Hand.kLeft) * ((gonnaCrashL.isNearWall()) ? .5 : 1));
			fL.follow(bL);
		}
		if (Math.abs(x.getY(Hand.kRight)) > Const.redzone) {
			bR.set(ControlMode.PercentOutput, x.getY(Hand.kRight) * ((gonnaCrashR.isNearWall()) ? .5 : 1));
			fR.follow(bR);
		}
	}
	public void XBoxDrive(XboxController xbox,double baseSpeed){

		/* This is a control scheme inspired by rocket league.

		 * 

		 * Pressing left trigger is reverse and pressing right trigger is forward. 

		 * The left thumb stick controls turning. Pushing the stick left will do will

		 * turn left and pushing the stick right will turn left

		 * 

		 */

		if(xbox.getRawButton(2)){

			if(xbox.getRawAxis(0)<0) {

				rightSide= -xbox.getRawAxis(0);

				leftSide=-rightSide;

			}

			if(xbox.getRawAxis(0)>0) {

				leftSide= xbox.getRawAxis(0);

				rightSide=-rightSide;

			}

		} else {

			baseSpeed= -xbox.getRawAxis(2)+xbox.getRawAxis(3);

			if(xbox.getRawAxis(0)<0) {

				leftSide=baseSpeed*(1+xbox.getRawAxis(0));

				rightSide=baseSpeed;

			}

			if(xbox.getRawAxis(0)>0) {

				rightSide=baseSpeed*(1-xbox.getRawAxis(0));

				leftSide=baseSpeed;

			}

		}

		turn(leftSide,rightSide);

		//combines the ax'es of the triggers

		

		

	}

	/**
	 * [WIP] Moves the robot based on a quadratic function of joystick movement
	 */
	@Deprecated
	public void tankDriveCurved(Joystick ljoy, Joystick rjoy) {// WIP
		// halves speed if near wall / isGonnaCrash
		// sets speed to the joystick function input times .5 if near wall, else times 1
		if (Math.abs(ljoy.getY()) > Const.ledzone)
			bL.set(ControlMode.PercentOutput, -ljoy.getY() * ((gonnaCrashL.isNearWall()) ? .5 : 1));
		fL.set(ControlMode.PercentOutput, -ljoy.getY() * ((gonnaCrashL.isNearWall()) ? .5 : 1));
		bR.set(ControlMode.PercentOutput, -rjoy.getY() * ((gonnaCrashR.isNearWall()) ? .5 : 1));
		fR.set(ControlMode.PercentOutput, -rjoy.getY() * ((gonnaCrashR.isNearWall()) ? .5 : 1));
	}

	/** Moves all motors at same power */
	public void setAllMotors(double spped) {

		bL.set(ControlMode.PercentOutput, spped);
		fL.set(ControlMode.PercentOutput, spped);
		bR.set(ControlMode.PercentOutput, spped);
		fR.set(ControlMode.PercentOutput, spped);
	}

	/** Runs both sides of the drivetrain at desired power individually */
	public void turn(double speedL, double speedR) {

		bL.set(ControlMode.PercentOutput, speedL);
		fL.set(ControlMode.PercentOutput, speedL);
		bR.set(ControlMode.PercentOutput, speedR);
		fR.set(ControlMode.PercentOutput, speedR);	}

	/**
	 * Causes the robot to spin by running both sides of the drive train in
	 * opposition
	 */
	public void spin() {

		bL.set(ControlMode.PercentOutput, -1.0);
		fL.set(ControlMode.PercentOutput, -1.0);
		bR.set(ControlMode.PercentOutput, 1.0);
		fR.set(ControlMode.PercentOutput, 1.0);
	}

	/** Runs front right motor at full power */
	public void runFR(double spped) {
		fR.set(ControlMode.PercentOutput, spped);
	}

	/** Runs back right motor at full power */
	public void runBR(double spped) {
		bR.set(ControlMode.PercentOutput, spped);
	}

	/** Runs front left motor at full power */
	public void runFL(double spped) {
		fL.set(ControlMode.PercentOutput, spped);
	}

	/** Runs back left motor at full power */
	public void runBL(double spped) {
		bL.set(ControlMode.PercentOutput, spped);
	}
	public void speed(double left, double right) 
	{
		fL.set(ControlMode.Velocity,left);
		fR.set(ControlMode.Velocity,right);
		
	}
}
