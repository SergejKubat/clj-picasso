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
  (max min-value (min value max-value)))

(defn hex-color-to-decimal [hex-color]
  (let [hex-without-hash (if (string/starts-with? hex-color "#")
                           (subs hex-color 1)
                           hex-color)]
    (Integer/parseInt hex-without-hash 16)))

(defn get-pixel-channels [^long pixel]
  {:red   (bit-and (bit-shift-right pixel 16) 0xFF)
   :green (bit-and (bit-shift-right pixel 8) 0xFF)
   :blue  (bit-and pixel 0xFF)})

(defn get-color-channels [^String color]
  (let [pixel (hex-color-to-decimal color)]
    (get-pixel-channels pixel)))

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

(defn calculate-average-color [neighborhoods]
  "Calculate the average color of a neighborhoods of pixels."
  (let [total-pixels (count neighborhoods)
        total-red (apply + (map #(nth % 0) neighborhoods))
        total-green (apply + (map #(nth % 1) neighborhoods))
        total-blue (apply + (map #(nth % 2) neighborhoods))]
    (create-pixel (int (/ total-red total-pixels))
                        (int (/ total-green total-pixels))
                        (int (/ total-blue total-pixels)))))

(defn generate-random-pixel []
  "Generate pixel with random channel values."
  (let [alpha (long (rand-int 256))
        red (long (rand-int 256))
        green (long (rand-int 256))
        blue (long (rand-int 256))]
    (bit-or (bit-shift-left alpha 24)
            (bit-shift-left red 16)
            (bit-shift-left green 8)
            blue)))