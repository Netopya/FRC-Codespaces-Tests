package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMSparkMax;

public class Intake implements AutoCloseable {
  private PWMSparkMax motor;
  private DoubleSolenoid piston;

  public Intake() {
    motor = new PWMSparkMax(1);
    piston = new DoubleSolenoid(1, 2, 3);
  }

  public void deploy() {
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void retract() {
    piston.set(DoubleSolenoid.Value.kReverse);
    motor.set(0); // turn off the motor
  }

  public void activate(double speed) {
    if (piston.get() == DoubleSolenoid.Value.kForward) {
      motor.set(speed);
    } else { // if piston isn't open, do nothing
      motor.set(0);
    }
  }

  @Override
  public void close() throws Exception {
    piston.close();
    motor.close();
  }
}