package subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import constants.Const;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import objects.Action;

public class arms extends Action {
	DoubleSolenoid pistonLeftArm, pistionRightArm, pistonLU, pistonRU;
	Solenoid pistonLFA, pistonRFA, pistonLUA, pistonRUA;
	public boolean grab;
	public boolean release;
	public TalonSRX intakeLeftArm, intakeRightArm;

	public arms() {
		pistonLeftArm = new DoubleSolenoid(Const.armLFwd, Const.armLRev);
		pistionRightArm = new DoubleSolenoid(Const.armRFwd, Const.armRRev);
		intakeLeftArm = new TalonSRX(Const.armLIntake);
		intakeRightArm = new TalonSRX(Const.armRIntake);
	}

	/** Closes arms */
	public void grab() {
		pistonLeftArm.set(DoubleSolenoid.Value.kForward);
		pistionRightArm.set(DoubleSolenoid.Value.kForward);
	}
	/**Closes one side; true=right*/
	
	public void halfGrab(boolean side) {
		if(side) {
			pistonRFA.set(true);
		} else {
			pistonLFA.set(true);
		}
	}

	/** Opens arms */
	public void release() {
		pistonLeftArm.set(DoubleSolenoid.Value.kReverse);
		pistionRightArm.set(DoubleSolenoid.Value.kReverse);
	}
	

	/** Makes intake take in */
	public void takeIn(double spped) {
		intakeLeftArm.set(ControlMode.PercentOutput, spped);
		intakeRightArm.set(ControlMode.PercentOutput, spped);
	}

	/** Makes intake push out */
	public void takeOut(double spped) {
		intakeLeftArm.set(ControlMode.PercentOutput, -spped);
		intakeRightArm.set(ControlMode.PercentOutput, -spped);
	}

	/** Runs only the left intake motor */
	public void runL(double spped) {
		intakeLeftArm.set(ControlMode.PercentOutput, spped);
	}

	/** Runs only the right intake motor */
	public void runR(double spped) {
		intakeRightArm.set(ControlMode.PercentOutput, spped);
	}

	/** Stops all intake motors */
	public void stop() {
		intakeLeftArm.set(ControlMode.PercentOutput, 0);
		intakeRightArm.set(ControlMode.PercentOutput, 0);
	}

	/** First is left-forward(0), right-forward(1), left-up(2), right-up(3); second is off(0) rev(1) or fwd(2) */
	/*
	public void runPiston(int which, int state) {
		
		switch (state) {
		case 0:
			switch(which) {
			case 0:
				pistonLeftArm.set(DoubleSolenoid.Value.kOff);
				break;
			case 1:
				pistionRightArm.set(DoubleSolenoid.Value.kOff);
				break;
			case 2:
				pistonLU.set(DoubleSolenoid.Value.kOff);
				break;
			case 3:
				pistonRU.set(DoubleSolenoid.Value.kOff);
				break;
			}
		case 1:
			switch(which) {
			case 0:
				pistonLeftArm.set(DoubleSolenoid.Value.kReverse);
				break;
			case 1:
				pistionRightArm.set(DoubleSolenoid.Value.kReverse);
				break;
			case 2:
				pistonLU.set(DoubleSolenoid.Value.kReverse);
				break;
			case 3:
				pistonRU.set(DoubleSolenoid.Value.kReverse);
				break;
			}
		case 2:
			switch(which) {
			case 0:
				pistonLeftArm.set(DoubleSolenoid.Value.kForward);
				break;
			case 1:
				pistionRightArm.set(DoubleSolenoid.Value.kForward);
				break;
			case 2:
				pistonLU.set(DoubleSolenoid.Value.kForward);
				break;
			case 3:
				pistonRU.set(DoubleSolenoid.Value.kForward);
				break;
			}
		}

	}
	*/
	
	/** First is left-forward(0), right-forward(1), left-up(2), right-up(3); second is off(f) or on(t) */
	/*
	public void runPiston(int which, boolean state) {
		switch (which) {
		case 0:
			pistonLFA.set(state);
			break;
		case 1:
			pistonRFA.set(state);
			break;
		case 2:
			pistonLUA.set(state);
			break;
		case 3:
			pistonRUA.set(state);
			break;
	}
	}
	*/
}
