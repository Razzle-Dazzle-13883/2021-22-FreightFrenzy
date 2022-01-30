/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomous.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "WebcamDetection")
public class WebcamDetection extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/remoteEvent.tflite";
    private static final String[] LABELS = {
            "CappingElement"
    };

    private static final String VUFORIA_KEY =
            "AQ5Ga0r/////AAABmY3dPUqJZE+9lWYfmEZA8GxAAge/a1bHQ8udF/kH7f+UjIKGtq1629AE2grZt0+aJjE2DUw3Vd7X6lP23pVERIZtyIHHEqjz5/6o2Fvnjh0Hi3Bc34cG8W8JBoxzq8PDJ6ucRNP83vFx4dyaBuu/S3Il+YyDbTPnMC0eGdjWMyolP9d82QW2b/rsrJln8qnQIoI0qBV2YbUrV/5xyNZ9f+eyOhk3X/hgNsRKFoexNZERZbYxEU3d37adTXMMY6m2msmOP+H9/PLpgMMe1hRfrQbL+iNmLPVZqPZaRevhDFa57biNixyRuH8wERJlHI49BvrNeGshYa7yaQnNU7eGjSiyZt6DvopUXWXiyIqlcuM6\n";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor spinMotor;
    DcMotor movingClaw;
    Servo claw;

    int leftFrontPos;
    int rightFrontPos;
    int leftBackPos;
    int rightBackPos;


    @Override
    public void runOpMode() {

        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        spinMotor = hardwareMap.dcMotor.get("spinMotor");
        movingClaw = hardwareMap.dcMotor.get("movingClaw");

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(.9);


        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();


            tfod.setZoom(1, 16.0/9.0);
        }


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        movingClaw.setDirection(DcMotorSimple.Direction.REVERSE);



        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        movingClaw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontPos = 0;
        rightFrontPos = 0;
        leftBackPos = 0;
        rightBackPos = 0;


        telemetry.addData(">", "DONE INTIALIZING | CHECK CAMERA STREAM FOR POSITION");
        telemetry.update();


        waitForStart();

        if (opModeIsActive()) {
            int counter = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            while (counter <= 3) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());



                        //POTENCIAL ISSUE WITH GET LEFT NOT GETTING THE LABEL
                        if (recognition.getLeft() < 300) {
                            telemetry.addData("LEVEL3", recognition.getLeft());
                            a += 1; //level1
                        } else if (recognition.getLeft() > 800) {
                            telemetry.addData("LEVEL3", recognition.getLeft());
                            c += 1;  //level3
                        } else {
                            telemetry.addData("LEVEL2", recognition.getLeft());
                            b += 1;  //level2
                        }



                        i++;
                      }
                      telemetry.update();
                    }
                }
                if (a > b && a > c) {
                    level3();
                } else if (b > a && b > c){
                    level2();
                } else if (c > a && c > b) {
                    level1();
                } else{
                    telemetry.addData("Target Zone", "Unknown");
                    telemetry.update();
                }
                telemetry.update();
                sleep(5000);
            }
                if (tfod != null) {
                    tfod.shutdown();
                }

        }

        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
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





    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.8f;
       tfodParameters.isModelTensorFlow2 = true;
       tfodParameters.inputSize = 320;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromFile(TFOD_MODEL_ASSET, LABELS);
    }


    //BOTTOM
    public void level1() {
        telemetry.addData("Bottom:", "LEVEL1");
        telemetry.update();


        movingClaw.setTargetPosition(45);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.2);

        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sleep(1000);

        drive(-38*7, 38*7, 38*7, -38*7, .25);        //Move Backwords To Carasouel

        drive(-38*30, -38*30, -38*30, -38*30, .15);        //Move Backwords To Carasouel

        //drive(-38*2, -38*2, -38*2, 38*2, .25);        //Move Backwords To Carasouel


        sleep(1000);

        spinMotor.setPower(-1);
        sleep(4000);

        spinMotor.setPower(0);
        sleep(500);



        drive(-38*43, 38*43, 38*43, -38*43, .25);        //Move Backwords To Carasouel

        drive(-38*10, -38*10, -38*10, -38*10, .25);

        drive(38*40, 38*40, 38*40, 38*40, .25);

        claw.setPosition(0.0);

        sleep(2000);

        drive(-38*15, -38*15, -38*15, -38*15, .25);        //Move Backwords To Carasouel

        drive(38*50, -38*50, -38*50, 38*50, .25);

        movingClaw.setTargetPosition(0);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.35);
        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        drive(38*30, 0, 0, 38*30, 6);        //Move Backwords To Carasouel


        drive(38*100, 38*100, 38*100, 38*100, 6);    //Move to Warehouse



    }


    //MID (LIKE MHA)
    public void level2() {
        telemetry.addData("Mid:", "LEVEL2");
        telemetry.update();
        movingClaw.setTargetPosition(75);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.2);

        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sleep(1000);

        drive(-38*7, 38*7, 38*7, -38*7, .25);        //Move Backwords To Carasouel

        drive(-38*30, -38*30, -38*30, -38*30, .15);        //Move Backwords To Carasouel

        //drive(-38*2, -38*2, -38*2, 38*2, .25);        //Move Backwords To Carasouel


        sleep(1000);

        spinMotor.setPower(-1);
        sleep(4000);

        spinMotor.setPower(0);
        sleep(500);



        drive(-38*43, 38*43, 38*43, -38*43, .25);        //Move Backwords To Carasouel

        drive(-38*10, -38*10, -38*10, -38*10, .25);

        drive(38*40, 38*40, 38*40, 38*40, .25);

        claw.setPosition(0.0);

        sleep(2000);

        drive(-38*15, -38*15, -38*15, -38*15, .25);        //Move Backwords To Carasouel

        drive(38*50, -38*50, -38*50, 38*50, .25);

        movingClaw.setTargetPosition(0);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.6);
        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        drive(38*30, 0, 0, 38*30, 6);        //Move Backwords To Carasouel


        drive(38*100, 38*100, 38*100, 38*100, 6);    //Move to Warehouse
    }



    //TOP
    public void level3() {
        telemetry.addData("Top:", "LEVEL3");
        telemetry.update();
        movingClaw.setTargetPosition(140);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.2);

        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sleep(1000);

        drive(-38*7, 38*7, 38*7, -38*7, .25);        //Move Backwords To Carasouel

        drive(-38*30, -38*30, -38*30, -38*30, .15);        //Move Backwords To Carasouel

        //drive(-38*2, -38*2, -38*2, 38*2, .25);        //Move Backwords To Carasouel


        sleep(1000);

        spinMotor.setPower(-1);
        sleep(4000);

        spinMotor.setPower(0);
        sleep(500);



        drive(-38*43, 38*43, 38*43, -38*43, .25);        //Move Backwords To Carasouel

        drive(-38*15, -38*15, -38*15, -38*15, .4);

        drive(38*50, 38*50, 38*50, 38*50, .25);

        claw.setPosition(0.0);

        sleep(2000);

        drive(-38*15, -38*15, -38*15, -38*15, .25);        //Move Backwords To Carasouel

        drive(38*50, -38*50, -38*50, 38*50, .25);

        movingClaw.setTargetPosition(0);
        movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movingClaw.setPower(.6);
        movingClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        drive(38*30, 0, 0, 38*30, 6);        //Move Backwords To Carasouel
        drive(38*100, 38*100, 38*100, 38*100, 6);    //Move to Warehouse
    }
}


