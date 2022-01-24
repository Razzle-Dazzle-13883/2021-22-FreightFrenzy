package org.firstinspires.ftc.teamcode.Autonomous.SmallChassis.BlueRemote;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "REMOTEBLUE")
@Disabled

public class BlueRemoteDuck extends LinearOpMode {


     DcMotor leftFront;
     DcMotor rightFront;
     DcMotor leftBack;
     DcMotor rightBack;
     DcMotor spinMotor;
     DcMotor movingClaw;

     Servo claw = null;


     int leftFrontPos;
     int rightFrontPos;
     int leftBackPos;
     int rightBackPos;
     int armPos;


    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");
        movingClaw = hardwareMap.get(DcMotor.class, "movingClaw");


        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(1.0);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        movingClaw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //IN CASE A WHEEL OR MOTOR IS REVERSED USED THIS
        //leftFront.setDirection((DcMotorSimple.Direction.REVERSE));


        leftFrontPos = 0;
        rightFrontPos = 0;
        leftBackPos = 0;
        rightBackPos = 0;
        armPos = 0;


        waitForStart();


        //drive(1000, 1000, -1000, 1000, 0.25);
        // the first number will move the leftfront wheel 1000 ticks, the 2nd will move the righfront wheel backwards 100 ticks, and so on
        //the last number, (0.25 in this case) will make them move at a speed of 0.25


        //1 inch is 38

        //going up a bit
        drive(38*4, 38*4, 38*4, 38*4, .7);

        drive(-38*6, 38*6, -38*6, 38*6, .25);


        //ARM
        movingClaw.setTargetPosition(-130);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.4);

        //closer to hub
        drive(-38*8, -38*8, -38*8, -38*8, .3);

        claw.setPosition(0.0);



        movingClaw.setPower(0);


        drive(38*8, 38*8, 38*8, 38*8, .6);   //back away

        drive(38*20, -38*20, 38*20, -38*20, .25); //turn more

        drive(38*12, 38*12, 38*12, 38*12, .6); //move down

        drive(38*4, -38*4, -38*4, 38*4, .3);  //move closer to the carasouel

        spinMotor.setPower(-.70);
        sleep(4500);
        spinMotor.setPower(0);
        sleep(1000);

        drive(-38*8, -38*8, -38*8, -38*8, .7);

        drive(-38*15, 0, 0, -38*15, .4);

        drive(-38*70, -38*70, -38*70, -38*70, .7);
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

    private void armTopPos() {
        movingClaw.setTargetPosition(-130);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.4);

        while(movingClaw.isBusy()) {
            idle();
        }

    }
    private void armDrive(int leftFrontTarget, int rightFrontTarget, int leftBackTarget, int rightBackTarget, int armTarget, double speed ) {
        leftFrontPos += leftFrontTarget;
        rightFrontPos += rightFrontTarget;
        leftBackPos += leftBackTarget;
        rightBackPos += rightBackTarget;
        armPos += armTarget;

        leftFront.setTargetPosition(leftFrontPos);
        rightFront.setTargetPosition(rightFrontPos);
        leftBack.setTargetPosition(leftBackPos);
        rightBack.setTargetPosition(rightBackPos);
        movingClaw.setTargetPosition(armPos);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setMode((DcMotor.RunMode.RUN_TO_POSITION));

        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);
        movingClaw.setPower(4);

        while (opModeIsActive() && leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy()) {
            idle();
        }
    }


}

