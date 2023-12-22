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