;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.utils
  (:require [clojure.string :as string])
  (:import (java.awt.image BufferedImage)))

(defn clamp [value min-value max-value]
  "Clamp the value to the specified range."
  (max (int min-value) (min (int value) (int max-value))))

(defn hex-color-to-decimal [hex-color]
  "Converts a hexadecimal color string (with or without a '#' prefix)
  to its decimal representation."
  (let [hex-without-hash (if (string/starts-with? hex-color "#")
                           (subs hex-color 1)               ;; remove the '#' prefix if present
                           hex-color)]
    ;; parse the hexadecimal string to its decimal representation using base 16
    (Integer/parseInt hex-without-hash 16)))

(defn get-pixel-channels [^long pixel]
  "Extracts the red, green, and blue color channels from a pixel
  represented as a number."
  {:red   (bit-and (bit-shift-right pixel 16) 0xFF)         ;; extract red channel
   :green (bit-and (bit-shift-right pixel 8) 0xFF)          ;; extract green channel
   :blue  (bit-and pixel 0xFF)})                            ;; extract blue channel

(defn get-color-channels [^String color]
  "Extracts the red, green, and blue color channels from a hexadecimal
   color string"
  (let [pixel (hex-color-to-decimal color)]
    (get-pixel-channels pixel)))

(defn create-pixel
  "Create pixel value in RGB or ARGB format based on the provided channels."
  ([red green blue]
   (bit-or (bit-shift-left (int red) 16)                    ;; combine red, green, and blue channels
           (bit-shift-left (int green) 8)
           (int blue)))
  ([alpha red green blue]
   (bit-or (bit-shift-left (int alpha) 24)                  ;; combine alpha, red, green, and blue components
           (bit-shift-left (int red) 16)
           (bit-shift-left (int green) 8)
           (int blue))))

(defn calculate-mean-intensity [^BufferedImage image ^long width ^long height]
  "Calculate the mean intensity of the given image."
  (let [total-pixels (* width height)
        ;; calculate the total intensity by summing the intensities of all pixels
        total-intensity (reduce +
                                (for [x (range width)
                                      y (range height)]
                                  (let [pixel (.getRGB image x y)
                                        channels (get-pixel-channels pixel)]
                                    (+ (int (:red channels)) (int (:green channels)) (int (:blue channels))))))
        ;; calculate the mean intensity by dividing the total intensity by the total number of pixels
        mean-intensity (double (/ (int total-intensity) (int total-pixels)))]
    mean-intensity))

(defn get-neighborhoods [^BufferedImage image width height x y size]
  "Get the pixel values in the neighborhood of the specified coordinates."
  (let [half-size (int (/ (int size) 2))                    ;; calculate half of the specified neighborhood size
        x (int x)
        y (int y)
        neighborhoods (for [dx (range (- x half-size) (+ x half-size 1))]
                        (for [dy (range (- y half-size) (+ y half-size 1))]
                          (let [clamped-x (clamp dx 0 (dec (int width))) ;; clamp x-coordinate to image boundaries
                                clamped-y (clamp dy 0 (dec (int height))) ;; clamp y-coordinate to image boundaries
                                pixel (.getRGB image clamped-x clamped-y)]
                            (bit-and (bit-shift-right pixel 16) 0xFF))))]
    neighborhoods))

(defn calculate-average-color [neighborhoods]
  "Calculate the average color of a neighborhoods of pixels."
  (let [total-pixels (int (count neighborhoods))
        ;; sum the red channel values of all pixels in the neighborhood
        total-red (int (apply + (map #(nth % 0) neighborhoods)))
        ;; sum the green channel values of all pixels in the neighborhood
        total-green (int (apply + (map #(nth % 1) neighborhoods)))
        ;; sum the blue channel values of all pixels in the neighborhood
        total-blue (int (apply + (map #(nth % 2) neighborhoods)))]
    (create-pixel (int (/ total-red total-pixels))          ;; calculate the average red channel value
                  (int (/ total-green total-pixels))        ;; calculate the average green channel value
                  (int (/ total-blue total-pixels)))))      ;; calculate the average blue channel value

(defn generate-random-pixel []
  "Generate pixel with random channel values."
  (bit-or (bit-shift-left (int (rand-int 256)) 24)          ;; generate a random value for the alpha channel
          (bit-shift-left (int (rand-int 256)) 16)          ;; generate a random value for the red channel
          (bit-shift-left (int (rand-int 256)) 8)           ;; generate a random value for the green channel
          (int (rand-int 256))))                            ;; generate a random value for the blue channel