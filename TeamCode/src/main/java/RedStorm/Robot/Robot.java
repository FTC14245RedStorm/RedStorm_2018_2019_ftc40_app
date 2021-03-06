package RedStorm.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static RedStorm.Constants.RobotConstants.ANDYMARK_NEVEREST_40_PULSES;
import static RedStorm.Constants.RobotConstants.DRIVE_GEAR_RATIO;
import static RedStorm.Constants.RobotConstants.DRIVE_WHEEL_CIRCUMFERENCE;

public class Robot {

    public HardwareMap hwMap = null;
    public DcMotor rightDrive = null;
    public DcMotor leftDrive = null;
    public DcMotor liftMotor = null;
    public BNO055IMU imu = null;
    public Servo teamMarkerArm;
    public Servo teamMarkerGrip;

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

        teamMarkerArm = hwMap.get(Servo.class, "marker_Arm");
        teamMarkerGrip = hwMap.get(Servo.class, "marker_Grip");

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

        initializeServosAutonomous();
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

    /**
     * This method will reset the encoder count of each motor to 0. It should be used before runWithEncoders
     * and getEncoderCount when strafing.
     */
    public void resetEncoders() {

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * This method will set the mode of all of the motors to run using encoder
     */
    public void runWithEncoders() {

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * This method will set the mode of all of the drive train motors to run without encoder
     */
    public void runWithoutEncoders() {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    /**
     * This method will return COUNTS after it is calculated from distance
     *
     * @param distance the desired distance in inches the robot will travel
     * @return counts - the number of encoder counts the robot will travel that is equal
     * to the number of inches
     */
    public double calculateEncoderCOUNTS(double distance) {

        double encoderCounts;
        double rotations;

        // Calculate the number of rotations for the given drive wheel size for the supplied distance
        rotations = (distance / DRIVE_WHEEL_CIRCUMFERENCE) * DRIVE_GEAR_RATIO;

        // Calculate the number of encoder counts for the given distance
        encoderCounts = ANDYMARK_NEVEREST_40_PULSES * rotations; //calculate encoder counts for given distance

        return encoderCounts;
    }

    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getDriveEncoderCount() {
        double leftEncoderCount;
        double rightEncoderCount;
        double averageEncoderCount;

        leftEncoderCount = leftDrive.getCurrentPosition();      // Get the current encoder count for the left motor
        rightEncoderCount = rightDrive.getCurrentPosition();    // Get the current encoder count for the right motor

        averageEncoderCount = (leftEncoderCount + rightEncoderCount) / 2.0;  // Calculate the average

        return averageEncoderCount;



    }
    public double getLiftEncoderCount() {
        double LiftEncoderCount;

        LiftEncoderCount = liftMotor.getCurrentPosition() //Get the current encoder count for the lift motor

        return LiftEncoderCount
    }
    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getLeftDriveEncoderCounts() {
        double leftEncoderCount;

        leftEncoderCount = leftDrive.getCurrentPosition();      // Get the current encoder count for the left motor




        return leftEncoderCount;
    }
    /**
     * This method will return the average encoder count from the left and right drive motors
     * @return averageEncoderCount - the average encoder count from the left and right drive motors
     */
    public double getRightDriveEncoderCounts() {
        double rightEncoderCount;



        rightEncoderCount = rightDrive.getCurrentPosition();    // Get the current encoder count for the right motor


        return rightEncoderCount;
    }
    /**
     * This method will initialize all of the servos in autonomous
     */
    public void initializeServosAutonomous() {

        teamMarkerArm.setPosition(0.0);
        teamMarkerGrip.setPosition(0.5);


    }
    /**
     * This method will initialize all of the servos in autonomous
     */
    public void setTeamMarkerArm(double servoPosition) {

        teamMarkerArm.setPosition(servoPosition);



    }
    /**
     * This method will initialize all of the servos in autonomous
     */
    public void setTeamMarkerGrip(double servoPosition) {


        teamMarkerGrip.setPosition(servoPosition);



    }
    //
    //
    }
