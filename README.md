# StopwatchTestTask
This application has similar functions to a stopwatch. 
 STEP 1: In the app's settings, the user will define the "click button", 
 which is the button used to take timings. 
The "click button" may be different for different Android phones. 
When the software is active, the selected "click button" will perform only 
functions related to the app. For example, if a volume button is selected, 
then it  will not perform the normal volume function when the app is active. 
If this is not possible, a similar solution must be used.
 STEP 2:  The application enters "timing collection mode", beginning with a 
 phone vibration (500ms duration). Now the app waits for clicks of the "click button”.
 STEP 3: Once the click button is pressed, the app starts logging the time 
 interval between each click. This will be measured in milliseconds (accurate to 1ms). 
 The time intervals will be displayed one per line as below: 
 500
 700
 800
 1000 etc.
 This will continue until there are no further clicks for 10 seconds (10,000ms). 
 Then the timing values will be saved to a database (as one set of data), and the app
 returns to STEP 2. The user will be able to review each set  of timings, and delete them as required.
 
 P.S. Вo not pay strong attention to the clock because of small inconsistencies
