;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.filters
  (:import (java.awt.image BufferedImage)))

(defn clamp [value min-value max-value]
  "Clamp the value to the specified range."
  (max min-value (min value max-value)))

(defn create-pixel
  ([red green blue]
   "Create new RGB pixel value."
   (bit-or (bit-shift-left red 16)
           (bit-shift-left green 8)
           blue))
  ([alpha red green blue]
   "Create new ARGB pixel value"
   (bit-or (bit-shift-left alpha 24)
           (bit-shift-left red 16)
           (bit-shift-left green 8)
           blue)))

(defn ^BufferedImage convert-to-grayscale [^BufferedImage image]
  "Convert the given image to grayscale."
  (let [width (.getWidth image)
        height (.getHeight image)
        gray-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              red (bit-and (bit-shift-right pixel 16) 0xFF)
              green (bit-and (bit-shift-right pixel 8) 0xFF)
              blue (bit-and pixel 0xFF)
              average (int (/ (+ red green blue) 3))
              gray-pixel (create-pixel average average average)]
          (.setRGB gray-image x y gray-pixel))))
    gray-image))

(defn ^BufferedImage convert-to-negative [^BufferedImage image]
  "Convert the given image to negative."
  (let [width (.getWidth image)
        height (.getHeight image)
        negative-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              red (bit-and (bit-shift-right pixel 16) 0xFF)
              green (bit-and (bit-shift-right pixel 8) 0xFF)
              blue (bit-and pixel 0xFF)
              new-red (- 255 red)
              new-green (- 255 green)
              new-blue (- 255 blue)
              new-pixel (create-pixel new-red new-green new-blue)]
          (.setRGB negative-image x y new-pixel))))
    negative-image))

(defn ^BufferedImage convert-to-sepia [^BufferedImage image]
  "Convert the given image to sepia."
  (let [width (.getWidth image)
        height (.getHeight image)
        sepia-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              red (bit-and (bit-shift-right pixel 16) 0xFF)
              green (bit-and (bit-shift-right pixel 8) 0xFF)
              blue (bit-and pixel 0xFF)
              new-red (min (int (+ (* 0.393 red) (* 0.769 green) (* 0.189 blue))) 255)
              new-green (min (int (+ (* 0.349 red) (* 0.686 green) (* 0.168 blue))) 255)
              new-blue (min (int (+ (* 0.272 red) (* 0.534 green) (* 0.131 blue))) 255)
              new-pixel (create-pixel new-red new-green new-blue)]
          (.setRGB sepia-image x y new-pixel))))
    sepia-image))

(defn ^BufferedImage adjust-brightness [^BufferedImage image ^double factor]
  "Adjust the brightness of the given image by the specified factor."
  (let [width (.getWidth image)
        height (.getHeight image)
        adjusted-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              red (bit-and (bit-shift-right pixel 16) 0xFF)
              green (bit-and (bit-shift-right pixel 8) 0xFF)
              blue (bit-and pixel 0xFF)
              new-red (int (clamp (* red factor) 0 255))
              new-green (int (clamp (* green factor) 0 255))
              new-blue (int (clamp (* blue factor) 0 255))
              new-pixel (create-pixel new-red new-green new-blue)]
          (.setRGB adjusted-image x y new-pixel))))
    adjusted-image))

(defn calculate-mean-intensity [^BufferedImage image ^long width ^long height]
  "Calculate the mean intensity of the given image."
  (let [total-pixels (* width height)
        total-intensity (reduce +
                                (for [x (range width)
                                      y (range height)]
                                  (let [pixel (.getRGB image x y)
                                        red (bit-and (bit-shift-right pixel 16) 0xFF)
                                        green (bit-and (bit-shift-right pixel 8) 0xFF)
                                        blue (bit-and pixel 0xFF)]
                                    (+ red green blue))))
        mean-intensity (double (/ total-intensity total-pixels))]
    mean-intensity))

(defn ^BufferedImage adjust-contrast [^BufferedImage image ^double factor]
  "Adjust the contrast of the given image by modifying intensity values based on the mean intensity."
  (let [width (.getWidth image)
        height (.getHeight image)
        mean-intensity (double (calculate-mean-intensity image width height))
        contrasted-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              red (bit-and (bit-shift-right pixel 16) 0xFF)
              green (bit-and (bit-shift-right pixel 8) 0xFF)
              blue (bit-and pixel 0xFF)
              new-red (clamp (int (+ mean-intensity (* factor (- red mean-intensity)))) 0 255)
              new-green (clamp (int (+ mean-intensity (* factor (- green mean-intensity)))) 0 255)
              new-blue (clamp (int (+ mean-intensity (* factor (- blue mean-intensity)))) 0 255)
              new-pixel (create-pixel new-red new-green new-blue)]
          (.setRGB contrasted-image x y new-pixel))))
    contrasted-image))

(defn ^BufferedImage convert-to-one-channel [^BufferedImage image ^String color-type]
  "Convert colored image to an image with either red effect, green effect, or blue effect."
  (let [width (.getWidth image)
        height (.getHeight image)
        one-channel-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)]
          (case color-type
            "red" (let [red (bit-and (bit-shift-right pixel 16) 0xFF)
                        new-pixel (create-pixel red 0 0)]
                    (.setRGB one-channel-image x y new-pixel))
            "green" (let [green (bit-and (bit-shift-right pixel 8) 0xFF)
                          new-pixel (create-pixel 0 green 0)]
                      (.setRGB one-channel-image x y new-pixel))
            "blue" (let [blue (bit-and pixel 0xFF)
                         new-pixel (create-pixel 0 0 blue)]
                     (.setRGB one-channel-image x y new-pixel))
            ))))
    one-channel-image))

(defn get-neighborhoods [^BufferedImage image width height x y size]
  "Get the pixel values in the neighborhood of the specified coordinates."
  (let [half-size (/ size 2)
        neighborhoods (for [dx (range (- x half-size) (+ x half-size 1))]
                        (for [dy (range (- y half-size) (+ y half-size 1))]
                          (let [clamped-x (clamp dx 0 (dec width))
                                clamped-y (clamp dy 0 (dec height))
                                pixel (.getRGB image clamped-x clamped-y)]
                            (bit-and (bit-shift-right pixel 16) 0xFF))))]
    neighborhoods))

(defn ^BufferedImage apply-median-filter [^BufferedImage image ^long size]
  "Apply a median filter to the given image with the specified neighborhood size."
  (let [width (.getWidth image)
        height (.getHeight image)
        filtered-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [neighborhoods (flatten (get-neighborhoods image width height x y size))
              sorted-neighborhood (sort neighborhoods)
              median-value (nth sorted-neighborhood (/ (count sorted-neighborhood) 2))
              new-pixel (create-pixel median-value median-value median-value)]
          (.setRGB filtered-image x y new-pixel))))
    filtered-image))

(defn calculate-average-color [neighborhoods]
  "Calculate the average color of a neighborhoods of pixels."
  (let [total-pixels (count neighborhoods)
        total-red (apply + (map #(nth % 0) neighborhoods))
        total-green (apply + (map #(nth % 1) neighborhoods))
        total-blue (apply + (map #(nth % 2) neighborhoods))]
    (create-pixel (int (/ total-red total-pixels))
                  (int (/ total-green total-pixels))
                  (int (/ total-blue total-pixels)))))

(defn ^BufferedImage apply-blur-filter [^BufferedImage image ^long size]
  "Apply blur filter to the given image with the specified neighborhood size."
  (let [width (.getWidth image)
        height (.getHeight image)
        blurred-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [neighborhoods (get-neighborhoods image width height x y size)
              average-color (calculate-average-color neighborhoods)]
          (.setRGB blurred-image x y average-color))))
    blurred-image))

(defn ^BufferedImage overlay-images [^BufferedImage image1 ^BufferedImage image2 transparency]
  "Overlay two images with adjustable transparency."
  (let [width (.getWidth image1)
        height (.getHeight image1)
        overlayed-image (BufferedImage. width height (.getType image1))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel1 (.getRGB image1 x y)
              red1 (bit-and (bit-shift-right pixel1 16) 0xFF)
              green1 (bit-and (bit-shift-right pixel1 8) 0xFF)
              blue1 (bit-and pixel1 0xFF)
              pixel2 (.getRGB image2 x y)
              red2 (bit-and (bit-shift-right pixel2 16) 0xFF)
              green2 (bit-and (bit-shift-right pixel2 8) 0xFF)
              blue2 (bit-and pixel2 0xFF)
              alpha (int (* transparency 255))
              new-pixel (create-pixel (int (+ (* (- 1.0 transparency) red1) (* transparency red2)))
                                      (int (+ (* (- 1.0 transparency) green1) (* transparency green2)))
                                      (int (+ (* (- 1.0 transparency) blue1) (* transparency blue2)))
                                      alpha)]
          (.setRGB overlayed-image x y new-pixel))))
    overlayed-image))