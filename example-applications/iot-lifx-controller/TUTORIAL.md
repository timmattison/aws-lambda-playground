# Tutorial: Using the IoT LIFX controller

This tutorial will walk you through getting the IoT LIFX controller set up, calling it, and then tearing it down.  The commands in this tutorial assume you are in the `iot-lifx-controller` directory.

## Step 0: Get a re:Invent IoT button

You cannot buy these.  If you do not have one you cannot do this tutorial.  However, you can hook this API up using API gateway and use it manually if you want.  It is just a bit trickier.  In the interest of brevity I will not include those steps in this tutorial

## Step 1: Set up your application.json file

Use the `src/main/resources/application.json.example` file as a guide.

Go to the LIFX API and get the IDs of the [scenes](http://api.developer.lifx.com/docs/list-scenes) or [lights/groups](http://api.developer.lifx.com/docs/list-lights) you want to control.  I've only tested with a group ID but any [selector](http://api.developer.lifx.com/docs/selectors) should work.

Fill in the values in `application.json.example` from the LIFX API pages, and then copy the file to `application.json`.

## Step 2: Build the JAR

```
$ mvn clean compile test package
```

You should get a `BUILD SUCCESS` if everything worked.

If the tests fail your `application.json` file may be invalid.

## Step 3: Create the Lambda function

Navigate to [step 2 of the AWS Lambda creation process](https://console.aws.amazon.com/lambda/home?region=us-east-1#/create?step=2) (step 1 is selecting a blueprint/template which we do not want to do).

- Name your function `IotLifxController`
- Enter a description if you want
- Set the runtime to `Java 8`
- Select `Upload a ZIP file` (it should be selected by default)
- Click "Upload" and select the file `iot-lifx-controller-1.0-SNAPSHOT-jar-with-dependencies.jar` from the `iot-lifx-controller/target` directory
- Set the handler to `com.timmattison.IotLifxController::handleRequest`
- Set the role to `Basic execution role` or `lambda_basic_execution`, whichever exists.  NOTE: If you have popups disabled this may continually tell you that you didn't fill the field in.  You MUST enable popups to get this to work.
- Set the memory to `1024 MB`.  Lower than 1024 MB does not work for me.
- Set the timeout to `5 seconds`
- Click `Next`
- Click `Create function`

## Step 3: Connect the Lambda function to your IoT button

NOTE: This assumes you have not customized your IoT button settings too much beyond the defaults.

- Click on the `AWS IoT` icon in the Amazon console
- Click `aws_iot_button_rule`, the fourth box in the middle of the screen
- Click `Edit Actions` on the right panel
- Click `Add New Action` on the right panel
- Select `Insert this message into a code function and execute it (Lambda)` in the `Choose an action` dropdown list
- Select `IotLifxController` from the `Function Name` dropdown list
- Click `Add Action`

## Step 4: Press your button!

Try the single, double, and long press options.  The first time you run use it it may be a little slow.  Subsequent times it will be faster.  After it has been unused for a while expect it to take a few seconds.

Not working?  [CloudWatch](https://console.aws.amazon.com/cloudwatch/home?region=us-east-1#) is your friend here.  If it isn't working do this:

- Click `Logs` on the left panel
- Click `aws/lambda/IotLifxController` in the `Log Groups` list
- Click the log entry that corresponds to the time that you had problems.  You should see some kind of error there.  Note that if you press the button more than once in a single minute those logs will be grouped together in a single entry.
