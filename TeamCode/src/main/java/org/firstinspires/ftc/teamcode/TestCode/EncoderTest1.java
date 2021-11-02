
package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Encoder test 3", group="Pushbot")
public class EncoderTest1 extends LinearOpMode {

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.8;
    static final double     TURN_SPEED              = 0.6;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor spinMotor;

    private DcMotor wheelIntake1;
    private DcMotor wheelintake2;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition());
        telemetry.update();

        waitForStart();

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        sleep(1000);
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(1.0,  3,  3, 3, 3);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(2.0,  -3,  -3, -3, -3);   // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(3.0,  3,  3, 3, 3);   // S3: Reverse 24 Inches with 4 Sec timeout

        sleep(1000);
        leftFront.setPower(1);
        rightFront.setPower(1);
        leftBack.setPower(1);
        rightBack.setPower(1);
        sleep(1000);


    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed, double leftFrontInches, double rightFrontInches, double leftBackInches, double rightBackInches) {
        int newLFT;
        int newRFT;
        int newLBT;
        int newRBT;

        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLFT = leftFront.getCurrentPosition() + (int)(leftFrontInches * COUNTS_PER_INCH);
            newRFT = rightFront.getCurrentPosition() + (int)(rightFrontInches * COUNTS_PER_INCH);
            newLBT = leftBack.getCurrentPosition() + (int)(leftBackInches * COUNTS_PER_INCH);
            newRBT = rightBack.getCurrentPosition() + (int)(rightBackInches * COUNTS_PER_INCH);

            leftFront.setTargetPosition(newLFT);
            rightFront.setTargetPosition(newRFT);
            leftBack.setTargetPosition(newLBT);
            rightBack.setTargetPosition(newRBT);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftBack.setPower(Math.abs(speed));
            rightBack.setPower(Math.abs(speed));



            while (opModeIsActive() &&
                   (leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy() )) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLFT,  newRFT, newLBT, newRBT);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        leftBack.getCurrentPosition(),
                        rightBack.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            //  sleep(250);   // optional pause after each move
        }
    }
}
