

import static org.junit.Assert.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.simulation.;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.simulation.PCMSim;
import frc.robot.Intake;

//import frc.robot.Constants.IntakeConstants;
import org.junit.*;

public class IntakeTest {
  public static final double DELTA = 1e-2; // acceptable deviation range
  Intake intake;
  PWMSim simMotor;
  PCMSim simPCM;
  //DoubleSolenoidSim simPiston;

  @Before // this method will run before each test
  public void setup() {
    assert HAL.initialize(500, 0); // initialize the HAL, crash if failed
    intake = new Intake(); // create our intake
    simMotor = new PWMSim(1); // create our simulation PWM motor controller
    simPCM = new PCMSim(1);
    
    //simPiston = new DoubleSolenoidSim(2, 3, 4); // create our simulation solenoid
  }

  @After // this method will run after each test
  public void shutdown() throws Exception {
    intake.close(); // destroy our intake object
  }

  @Test // marks this method as a test
  public void doesntWorkWhenClosed() {
    intake.retract(); // close the intake
    intake.activate(0.5); // try to activate the motor
    assertEquals(0.0, simMotor.getSpeed(), DELTA); // make sure that the value set to the motor is 0
  }

  @Test
  public void worksWhenOpen() {
    intake.deploy();
    intake.activate(0.5);
    assertEquals(0.5, simMotor.getSpeed(), DELTA);
  }

  @Test
  public void retractTest() {
    intake.retract();
    assertFalse("Solenoid1 Fwd", simPCM.getSolenoidOutput(2));
    assertTrue("Solenoid1 Rwd", simPCM.getSolenoidOutput(3));
    //assertEquals(DoubleSolenoid.Value.kReverse, simPiston.get());
  }

  @Test
  public void deployTest() {
    intake.deploy();
    assertTrue("Solenoid1 Fwd", simPCM.getSolenoidOutput(2));
    assertFalse("Solenoid1 Rwd", simPCM.getSolenoidOutput(3));
    //assertEquals(DoubleSolenoid.Value.kForward, simPiston.get());
  }
}