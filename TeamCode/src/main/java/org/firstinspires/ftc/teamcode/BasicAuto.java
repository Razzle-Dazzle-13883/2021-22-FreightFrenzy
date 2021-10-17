package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;



@Autonomous(name = "BasicAuto")
    public class BasicAuto extends LinearOpMode {
    private DcMotor rightFront;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor leftBack;


    @Override
    public void runOpMode() throws InterruptedException {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        //.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();


        rightFront.setPower(0.0);
        rightBack.setPower(0.0);
        leftFront.setPower(0.0);
        leftBack.setPower(0.0);

        sleep(500);

        rightFront.setPower(1.0);
        rightBack.setPower(1.0);
        leftFront.setPower(1.0);
        leftBack.setPower(1.0);

        sleep(3000);


    }
}