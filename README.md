<p align="center">
<img src="http://i.imgur.com/BsdBDgI.png" alt="BubbleExpandableMenu"/>
</p>

## What is BubbleExpandableMenu ?

<p align="center">
<img src="http://i.imgur.com/fKGCCET.gif" width="295" height="568" />
</p>

## Demo

Build and run the `BubbleExpandableMenuDemo` project in Android Studio to see `BubbleExpandableMenu` in action.

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

## License

BubbleExpandableMenu is available under the MIT license.

Copyright © 2013 Charles Harroch.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
