package org.firstinspires.ftc.teamcode.Autonomous.SmallChassis.BlueRemote;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ARMAUTO")

public class ARMTEST extends LinearOpMode {


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
     int spinMotorPos;

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


        //IN CASE A WHEEL OR MOTOR IS REVERSED USED THIS
        //leftFront.setDirection((DcMotorSimple.Direction.REVERSE));


        leftFrontPos = 0;
        rightFrontPos = 0;
        leftBackPos = 0;
        rightBackPos = 0;
        armPos = 0;
        spinMotorPos = 0;


        waitForStart();

        drive(-38*25, 38*25, 38*25, -38*25, .5); //Strafe to Hub

        movingClaw.setTargetPosition(-130);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.4);
        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        drive(-38*6, -38*6, -38*6, -38*6, .25);  //up a bit

        claw.setPosition(0.0); //Drop

        drive(-38*4, -38*4, -38*4, -38*4, .2);   //back away

        movingClaw.setTargetPosition(0);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.2);
        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        drive(-38*20, 38*20, -38*20, 38*20, .6); //ROTATE

        drive(38*28, 38*28, 38*28, 38*28, .7);  //move closer to the carasouel

        drive(38*4, -38*4, -38*4, 38*4, .5);

        drive(38*2, 38*2, 38*2, 38*2, .2);  //move closer to the carasouel

        drive(38*8, -38*8, -38*8, 38*8, .3);

        carousel(360, 1);

        drive(-38*25, 38*25, 38*25, -38*25, .3); //diagnol squaring against the wall

        drive(-38*25, 38*25, 38*25, -38*25, .3); // move to warehouse
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


    private void carousel(int spinMotorTarget, double speed  ) {
        spinMotorPos += spinMotorTarget;
        spinMotor.setTargetPosition(spinMotorPos);
        spinMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinMotor.setPower(speed);

        while (opModeIsActive() && spinMotor.isBusy()) {
            idle();
        }
    }
}

