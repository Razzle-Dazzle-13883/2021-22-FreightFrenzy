package org.firstinspires.ftc.teamcode.Autonomous.Remote;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "RemoteBaseARM")
public class RemoteBaseARM extends LinearOpMode {


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



        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

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

        waitForStart();
        drive(-38*7, 38*7, 38*7, -38*7, .25);        //Move Backwords To Carasouel

        drive(-38*30, -38*30, -38*30, -38*30, .15);        //Move Backwords To Carasouel

        //drive(-38*2, -38*2, -38*2, 38*2, .25);        //Move Backwords To Carasouel


        sleep(1000);

        spinMotor.setPower(-1);
        sleep(4000);

        spinMotor.setPower(0);
        sleep(500);



        drive(-38*2, 38*2, 38*2, -38*2, .25);        //Move Backwords To Carasouel

        drive(38*30, 38*30, 38*30, 38*30, .25);        //Move Backwords To Carasouel
        drive(38*3, -38*3, -38*3, 38*7, .25);        //Move Backwords To Carasouel
        drive(38*20, 0, 0, 38*20, .25);        //Move Backwords To Carasouel
        drive(38*5, -38*5, -38*5, 38*5, .15);        //Move Backwords To Carasouel

        drive(38*100, 38*100, 38*100, 38*100, .35);    //Move to Warehouse


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

