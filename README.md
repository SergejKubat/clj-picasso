# clj-picasso

## Overview

This is a simple image processing library for Clojure, developed as a university project. 
The library provides basic functionalities for loading, saving, and manipulating images. 
It is designed to be easy to use and extendable.

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


## Getting Started

To use **clj-picasso** library in your Clojure project, follow the steps below:

### Prerequisites

- [Clojure](https://clojure.org/releases/downloads) installed (version 1.11.1)
- [Leiningen](https://leiningen.org/) build tool for Clojure projects

## Installation

Add the following dependency to your project.clj file:

```clojure
;; [clj-picasso "1.0.0"]
```

## Example usage

```
(require '[clj-picasso.core :as picasso])

;; Load an image
(def image (picasso/load-from-path "path/to/image.jpg"))

;; Apply grayscale filter
(def resized-image (picasso/apply-grayscale image))

;; Save the grayscaled image
(picasso/save-image resized-image "path/to/grayscale-image.jpg")
```

Before:

![Before](resources/images/input.png)

After:

![Before](resources/images/grayscale.png)

# More examples

Explore the usage of **clj-picasso** with the following examples:

[//]: # (- [Basic Image Operations]&#40;examples/basic-operations.clj&#41;)

[//]: # (- [Color Manipulation]&#40;examples/color-manipulation.clj&#41;)

[//]: # (- [Image Overlay]&#40;examples/image-overlay.clj&#41;)

[//]: # (- [Drawing on Images]&#40;examples/drawing-on-images.clj&#41;)

[//]: # (- [Batch Processing]&#40;examples/batch-processing.clj&#41;)

Feel free to examine these examples to understand how to use different features of the library in your own projects.

## Testing

[Midje](https://github.com/marick/Midje) was utilized for testing this library.
Midje is a testing library for Clojure that provides a readable and flexible syntax for writing tests.

### Running Tests

To run the tests, ensure that Midje is installed, and then execute the following command:

```bash
lein midje
```

## Contributing

If you would like to contribute to the project, feel free to fork the repository and submit pull requests. 
Contributions, bug reports, and feature requests are welcome!

## Acknowledgments

[1] Clojure - Cheatsheet (https://clojure.org/api/cheatsheet)

[2] ClojureDocs (https://clojuredocs.org/)

[3] Daniel Higginbotham, Clojure for the Brave and True (https://www.braveclojure.com/clojure-for-the-brave-and-true/)

[4] A Clojure Introduction to Binary and Bitwise Operators (https://cek.io/blog/2017/08/17/clojure-introduction-binary-bitwise/)

[5] Image Processing: Techniques, Types, & Applications (https://www.v7labs.com/blog/image-processing-guide)

[6] Java image processing (https://www.geeksforgeeks.org/image-processing/)

[7] Stroking and Filling Graphics Primitives (https://www.iitk.ac.in/esc101/05Aug/tutorial/2d/display/strokeandfill.html)

[8] https://github.com/mikera/imagez

[9] https://github.com/marick/Midje

## License

Copyright © 2023 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.