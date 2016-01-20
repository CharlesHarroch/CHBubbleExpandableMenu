<p align="center">
<img src="http://i.imgur.com/BsdBDgI.png" alt="BubbleExpandableMenu"/>
</p>

## What is BubbleExpandableMenu ?

<p align="center">
<img src="http://i.imgur.com/fKGCCET.gif" width="320" height="568" />
</p>

## Demo

Build and run the `BubbleExpandableMenuDemo` project in Android Studio to see `BubbleExpandableMenu` in action.

## Features

- Add Items dynamically

## Installation

### JCENTER / MAVEN

Comming soon.

### Manual Install

All you need to do is import `BubbleExpandableMenu` folder as modul into your project.

## Example Usage

In your fragment or Activity layout XML add this declaration :
``` xml
<com.chtechlab.bubbleexpandablemenu.BubbleExpandableMenu
        android:id="@+id/bubble_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
</com.chtechlab.bubbleexpandablemenu.BubbleExpandableMenu>
```

After, you can add items in your controller like this :
``` java
bubbleMenu.addItem(R.mipmap.ic_launcher, 1);
```

Present the menu :
````java
bubbleMenu.show();
````



## Work 

Now, read/learn the code and use it !

# License

This solution is under MIT license.
