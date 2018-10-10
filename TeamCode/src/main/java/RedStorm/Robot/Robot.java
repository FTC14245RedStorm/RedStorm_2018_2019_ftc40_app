package RedStorm.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class Robot {

    public HardwareMap hwMap = null;
    public DcMotor rightDrive = null;
    public DcMotor leftDrive = null;
    public DcMotor liftMotor = null;
    public BNO055IMU imu = null;

    public BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    public Orientation angles;
    public double integratedZAxis;
    public double iza_lastHeading = 0.0;
    public double iza_deltaHeading;
    public float iza_newHeading;
    public Orientation iza_angles;

    public Robot() {

    }

    /**
     * This method will initialize the robot
     *
     * @param ahwMap hardware map for the robot
     */
    public void initialize(HardwareMap ahwMap) {

        // Save reference to hardware map
        hwMap = ahwMap;

        // Define and initialize motors, the names here are what appears
        // in the configuration file on the Robot Controller/Driver Station
        leftDrive = hwMap.get(DcMotor.class, "left_Back");
        rightDrive = hwMap.get(DcMotor.class, "right_Back");
        liftMotor = hwMap.get(DcMotor.class,  "motor_Lift");

        // Defines the directions the motors will spin, typically motors that
        // are mounted on the left side are mounted in such a way as that the
        // the motor will spin backwards, so we set the default direction to
        // to be REVERSE so that the left motor will spin forwards with respect
        // to the ROBOT.
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        rightDrive.setPower(0);
        leftDrive.setPower(0);
        liftMotor.setPower(0);

        // Set all motors to run without encoders.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        imu = hwMap.get(BNO055IMU.class, "imu");
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        imu.initialize(parameters);
    }

    /**
     * This method will set the power for the drive motors
     *
     *
     * @param leftBackMotorPower power setting for the left back motor
     * @param rightBackMotorPower power setting for the right back motor
     */
    public void setDriveMotorPower(double leftBackMotorPower, double rightBackMotorPower){

        /* Set the motor powers */
        leftDrive.setPower(leftBackMotorPower);
        rightDrive.setPower(rightBackMotorPower);

    }

    /**
     * This method will set the power for the lift motor
     * @param liftMotorMotorPower power setting for the lift motor
     */
    public void setLiftMotorPower(double liftMotorMotorPower){

        /* Set the Lift power */
        liftMotor.setPower(liftMotorMotorPower);

    }
}
