# GBRecyclerIndicator

This is a GoodBarber library to handle Android RecyclerViews items removing boilerplate code and also to automatically manage the different types of elements on the List.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Android minSdkVersion: 16

### How to import dependency

On root of the project change the file `build.gradle` and add this line on repositories:

`allprojects {`
    `repositories {`
        `jcenter()`
        `maven { url "https://jitpack.io" }`
    `}`
`}`

On `<ProjectName>/src/builde.gradle` add this line on _dependencies_:

`compile 'com.github.dtfortunato:GBRecyclerIndicator:1.0'`

Now it's ready to use the GBRecyclerIndicator lib

## Features

The main feature on this library is to you easily manage all items displayed on your RecyclerView. Here you can easily have multiple columns and different number of columns in each line of the list. Manage dynamically the items content and persisting the current status of the item without using any hack on the view or model data, because here we use a middle layer between the Adapter and the View to be displayed on the RecyclerView that we call **GBRecyclerIndicator**.
With this GBRecyclerIndicator you can easily do this:
* Have multiple and differentiated number of columns in each line
* Easily handle different view types to be displayed on the RecyclerView
* Persist current status on the indicator (when the view is reused, then we can replace the current status of the item when it's visible again)
* Pager effect on Horizontal RecyclerView

**Example Vertical List with multiple columns**:
![Vertical List Sample](https://github.com/dtfortunato/GBRecyclerIndicatorSample/blob/master/gifs/sample_vertical_list.gif?raw=true)

**Example Horizontal List inside of a Vertical List**:
![Horizontal List Sample](https://github.com/dtfortunato/GBRecyclerIndicatorSample/blob/master/gifs/sample_horizontal_list.gif?raw=true)

### Usage

We also published a sample project to you to easily try our library. Check it out here: https://github.com/dtfortunato/GBRecyclerIndicatorSample
