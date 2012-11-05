This is the Quadrokinect project, SINLAB, EPFL.
The idea of the project is to make a system, that will controll the group of ARDrones with Kinect.

So the logic is following:
	-We get the coordinates from the kinect by OSCeleton and receive them in our program.
	-Depending on the hands position, we generate the commands to send to the ARDrone.
	-And we send them with ARDroneForP5.

For the group controll, we have to make some kind of supervisor to know the exact position of each ARDrone in space (such that they don't crash). THe idea is to use the ARDRone's accelerometer and gyroscope to calculate the movements of ARDrone + to use some markers of other drones/floor/some where else to correct the data from ARDrone.

The demo video:  http://www.youtube.com/watch?v=HNe1jM8NfLs (only one kopter for the moment)