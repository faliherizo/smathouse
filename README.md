## Introduction

smathouse Android application is a native client for openHAB. It uses REST API of openHAB to render
sitemaps of your openHAB. It also supports my.openhab.org including push notifications.
Release version of the app is always available for installation through

## Setting up development environment

If you want to contribute to Android application we are here to help you to set up
development environment. openHAB Android app is developed using Android studio and also can be
build with maven.

- Download and install [Android Studio](http://developer.android.com/sdk/installing/studio.html)
- After installation launch Android Studio, download and install Android SDK version 4.0.3 (Tools ->
Android -> SDK Manager)
- Check out the latest code from github
- Open the project in Android Studio (File -> Open, select project folder)

# smathouse


API:

fr.mbds.openhab.lifi:
	activity : activity
	fragement: fragement 
	model: model of object
	service: service to use
	adapteur: the adapteur to use in the different fragement 
	SmartLightHandler : class (get the id  for Lampadaire LIFI) 

Configuration : 
	string.xml :  All string and configuration to connect for ws and openhab 

