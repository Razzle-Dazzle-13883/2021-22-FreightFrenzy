package org.firstinspires.ftc.teamcode.Autonomous.SmallChassis.Blue.Duck;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BlueDuckSRemote")

public class BlueDuckHalf extends LinearOpMode {

     DcMotor leftFront;
     DcMotor rightFront;
     DcMotor leftBack;
     DcMotor rightBack;
     DcMotor spinMotor;

     int leftFrontPos;
     int rightFrontPos;
     int leftBackPos;
     int rightBackPos;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");

        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontPos = 0;
        rightFrontPos = 0;
        leftBackPos = 0;
        rightBackPos = 0;

        waitForStart();



        //38 ticks = 1 inch

        //moving to the side (hub)
        drive(38*24, -38*24, -38*24, 38*24, .45);
        //Moving to the Hub
        drive(-38*7, -38*7, -38*7, -38*7, .3);

        //ARM

        //Move Diagnolly onto wall and square against the wall
        drive(38*0, 38*10, 38*10, 38*0, .6);
        //move to carasouel
        drive(-38*10, 38*10, 38*10, -38*10, .8);

        //Rotate Carasouel
        spinMotor.setPower(.70);
        sleep(3500);
        spinMotor.setPower(0);
        sleep(750);

        //turn  to face storage
        drive(38*30, -38*30, 38*30, -38*30, .25);
        //square agaisnt  the wall
        drive(38*5, -38*5, -38*5, 38*5, .45);
        //move into storage
        drive(-38*80, -38*80, -38*80, -38*80, .3);

    }

    private void drive(int leftFrontTarget, int rightFrontTarget, int leftBackTarget, int rightBackTarget, double speed  ) {
        leftFrontPos += leftFrontTarget;
        rightFrontPos += rightFrontTarget;
        leftBackPos += leftBackTarget;
        rightBackPos += rightBackTarget;

        leftFront.setTargetPosition(leftFrontPos);
        rightFront.setTargetPosition(rightFrontPos);
        leftBack.setTargetPosition(leftBackPos);
        rightBack.setTargetPosition(rightBackPos);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);

        while (opModeIsActive() && leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy()) {
            idle();
        }
    }
}
