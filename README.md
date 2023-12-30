# clj-picasso

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.stavrogin/clj-picasso.svg)](https://clojars.org/org.clojars.stavrogin/clj-picasso)

## Overview

This is a simple image processing library for Clojure, developed as a university project. 
The library provides basic functionalities for loading, saving, and manipulating images.
The primary goal of this library is to provide a versatile and easy-to-use set of tools 
for handling various image processing tasks in Clojure.
It is designed to be easy to use and extendable.

## Getting Started

To use **clj-picasso** library in your Clojure project, follow the steps below:

## Installation

### Leiningen/Boot

```clojure
[org.clojars.stavrogin/clj-picasso "1.0.0"]
```

### Clojure CLI/deps.edn

```clojure
[org.clojars.stavrogin/clj-picasso "1.0.0"]
```

### Gradle

```java
implementation("org.clojars.stavrogin:clj-picasso:1.0.0")
```

### Maven

```xml
<dependency>
  <groupId>org.clojars.stavrogin</groupId>
  <artifactId>clj-picasso</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Example usage

```clojure
(ns user-ns.core
  (:require [clj-picasso.loading :refer :all]
            [clj-picasso.filters :refer :all]))

;; Load an image
(def image (load-from-path "path/image.png"))

;; Apply grayscale filter
(def grayscaled-image (apply-grayscale image))

;; Save grayscaled image
(save-image grayscaled-image "path/grayscale-image.png")
```

Before:

![Before](resources/images/input.png)

After:

![Before](resources/images/grayscale.png)

# More examples

Explore the usage of **clj-picasso** with the following examples:

- [Basic Operations](examples/basic_operations.md)
- [Filters](examples/filters.md)
- [Image Overlay](examples/overlay.md)
- [Drawing on Images](examples/drawing.md)
- [Watermark](examples/watermark.md)
- [Comparison](examples/comparison.md)
- [Batch processing](examples/batch_processing.md)

Feel free to examine these examples to understand how to use different features of the library in your own projects.

## Features

### Image Loading and Saving

- **load-from-path:** Load an image from a file path.
- **load-from-file:** Load an image from a file.
- **save-image:** Save an image to a file.

### Basic Operations

- **resize:** Change the size of an image.
- **scale:** Scale the size of an image.
- **crop:** Crop a region from an image.
- **rotate:** Rotate an image by a specified angle.
- **mirror:** Create an mirror image.

### Filters

- **apply-grayscale:** Apply a grayscale filter to the image.
- **apply-sepia:** Apply a sepia filter to the image.
- **apply-negative:** Apply a negative filter to the image.
- **apply-one-channel:** Convert an image to one channel.
- **apply-median:** Apply a median filter to the image.
- **apply-blur:** Apply a (box) blur filter to the image.
- **adjust-brightness:** Adjust the brightness of the image.
- **adjust-contrast:** Adjust the contrast of the image.

### Image Overlay

- **overlay-images:** Overlay two images with adjustable transparency.

### Drawing on Images

- **draw-line:** Draw line on an image.
- **draw-rectangle:** Draw rectangle on an image.
- **draw-ellipse:** Draw ellipse on an image.

### Watermark

- **set-watermark-text** Add a text watermark to the image.
- **set-watermark-image** Add an image watermark to the image.

### Comparison

- **equal-images?:** Compare two images pixel by pixel.
- **mean-squared-error:** Calculate the mean squared error between two images.

### Batch Processing

- **process-images-in-directory:** Apply a set of operations to all images in a directory.

## Supported Image Formats

**clj-picasso** is built on top of Java's BufferedImage class and supports a variety of image formats.
The supported image formats include, but may not be limited to:

- JPEG
- PNG
- GIF
- TIFF
- BMP
- WBMP

Please note that the exact set of supported formats can depend on the underlying Java implementation and
the platform your code is running on. For detailed information about supported image formats in Java,
refer to the [Java Image I/O API documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/imageio/package-summary.html).

## Testing

[Midje](https://github.com/marick/Midje) was utilized for testing this library.
Midje is a testing library for Clojure that provides a readable and flexible syntax for writing tests.

### Running Tests

To run the tests, ensure that Midje is installed, and then execute the following command:

```bash
lein midje
```

## Contributing

Contributions to this image processing library are highly encouraged! If you have ideas for improvements, 
encounter issues, or want to add new features, follow the steps below to contribute:

### Prerequisites

Before contributing, make sure you have the following installed on your machine:

- [Clojure](https://clojure.org/releases/downloads) (version 1.11.1 or newer)
- [Leiningen](https://leiningen.org/) build tool for Clojure projects
- [Git](https://git-scm.com/) version control tool

### Clone the Repository

Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/SergejKubat/clj_picasso.git
```

Change into the project directory:

```bash
cd clj-picasso
```

### Install Dependencies

Make sure to install the project dependencies using Leiningen:

```bash
lein deps
```

## Acknowledgments

[1] Clojure - Cheatsheet (https://clojure.org/api/cheatsheet)

[2] ClojureDocs (https://clojuredocs.org/)

[3] Daniel Higginbotham, Clojure for the Brave and True (https://www.braveclojure.com/clojure-for-the-brave-and-true/)

[4] A Clojure Introduction to Binary and Bitwise Operators (https://cek.io/blog/2017/08/17/clojure-introduction-binary-bitwise/)

[5] Image Processing: Techniques, Types, & Applications (https://www.v7labs.com/blog/image-processing-guide)

[6] Java image processing (https://www.geeksforgeeks.org/image-processing/)

[7] Stroking and Filling Graphics Primitives (https://www.iitk.ac.in/esc101/05Aug/tutorial/2d/display/strokeandfill.html)

[8] (https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html)

[9] https://github.com/mikera/imagez

[10] https://github.com/marick/Midje

## License

Copyright © 2023

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.