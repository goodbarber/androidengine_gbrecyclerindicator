GoodBarber Internal Librairies - GBRecyclerIndicator
======================

This library is used in the Android native engine of GoodBarber.
It handles Android RecyclerViews items removing boilerplate code and automatically manage the different types of elements on the List.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Android minSdkVersion: 21

### How to import dependency

In your root `build.gradle` (or `settings.gradle` with `dependencyResolutionManagement`), add JitPack to the repositories:

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

In your module's `build.gradle`, add this line to _dependencies_:

```groovy
implementation 'com.github.goodbarber:androidengine_gbrecyclerindicator:1.2.0'
```

Or, if you use a version catalog (`libs.versions.toml`):

```toml
[versions]
gbrecyclerindicator = "1.2.0"

[libraries]
goodbarber-gbrecyclerindicator = { module = "com.github.goodbarber:androidengine_gbrecyclerindicator", version.ref = "gbrecyclerindicator" }
```

Now it's ready to use the GBRecyclerIndicator lib.
You can also check our sample.

## Features

The main feature on this library is to you easily manage all items displayed on your RecyclerView. Here you can easily have multiple columns and different number of columns in each line of the list. Manage dynamically the items content and persisting the current status of the item without using any hack on the view or model data, because here we use a middle layer between the Adapter and the View to be displayed on the RecyclerView that we call **GBRecyclerIndicator**.
With this GBRecyclerIndicator you can easily do this:
* Have multiple and differentiated number of columns in each line
* Easily handle different view types to be displayed on the RecyclerView
* Persist current status on the indicator (when the view is reused, then we can replace the current status of the item when it's visible again)
* Pager effect on Horizontal RecyclerView
* Lifecycle-safe subscriptions for recycled cells: `GBRecyclerViewHolder` is a `LifecycleOwner`, and the `observeRecycled` / `launchRecycled` helpers bind LiveData observers and coroutines to the cell's on-screen lifecycle. Subscriptions pause when the cell scrolls off screen and resume when it comes back (including RecyclerView cache re-attach, with no rebind), and are cleared automatically on rebind/recycle — no manual teardown and no leaks.

**Example Vertical List with multiple columns**:

![Vertical List Sample](https://github.com/dtfortunato/GBRecyclerIndicatorSample/blob/master/gifs/sample_vertical_list.gif?raw=true)

**Example Horizontal List inside of a Vertical List**:

![Horizontal List Sample](https://github.com/dtfortunato/GBRecyclerIndicatorSample/blob/master/gifs/sample_horizontal_list.gif?raw=true)

### Usage

We also published a sample project in this repository for you to easily try our library.
