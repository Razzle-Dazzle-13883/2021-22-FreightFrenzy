package org.firstinspires.ftc.teamcode.TestCode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Test")
public class EncoderTest4 extends LinearOpMode {


    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor spinMotor;

    private DcMotor wheelIntake1;
    private DcMotor wheelintake2;

    //ENcoder Positions

    private int leftFrontPos;
    private int rightFrontPos;
    private int leftBackPos;
    private int rightBackPos;


    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");

        wheelIntake1 = hardwareMap.get(DcMotor.class, "wheelIntake1");
        wheelintake2 = hardwareMap.get(DcMotor.class, "wheelintake2");

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

        drive(1000, 1000, -1000, 1000, 1.0);
        drive(-1000, -1000, -1000, -1000, 1.0);
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

