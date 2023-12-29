;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.basic-operations
  (:require [clj-picasso.utils :as utils])
  (:import (java.awt.geom AffineTransform)
           (java.awt.image BufferedImage)))

(defn ^BufferedImage resize-image [^BufferedImage image ^long width ^long height]
  "Resize the given image to the specified width and height."
  (let [resized-image (BufferedImage. width height (.getType image))]
    ;; chain method calls on the Graphics2D object
    (doto (.createGraphics resized-image)
      (.drawImage image 0 0 width height nil)
      (.dispose))
    resized-image))

(defn ^BufferedImage scale-image [^BufferedImage image ^double factor]
  "Scale the given image based on specified factor."
  (let [width (.getWidth image)
        height (.getHeight image)
        new-width (int (* width factor))                    ;; calculate scaled width
        new-height (int (* height factor))                  ;; calculate scaled height
        scaled-image (BufferedImage. new-width new-height (.getType image))]
    ;; chain method calls on the Graphics2D object
    (doto (.createGraphics scaled-image)
      (.drawImage image 0 0 new-width new-height nil)
      (.dispose))
    scaled-image))

(defn ^BufferedImage crop-image
  "Crop the given image to the specified region."
  ([^BufferedImage image x y width height]
   (let [original-width (.getWidth image)
         original-height (.getHeight image)]
     ;; check if the crop region is within bounds
     (if (and (<= (+ (int x) (int width)) original-width) (<= (+ (int y) (int height)) original-height))
       (let [cropped-image (BufferedImage. width height (.getType image))]
         ;; chain method calls on the Graphics2D object
         (doto (.createGraphics cropped-image)
           ;; crop the image
           (.drawImage image 0 0 width height x y (+ (int x) (int width)) (+ (int y) (int height)) nil)
           (.dispose))
         cropped-image)
       (throw (IllegalArgumentException. "Image dimensions are too small.")))))
  ([^BufferedImage image width height]
   (crop-image image 0 0 width height)))

(defn ^BufferedImage rotate-image [^BufferedImage image ^double angle]
  "Rotate the given image by the specified angle."
  (let [width (.getWidth image)
        height (.getHeight image)
        rotated-image (BufferedImage. width height (.getType image))]
    (let [graphics (.createGraphics rotated-image)
          transform (AffineTransform.)]
      (.rotate transform (Math/toRadians angle) (/ width 2) (/ height 2)) ;; rotate around the center
      (.drawImage graphics image transform nil)
      (.dispose graphics))
    rotated-image))

(defn ^BufferedImage mirror-image [^BufferedImage image]
  "Create a mirror image from specified image."
  (let [width (.getWidth image)
        height (.getHeight image)
        mirrored-image (BufferedImage. width height (.getType image))]
    ;; iterate through pixels
    (doseq [y (range height)]
      (loop [left-x 0
             right-x (dec width)]
        (when (< left-x width)
          (let [pixel (.getRGB image left-x y)]             ;; get pixel from original image
            (.setRGB mirrored-image right-x y pixel)        ;; set opposite pixel in mirrored image
            (recur (inc left-x) (dec right-x))))))
    mirrored-image))

(defn ^BufferedImage generate-random-image [width height]
  "Generate a random image with the specified width and height."
  (let [image (BufferedImage. width height BufferedImage/TYPE_INT_ARGB)]
    ;; iterate through pixels
    (doseq [y (range height)]
      (doseq [x (range width)]
        (let [pixel (utils/generate-random-pixel)]
          (.setRGB image x y pixel))))                      ;; set random pixel
    image))