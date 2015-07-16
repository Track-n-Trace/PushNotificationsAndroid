Quick Start with Push
===
To get started with Push, you can run sample mobile application. The sample application registers a device with Push, subscribes to a tag and receives notifications.

##Android: Getting Started

Creating the Mobile Cloud boilerplate application
---

1. Login to IBM Bluemix
2. Click 'Catalog' or 'Create An App'
3. Under Boilerplates, select Mobile Cloud.
4. Enter in App Info & select 'Create'
5. You now have a mobile cloud backend, providing you with some mobile services on the cloud!

For Android Studio Users
---

First you will need to download the Gradle .zip file from [here](http://www.gradle.org). To install Gradle, simply extract the downloaded zip into the directory of your choice.

Android Studio will ask for a GRADLE HOME when importing the sample. Set that path to the directory extracted from the Gradle .zip file where the 'bin' directory lives. The 'build.gradle' file will automatically build your project, pulling in the required dependencies. You may then and skip down to step #4 of "Getting Started With Eclipse".


Getting Started With Eclipse
---

1. Set up the Android Development Tools (ADT)
	a. Download the ADT and install it
	b. Launch Eclipse IDE from the installed ADT Bundle. Then go to Window > SDK Manager.
    c. Enable the Updates/New and Obsolete checkbox for the Show property in Android SDK Manager.
	d. Install the latest SDK Platform-tools, the Android platform API (at least 4.3 / API18), and under extras, the package Google Play Services.

2. Download the Android sample application
	a. Unzip the sample application to a directory of your choice.
	b. From the ADT, import the sample application into your workspace
		- Click File > Import.
		- Click General > Existing Projects into Workspace
		- Click the QuickStartPushDemo_Android directory that you unzipped.
		- Click the Copy projects into workspace checkbox.
		- Ensure that the Android platform is included on the Java build path: Project > Properties > Java Build Path > Order & Export tab > Select Android x.x
		
3. Download the Mobile Cloud Services SDK for Android. For more information, see Building a mobile application.
	a. If you have not created one, create a Mobile Cloud application.
	b. From the Overview section of the application, download the Mobile Cloud Services SDK for Android.
	c. Click File > Import > General > File System to import the files `ibmpush.jar` and `ibmbluemix.jar` from the IBM SDK for Android into your libs directory of the sample project.
	d. Add the copied JAR files to the sample application classpath.
	e. Import Google Play Services library into your workspace from <ADT installation dir>\sdk\extras\google\google_play_services. Click File > Import, select Android > Existing Android Code into Workspace and browse to the location of the library project to import it. (Select the option Copy projects into workspace).
	f. Add a reference of Google Play Services library to your application. Right click <project> > properties > Android > Library > Add (Add Google play services reference from your project workspace)
	
4. Configure the sample application
	a. Obtain a GCM API Key for Server application and the Google Project Number under which the API Key was generated. See http://developer.android.com/google/gcm/gs.html#create-proj to obtain a Google API Key for Server applications and Project Number.
	b. In your Mobile Cloud application, click the Push service and the Configuration tab. Under Android, configure the Google API key and project number.
	c. In the 'assets\bluelist.properties' file of the sample application, update the applicationID, applicationSecret and applicationRoute of your Mobile Cloud application. The applicationID, applicationRoute and applicationSecret can be found in the Overview section of your Mobile Cloud application.
			
    ```
    applicationID=<INSERT_APPLICATION_ID_HERE>
    applicationSecret=<INSERT_APPLICATION_SECRET_HERE>
    applicationRoute=MyBluemixApp.mybluemix.net
    ```
	
5. Run the sample application

	a. From ADT, create an emulator under Window>Android Virtual Device Manager. Set the target to Google APIs. In Android Studio, you may create an emulator once you begin to run the app.

	b. Start the emulator, or even better, connect a running device.
	
	c. Right-click on your project and select Run As > Android application. With Android Studio Click the run button at the top of the IDE with your project selected.

	d. Run the QuickStartPushDemo_Android application within the emulator or on your device.

	e. Go back to your Mobile Cloud application dashboard on Bluemix, click the Push service and the Notifications tab. Send a test notification to the mobile device.

	f. Return to your emulator or device and you will see the notification arrive on your emulator.
	
This package contains sample code provided in source code form. The samples are licensed under the under the Apache License, Version 2.0 (the "License").  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 and may also view the license in the license.txt file within this package.  Also see the notices.txt file within this package for additional notices.