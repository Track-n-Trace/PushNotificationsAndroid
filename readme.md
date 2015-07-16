PUSH ANDROID
=============

Purpose
========
Use this android app to get notifications based on your checklist created.


Requirements
=============
- For Android phones, the minimum version of the operating system should be 4.3 (JellyBean).
- An IBM Bluemix account with Track-N-Trace app running (With PUSH service added)
- You should be registered as a user on the cloud app to use this android app.


Outcome
========
At the end of this tutorial you'll be able to receive notification for your assets. The notifications are based on the checklist created by the user who logs in into the android app.


Target Audience
================
Anyone who is using the Track-N-Trace cloud app with mobile notifications. 


Steps to be followed
=====================
- Clone the "https://github.com/Track-n-Trace/quick-start-push.git" repository. To do this, from a command window, navigate to the directory on your computer in which you want to clone the repository, and then enter:
	git clone https://github.com/Track-n-Trace/quick-start-push.git

- Install Android Studio (if you have already not done that)

- Open Android Studio.
Click on File > Open Project

- Browse to the location where you have cloned the repository and select the project.

- Open the project.

- Browse to the assets folder in the project and open bluelist.properties file. Now, enter your APPLICATION ID, APPLICATION SECRET and APPLICATION ROUTE in the variables provided there.

- Open the Login.java file and replace <YOUR-APP-NAME> by te name of your application name on bluemix.
(String url = "http://<YOUR-APP-NAME>.mybluemix.net")

- Deploy the app on your android phone.

- Login using your username and password with which you were registered on the Track-N-Trace app as a user.

- Upon login, you will get a screen saying "Welcome YOUR_USERNAME"

- Wait for sometime and you will see "Subscribed to notifications" on successful registration to the push service. In case some error occurs in registration, you will get an error message. In this scenario, logout and login again.



PUSH CLOUD
============

To use the push service on the cloud app the following steps are to be followed.

- Open the app.js file in Node.Code folder

- In the json variable named appConfig, enter your application id, application secret and application route

	var appConfig = {   	
    	applicationId: <APPLICATION-ID>,
   	 	applicationRoute: <ROUTE FOR APPLICATION>,
    	applicationSecret: <APPLICATION SECRET>
	};