package org.firstinspires.ftc.teamcode.Autonomous.BlueAuto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BlueBasePosition2")
public class BlueBasePosition2 extends LinearOpMode {


     DcMotor leftFront;
     DcMotor rightFront;
     DcMotor leftBack;
     DcMotor rightBack;
     DcMotor spinMotor;



    //ENcoder Positions

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


        //IN CASE A WHEEL OR MOTOR IS REVERSED USED THIS
        //leftFront.setDirection((DcMotorSimple.Direction.REVERSE));


        leftFrontPos = 0;
        rightFrontPos = 0;
        leftBackPos = 0;
        rightBackPos = 0;

        waitForStart();


        //drive(1000, 1000, -1000, 1000, 0.25);
        // the first number will move the leftfront wheel 1000 ticks, the 2nd will move the righfront wheel backwards 100 ticks, and so on
        //the last number, (0.25 in this case) will make them move at a speed of 0.25



        //1 inch is 38

        //moving to the side
        drive(-38*24, 38*24, 38*24, -38*24, .25);
       // moves up
        drive(-38*10, -38*10, -38*10, -38*10, .25);
        //right
        drive(-38*10, 38*10, 38*10, -38*10, .25);
        //down
        drive(38*2, 38*2, 38*2, 38*2, .25);

        drive(-38*1, -38*1, 38*1, 38*1, .25);

        spinMotor.setPower(.30);
        sleep(5000);
        spinMotor.setPower(0);
        sleep(2000);

        drive(-38*22, -38*23, -38*22, -38*23, .25);



        //drive(-38*43, -38*43, -38*43, -38*43, .5);






















        //drive(-537, -537, -537, -537, -.5);
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
