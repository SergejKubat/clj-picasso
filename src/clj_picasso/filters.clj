;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.filters
  (:require [clj-picasso.utils :as utils])
  (:import (java.awt.image BufferedImage)))

(defn ^BufferedImage apply-grayscale [^BufferedImage image]
  "Apply a grayscale filter to the given image."
  (let [width (.getWidth image)
        height (.getHeight image)
        gray-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              channels (utils/get-pixel-channels pixel)
              average (int (/ (+ (int (:red channels)) (int (:green channels)) (int (:blue channels))) 3))
              gray-pixel (utils/create-pixel average average average)] ;; set pixel channels to average
          (.setRGB gray-image x y gray-pixel))))
    gray-image))

(defn ^BufferedImage apply-sepia [^BufferedImage image]
  "Apply a sepia filter to the given image."
  (let [width (.getWidth image)
        height (.getHeight image)
        sepia-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              channels (utils/get-pixel-channels pixel)
              ;; newRed = 0.393*R + 0.769*G + 0.189*B
              ;; newGreen = 0.349*R + 0.686*G + 0.168*B
              ;; newBlue = 0.272*R + 0.534*G + 0.131*B
              new-red (min (int (+ (* 0.393 (int (:red channels))) (* 0.769 (int (:green channels))) (* 0.189 (int (:blue channels)))))
                           255)
              new-green (min (int (+ (* 0.349 (int (:red channels))) (* 0.686 (int (:green channels))) (* 0.168 (int (:blue channels)))))
                             255)
              new-blue (min (int (+ (* 0.272 (int (:red channels))) (* 0.534 (int (:green channels))) (* 0.131 (int (:blue channels)))))
                            255)
              new-pixel (utils/create-pixel new-red new-green new-blue)]
          (.setRGB sepia-image x y new-pixel))))
    sepia-image))

(defn ^BufferedImage apply-negative [^BufferedImage image]
  "Apply a negative filter to the given image."
  (let [width (.getWidth image)
        height (.getHeight image)
        negative-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              channels (utils/get-pixel-channels pixel)
              new-red (- 255 (int (:red channels)))
              new-green (- 255 (int (:green channels)))
              new-blue (- 255 (int (:blue channels)))
              new-pixel (utils/create-pixel new-red new-green new-blue)]
          (.setRGB negative-image x y new-pixel))))
    negative-image))

(defn ^BufferedImage apply-one-channel [^BufferedImage image ^String color-type]
  "Convert given image to an image with either red effect, green effect, or blue effect."
  (let [width (.getWidth image)
        height (.getHeight image)
        one-channel-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)]
          (case color-type
            "red" (let [red (bit-and (bit-shift-right pixel 16) 0xFF) ;; extract red channel value
                        new-pixel (utils/create-pixel red 0 0)] ;; create a new pixel with the red channel effect
                    (.setRGB one-channel-image x y new-pixel))
            "green" (let [green (bit-and (bit-shift-right pixel 8) 0xFF) ;; extract green channel value
                          new-pixel (utils/create-pixel 0 green 0)] ;; create a new pixel with the green channel effect
                      (.setRGB one-channel-image x y new-pixel))
            "blue" (let [blue (bit-and pixel 0xFF)
                         new-pixel (utils/create-pixel 0 0 blue)] ;; extract blue channel value
                     (.setRGB one-channel-image x y new-pixel)) ;; create a new pixel with the blue channel effect
            ))))
    one-channel-image))

(defn ^BufferedImage apply-median [^BufferedImage image ^long size]
  "Apply a median filter to the given image with the specified neighborhood size."
  (let [width (.getWidth image)
        height (.getHeight image)
        filtered-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        ;; extract the neighborhoods around the current pixel
        (let [neighborhoods (flatten (utils/get-neighborhoods image width height x y size))
              ;; sort the pixel values within the neighborhood
              sorted-neighborhood (sort neighborhoods)
              ;; calculate the median value by selecting the middle element in the sorted neighborhood
              median-value (nth sorted-neighborhood (/ (count sorted-neighborhood) 2))
              ;; create a new pixel with the median value for each color channel
              new-pixel (utils/create-pixel median-value median-value median-value)]
          (.setRGB filtered-image x y new-pixel))))
    filtered-image))

(defn ^BufferedImage apply-blur [^BufferedImage image ^long size]
  "Apply a blur filter to the given image with the specified neighborhood size."
  (let [width (.getWidth image)
        height (.getHeight image)
        blurred-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        ;; extract the neighborhoods around the current pixel
        (let [neighborhoods (utils/get-neighborhoods image width height x y size)
              ;; calculate the average color of the neighborhoods
              average-color (utils/calculate-average-color neighborhoods)]
          (.setRGB blurred-image x y average-color))))
    blurred-image))

(defn ^BufferedImage adjust-brightness [^BufferedImage image ^double factor]
  "Adjust the brightness of the given image by the specified factor."
  (let [width (.getWidth image)
        height (.getHeight image)
        adjusted-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              channels (utils/get-pixel-channels pixel)
              ;; calculate the new color values based on the brightness factor
              new-red (int (utils/clamp (* (int (:red channels)) factor) 0 255))
              new-green (int (utils/clamp (* (int (:green channels)) factor) 0 255))
              new-blue (int (utils/clamp (* (int (:blue channels)) factor) 0 255))
              ;; create a new pixel with the adjusted color values
              new-pixel (utils/create-pixel new-red new-green new-blue)]
          (.setRGB adjusted-image x y new-pixel))))
    adjusted-image))

(defn ^BufferedImage adjust-contrast [^BufferedImage image ^double factor]
  "Adjust the contrast of the given image by modifying intensity values based on the mean intensity."
  (let [width (.getWidth image)
        height (.getHeight image)
        ;; calculate the mean intensity of the original image
        mean-intensity (double (utils/calculate-mean-intensity image width height))
        contrasted-image (BufferedImage. width height (.getType image))]
    (doseq [x (range width)]
      (doseq [y (range height)]
        (let [pixel (.getRGB image x y)
              channels (utils/get-pixel-channels pixel)
              ;; calculate new color values based on the contrast adjustment and mean intensity
              new-red (utils/clamp (int (+ mean-intensity (* factor (- (int (:red channels)) mean-intensity))))
                                   0 255)
              new-green (utils/clamp (int (+ mean-intensity (* factor (- (int (:green channels)) mean-intensity))))
                                     0 255)
              new-blue (utils/clamp (int (+ mean-intensity (* factor (- (int (:blue channels)) mean-intensity))))
                                    0 255)
              ;; create a new pixel with the adjusted color values
              new-pixel (utils/create-pixel new-red new-green new-blue)]
          (.setRGB contrasted-image x y new-pixel))))
    contrasted-image))