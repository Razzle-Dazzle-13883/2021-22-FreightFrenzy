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

package org.firstinspires.ftc.teamcode.Autonomous.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "VisionSensor", group = "Concept")
@Disabled

public class VisionSensor extends LinearOpMode {

    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;

    DcMotor spinMotor;

    int leftFrontPos;
    int rightFrontPos;
    int leftBackPos;
    int rightBackPos;


    private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/blue.tflite";
    private static final String[] LABELS = {
      "LEVEL1",
      "LEVEL2",
      "LEVEL3"
    };

    private static final String VUFORIA_KEY =
            "Adahhfr/////AAABmRGLi4HJekbOoQzn7r/yjGgIS3VZVB/CZv99kmxg6FzRwsRVPCTlx7vC2DVesU8VloA/tPCHGWGPPmEGYyTi95LyPIGNI1Rr2f/dJu5VVibtpiPexoU1OuAk/dOQZwMpCKuBBmJBaSEMvLvwYe8Bvu1k5oms7r+2a1kXBLuZhXMRRhIUcQ2dI+BYpWX1SN4XkBnpjCyxbI1mM3jFHk1nZdXvto4FkuCTK3cTs4saljczQlOqBPNhqwKPx2PiBc0HnJB6EN93RBUyiqzjNIO/24a+NIFeyq3Vt+Y6jvABriqWWSBJOWyqAqibSoQjDrUcaA30vn0F4FMU2EOtqNOKM0SMTwXKKpleop/qzMkoJOF2";

    private VuforiaLocalizer vuforia;


    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        spinMotor = hardwareMap.dcMotor.get("leftBack");

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            tfod.setZoom(1, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();


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

        if (opModeIsActive()) {
            int counter = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            while (counter <= 5) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 0 ) {
                            // empty list.  no objects recognized.
                            telemetry.addData("TFOD", "No items detected.");
                            c += 1;
                        } else {
                            // list is not empty.
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                // check label to see which target zone to go after.
                                if (recognition.getLabel().equals("LEVEL1")) {
                                    telemetry.addData("LEVEL1", "1");
                                    a += 1;
                                } else if (recognition.getLabel().equals("LEVEL2")) {
                                    telemetry.addData("LEVEL2", "2");
                                    b += 1;
                                } else if (recognition.getLabel().equals("LEVEL3")) {
                                telemetry.addData("LEVEL3", "3");
                                    c += 1;
                                }
                            }
                        }

                        telemetry.update();
                        counter += 1;
                    }
                }
            }
            if (a > b && a > c) {
                targetA();
            } else if (b > a && b > c){
                targetB();
            } else if (c > a && c > b) {
                targetC();
            } else{
                telemetry.addData("Target Zone", "Unknown");
                telemetry.update();
            }

            sleep(5000);
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromFile(TFOD_MODEL_ASSET, LABELS);
    }
    //LEVEL 1 (Bottom)
    public void targetA() {
        telemetry.addData("Status", "LEVEL1");
        telemetry.update();

        //drive(-38*24, 38*24, 38*24, -38*24, .3);
    }

    //LEVEL 2 (MID)
    public void targetB() {
        telemetry.addData("Status", "LEVEL2");
        telemetry.update();
        spinMotor.setPower(-.5);
        sleep(500);

    }

    //LEVEL 3 (TOP)
    public void targetC() {
        telemetry.addData("Status", "LEVEL3");
        telemetry.update();
        spinMotor.setPower(-.3);
        sleep(1500);

    }

}