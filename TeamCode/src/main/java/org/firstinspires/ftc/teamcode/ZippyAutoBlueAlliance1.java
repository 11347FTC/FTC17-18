package org.firstinspires.ftc.teamcode;

import android.media.MediaCodecInfo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.sql.Time;

/**
 * Created by Ganondorf on 11/16/2016.
 */
@Autonomous (name="ZippyAutoBlue1", group="Zippy")
public class ZippyAutoBlueAlliance1 extends LinearOpMode {
    //DcMotor frontLeftMotor = null;
    //DcMotor frontRightMotor = null;
    //For Mechanum
    DcMotor backLeft = null;
    DcMotor backRight = null;
    Servo leftPoker;
    Servo rightPoker;
    //Low Goal Shooter Motor
    DcMotor lowGoal = null;
    ColorSensor beacon = null;
    private ElapsedTime runtime = new ElapsedTime();


    double DriveSpeed = 0.5;
    double TurnSpeed = 0.6;
    double ShootPower = 0.5;
    //complete

    @Override
    public void runOpMode() {
        //frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        //frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        //For Mechanum
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        leftPoker = hardwareMap.servo.get("leftPoker");
        rightPoker = hardwareMap.servo.get("rightPoker");

        //frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        //frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        //For Mechanum

        //Right Motor needs to be reversed
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        //frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftPoker.setPosition(0);
        rightPoker.setPosition(1);
        //Low Goal Shooter
        lowGoal = hardwareMap.dcMotor.get("lowGoal");
        //Color Sensor
        beacon = hardwareMap.colorSensor.get("beacon");

        waitForStart();
        beacon.enableLed(false);
        leftPoker.setPosition(0);
        rightPoker.setPosition(1);

        //Move Forward for 5ft
        DriveForwardTime(DriveSpeed,1000);

        //Turn 90 degrees
        TurnRightTime(TurnSpeed, 350);
        //Move Forward for almost 5ft
        DriveForwardTime(DriveSpeed, 980);
        //Sense beacons
        TurnRightTime(TurnSpeed,100);
        SenseBlue();
        StraightenLeft(TurnSpeed,100);
        DriveForwardTime(DriveSpeed,20);
        ResetPoker();
        DriveBackwardTime(DriveSpeed,400);
        TurnLeftTime(TurnSpeed, 350);
        DriveForwardTime(DriveSpeed, 800);
        TurnRightTime(TurnSpeed, 350);
        DriveForwardTime(DriveSpeed, 380);
        TurnRightTime(TurnSpeed,100);
        SenseRed();
        StraightenLeft(TurnSpeed,100);
        DriveForwardTime(DriveSpeed, 20);
        ResetPoker();
        DriveBackwardTime(DriveSpeed, 20);
        TurnRightTime(TurnSpeed, 525);
        DriveForwardTime(DriveSpeed, 1000);
        TurnLeftTime(TurnSpeed, 350);
        DriveForwardTime(DriveSpeed, 1250);
        Shoot(ShootPower, 2000);

    }
    //A lot of Methods for Auto
    public void DriveForward(double power){
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    public void DriveBackward(double power){
        backLeft.setPower(-power);
        backRight.setPower(-power);
    }
    public void TurnLeft(double power){
        backLeft.setPower(-power);
        backRight.setPower(power);
    }
    public void TurnRight(double power){
        backLeft.setPower(power);
        backRight.setPower(-power);
    }
    public void TimeElapsed(long time) {
        sleep(time);
    }
    public void DriveForwardTime(double power, long time){
        DriveForward(power);
        TimeElapsed(time);
    }
    public void DriveBackwardTime(double power, long time){
        DriveBackward(power);
        TimeElapsed(time);
    }
    public void TurnLeftTime (double power, long time){
        TurnLeft(power);
        TimeElapsed(time);
    }
    public void TurnRightTime (double power, long time){
        TurnRight(power);
        TimeElapsed(time);
    }
    public void PokeRight (double position){
        rightPoker.setPosition(position);
    }
    public void PokeLeft (double position){
        leftPoker.setPosition(position);
    }
    public void SenseRed (){
        telemetry.addData("3 Red ", beacon.red());
        telemetry.addData("4 Green ", beacon.green());
        telemetry.addData("5 Blue ", beacon.blue());

        if (beacon.red() > beacon.blue() || beacon.red() > beacon.green()) {
            PokeLeft(0.75);
            TimeElapsed(500);
        } else if (beacon.blue() > beacon.red() || beacon.blue() > beacon.green()) {
            PokeRight(0.25);
            TimeElapsed(500);
        }
        else {
            //Just incase it doesn't sense anything

            TimeElapsed(500);

        }
    }
    public void SenseBlue (){
        telemetry.addData("3 Red ", beacon.red());
        telemetry.addData("4 Green ", beacon.green());
        telemetry.addData("5 Blue ", beacon.blue());

        if (beacon.blue() > beacon.red() || beacon.blue() > beacon.green()) {
            PokeLeft(0.75);
            TimeElapsed(500);
        } else if (beacon.red() > beacon.blue() || beacon.red() > beacon.green()) {
            PokeRight(0.25);
            TimeElapsed(500);
        }
        else {
            //Just incase it doesn't sense anything

            TimeElapsed(500);
        }
    }
    public void Shoot (double power, long time){
        lowGoal.setPower(power);
        TimeElapsed(time);
    }
    public void StraightenRight (double power, long time){
        backRight.setPower(-power);
        TimeElapsed(time);
    }
    public void StraightenLeft (double power, long time){
        backLeft.setPower(-power);
        TimeElapsed(time);
    }
    public void ResetPoker (){
        leftPoker.setPosition(0);
        rightPoker.setPosition(1);
    }
}