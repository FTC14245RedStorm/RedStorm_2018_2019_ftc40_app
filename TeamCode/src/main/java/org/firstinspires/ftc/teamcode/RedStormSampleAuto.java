package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import RedStorm.Robot.Robot;

@Autonomous (name="Sample RedStorm Autonomous Program", group="Samples")

/**
 * Created by Steve Kocik as a sample for RedStorm to build off of...
 */

public abstract class RedStormSampleAuto extends LinearOpMode {


    public Robot robot = new Robot();    // Create a new instance of the robot

    @Override
    public void runOpMode() throws InterruptedException {


        // Initialize and set up the robot's drive motors
        robot.initialize(hardwareMap);             // Initialize the robot
        robot.resetEncoders();                     // Reset the encoder counts
        robot.runWithEncoders();                   // Tell the motors to run with encoders

        // Wait for the start button to be pushed
        waitForStart();

        // Calculate the number of encoder counts to travel for the defined distance

        double encoderCountstoTravel = robot.calculateEncoderCOUNTS(24);   // Calculate the number of encoder counts to travel 24 inches


        // While the autonomous period is still active AND the robot has not reached the number
        // of encoder counts to travel 24 inches
        while(opModeIsActive() && robot.getDriveEncoderCount() < encoderCountstoTravel) {

            robot.setDriveMotorPower(0.5, 0.50);   // Set power to 50%
        }

        robot.setDriveMotorPower(0.0,0.0);         // Motors stop
    }
}