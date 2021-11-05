package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "CustomDectectionWebcam3")
public class CustomDectectionWebcam3 extends LinearOpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;


    private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/CustomDectection.tflite";
    private static final String LABEL_FIRST_ELEMENT = "LEVEL1";
    private static final String LABEL_SECOND_ELEMENT = "LEVEL2";
    private static final String LABEL_THIRD_ELEMENT = "LEVEL3";


    private static final String VUFORIA_KEY =
            "AQ5Ga0r/////AAABmY3dPUqJZE+9lWYfmEZA8GxAAge/a1bHQ8udF/kH7f+UjIKGtq1629AE2grZt0+aJjE2DUw3Vd7X6lP23pVERIZtyIHHEqjz5/6o2Fvnjh0Hi3Bc34cG8W8JBoxzq8PDJ6ucRNP83vFx4dyaBuu/S3Il+YyDbTPnMC0eGdjWMyolP9d82QW2b/rsrJln8qnQIoI0qBV2YbUrV/5xyNZ9f+eyOhk3X/hgNsRKFoexNZERZbYxEU3d37adTXMMY6m2msmOP+H9/PLpgMMe1hRfrQbL+iNmLPVZqPZaRevhDFa57biNixyRuH8wERJlHI49BvrNeGshYa7yaQnNU7eGjSiyZt6DvopUXWXiyIqlcuM6";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");


        //rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Motors and Servo Initialized");
        telemetry.update();

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            tfod.setZoom(1.5, 1.78);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            int counter = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            while (counter <= 35) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 0 ) {
                            telemetry.addData("TFOD", "No items detected.");
                            d += 1;
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
                                    telemetry.addData("Target Zone", "1");
                                    a += 1;
                                } else if (recognition.getLabel().equals("LEVEL2")) {
                                    telemetry.addData("Target Zone", "2");
                                    b += 1;
                                } else if (recognition.getLabel().equals("LEVEL3")) {
                                    telemetry.addData("Target Zone", "3");
                                    c += 1;
                                }


                            }
                        }

                        telemetry.update();
                        counter += 1;
                    }
                }
            }
            if (a > b && a > c && a > d) {
                Level1();
                telemetry.addData("Status", "Level 1 Dectecting");
                telemetry.update();
            } else if (b > a && b > c && b > d){
                Level2();
                telemetry.addData("Status", "Level 2 Dectecting");
                telemetry.update();
            } else if (c > a && c > b && c > d) {
                Level3();
                telemetry.addData("Status", "Level 3 Dectecting");
                telemetry.update();
            } else{
                telemetry.addData("Target Zone", "Unknown");
                telemetry.update();
            }
            telemetry.update();
            sleep(7500);
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT, LABEL_THIRD_ELEMENT);
    }

    //Bottom
    public void Level1() {
        telemetry.addData("Status", "Level 1 Selected");
        telemetry.update();
        sleep(1500);


    }

    //Middle
    public void Level2() {
        telemetry.addData("Status", "Level 2 Selected");
        telemetry.update();
        sleep(1500);


    }

    //Top
    public void Level3() {
        telemetry.addData("Status", "Level 3 Selected");
        telemetry.update();
        sleep(1500);



    }
}