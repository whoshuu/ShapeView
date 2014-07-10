# ShapeView

A lightweight library for creating basic shapes as views.

## Features

- Define shapes with a simple and concise XML specification.
- Access properties programmatically with Java.
- Supports the following shapes:
  - Equilateral Triangle
  - Square
  - Rectangle
  - Circle
  - Oval
  - and more to come!

## Installing

Currently, ShapeView is only available as a library project. Download the source code and import it as a library project in Eclipse. For more information on how to do this, read [here](http://developer.android.com/tools/projects/index.html#LibraryProjects).

## Usage

In the XML you plan on using ShapeView in, place the namespace attribute in one of the top level elements:

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_width="match_parent"
    xmlns:shapeview="http://schemas.android.com/apk/res-auto" > <!-- You need this>
    ...
```

Then, put a ShapeView in the XML:

```xml
    <com.whoshuu.shapeview.ShapeView
        android:id="@+id/svRectangle"
        android:layout_width="20dp"
        android:layout_height="40dp"
        shapeview:shape="rectangle"
        shapeview:color="#0F0"
        shapeview:style="stroke"
        shapeview:stroke_width="5dp" />
```

To access it in Java, first grab the `View` object:

```java
ShapeView svRectangle = (ShapeView) findViewById(R.id.svRectangle);
```

and change a property:

```java
svRectangle.setStyle(Style.FILL);
```

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/whoshuu/ShapeView/pulls). Features can be requested using [issues](https://github.com/whoshuu/ShapeView/issues). All code, comments, and critiques are greatly appreciated.
