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
   (bit-or (bit-shift-left (int red) 16)
           (bit-shift-left (int green) 8)
           (int blue)))
  ([alpha red green blue]
   "Create new ARGB pixel value"
   (bit-or (bit-shift-left (int alpha) 24)
           (bit-shift-left (int red) 16)
           (bit-shift-left (int green) 8)
           (int blue))))

(defn calculate-mean-intensity [^BufferedImage image ^long width ^long height]
  "Calculate the mean intensity of the given image."
  (let [total-pixels (* width height)
        total-intensity (reduce +
                                (for [x (range width)
                                      y (range height)]
                                  (let [pixel (.getRGB image x y)
                                        channels (get-pixel-channels pixel)]
                                    (+ (int (:red channels)) (int (:green channels)) (int (:blue channels))))))
        mean-intensity (double (/ (int total-intensity) (int total-pixels)))]
    mean-intensity))

(defn get-neighborhoods [^BufferedImage image width height x y size]
  "Get the pixel values in the neighborhood of the specified coordinates."
  (let [half-size (int (/ (int size) 2))
        x (int x)
        y (int y)
        neighborhoods (for [dx (range (- x half-size) (+ x half-size 1))]
                        (for [dy (range (- y half-size) (+ y half-size 1))]
                          (let [clamped-x (clamp dx 0 (dec (int width)))
                                clamped-y (clamp dy 0 (dec (int height)))
                                pixel (.getRGB image clamped-x clamped-y)]
                            (bit-and (bit-shift-right pixel 16) 0xFF))))]
    neighborhoods))

(defn calculate-average-color [neighborhoods]
  "Calculate the average color of a neighborhoods of pixels."
  (let [total-pixels (int (count neighborhoods))
        total-red (int (apply + (map #(nth % 0) neighborhoods)))
        total-green (int (apply + (map #(nth % 1) neighborhoods)))
        total-blue (int (apply + (map #(nth % 2) neighborhoods)))]
    (create-pixel (int (/ total-red total-pixels))
                        (int (/ total-green total-pixels))
                        (int (/ total-blue total-pixels)))))

(defn generate-random-pixel []
  "Generate pixel with random channel values."
  (bit-or (bit-shift-left (int (rand-int 256)) 24)
          (bit-shift-left (int (rand-int 256)) 16)
          (bit-shift-left (int (rand-int 256)) 8)
          (int (rand-int 256))))