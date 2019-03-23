TEST SUITE README

JUNIT 4.12 USED FOR THIS TEST SUITE - junit-4.12.jar and hamcrest-core-1.3.jar included in the cardsTest.zip directory.


First extract cardsTest.zip, and locate the folder it has been extracted to.

Next, open a command line and change the current working directory to the folder where you've extracted the contents of cardTest.zip.
On Windows, this can be done using the command cd <path>.


To run the test suite, enter the command:
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;. org.junit.runner.JUnitCore TestSuite

If the running of the test suite is successful, the output on the terminal will read:
OK (47 tests)

Any other outputs such as "player 1 wins" are outputs from the program being tested on and should be ignored during testing.


The .java and .class files for the whole project are included in the cardsTest zip file.
All text files used in the test suite are also included in this directory.


To run each individual test class, use the commands:

java -cp junit-4.12.jar;hamcrest-core-1.3.jar;. org.junit.runner.JUnitCore PlayerTest
This output should read:
OK(15 Tests)

java -cp junit-4.12.jar;hamcrest-core-1.3.jar;. org.junit.runner.JUnitCore CardTest
This output should read:
OK (3 tests)

java -cp junit-4.12.jar;hamcrest-core-1.3.jar;. org.junit.runner.JUnitCore CardDeckTest
This output should read:
OK (8 tests)

java -cp junit-4.12.jar;hamcrest-core-1.3.jar;. org.junit.runner.JUnitCore CardGameTest
This output should read:
OK (21 tests)


NOTE: 
The commands in this README were executed and tested on a Windows computer.
These commands were tested and work using Java version 9.0.4 and Java version 10.0.1