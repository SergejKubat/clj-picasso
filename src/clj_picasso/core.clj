;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.core
  (:import (java.awt.geom AffineTransform)
           (java.awt.image BufferedImage)
           (java.io File IOException)
           (javax.imageio ImageIO)))

(defn ^BufferedImage load-image [^String file-path]
  "Load an image from specified file path."
  (try
    (let [image (ImageIO/read (File. (str file-path)))]
      image)
    (catch IOException ex
      (println (str "Error loading image: " (.getMessage ex)))
      nil)))

(defn save-image [^BufferedImage image ^String file-path]
  "Save the given image to the specified file path."
  (try
    (ImageIO/write image "png" (File. (str file-path)))
    (catch IOException ex
      (println "Error saving image:" (.getMessage ex)))))

(defn ^BufferedImage resize-image [^BufferedImage image ^long width ^long height]
  "Resize the given image to the specified width and height."
  (let [resized-image (BufferedImage. width height (.getType image))]
    (doto (.createGraphics resized-image)
      (.drawImage image 0 0 width height nil)
      (.dispose))
    resized-image))

(defn ^BufferedImage scale-image [^BufferedImage image ^double factor]
  "Resize the given image to the specified width and height."
  (let [width (.getWidth image)
        height (.getHeight image)
        new-width (int (* width factor))
        new-height (int (* height factor))
        scaled-image (BufferedImage. new-width new-height (.getType image))]
    (doto (.createGraphics scaled-image)
      (.drawImage image 0 0 new-width new-height nil)
      (.dispose))
    scaled-image))

(defn ^BufferedImage crop-image [^BufferedImage image x y width height]
  "Crop the given image to the specified region."
  (let [cropped-image (BufferedImage. width height (.getType image))]
    (doto (.createGraphics cropped-image)
      (.drawImage image 0 0 width height x y (+ (int x) (int width)) (+ (int y) (int height)) nil)
      (.dispose))
    cropped-image))

(defn ^BufferedImage rotate-image [^BufferedImage image angle]
  "Rotate the given image by the specified angle in radians."
  (let [width (.getWidth image)
        height (.getHeight image)
        rotated-image (BufferedImage. width height (.getType image))]
    (let [graphics (.createGraphics rotated-image)
          transform (AffineTransform.)]
      (.rotate transform angle (/ width 2) (/ height 2))
      (.drawImage graphics image transform nil)
      (.dispose graphics))
    rotated-image))

(defn ^BufferedImage mirror-image [^BufferedImage image]
  "Create a mirror image from given image."
  (let [width (.getWidth image)
        height (.getHeight image)
        mirrored-image (BufferedImage. width height (.getType image))]
    (doseq [y (range height)]
      (loop [left-x 0
             right-x (dec width)]
        (when (< left-x width)
          (let [pixel (.getRGB image left-x y)]
            (.setRGB mirrored-image right-x y pixel)
            (recur (inc left-x) (dec right-x))))))
    mirrored-image))

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

(defn ^BufferedImage generate-random-image [width height]
  "Generate a random image with the specified width and height."
  (let [image (BufferedImage. width height BufferedImage/TYPE_INT_ARGB)]
    (doseq [y (range height)]
      (doseq [x (range width)]
        (let [pixel (generate-random-pixel)]
          (.setRGB image x y pixel))))
    image))

(def ^BufferedImage image (load-image "./resources/images/input.png"))
(def ^BufferedImage image2 (load-image "./resources/images/input-2.png"))
(def ^BufferedImage watermark-image (load-image "./resources/images/watermark-example.png"))

;(save-image (resize-image image 400 225) "./resources/images/resized.png")
;(save-image (scale-image image 2.0) "./resources/images/scaled.png")
;(save-image (crop-image image 100 100 200 200) "./resources/images/cropped.png")
;(save-image (rotate-image image (/ Math/PI 2)) "./resources/images/rotated.png")
;(save-image (mirror-image image) "./resources/images/mirrored.png")
;(save-image (generate-random-image 250 250) "./resources/images/random.png")
;(save-image (convert-to-grayscale image) "./resources/images/grayscale.png")
;(save-image (convert-to-negative image) "./resources/images/negative.png")
;(save-image (convert-to-sepia image) "./resources/images/sepia.png")
;(save-image (adjust-brightness image 1.5) "./resources/images/brightness.png")
;(save-image (adjust-contrast image 1.5) "./resources/images/contrast.png")
;(save-image (convert-to-one-channel image "red") "./resources/images/red.png")
;(save-image (convert-to-one-channel image "green") "./resources/images/green.png")
;(save-image (convert-to-one-channel image "blue") "./resources/images/blue.png")
;(save-image (apply-median-filter image 2) "./resources/images/median.png")
;(save-image (apply-blur-filter image 20) "./resources/images/blurred.png")
;(save-image (draw-line-on-image image 100 175 400 175 10.0 "#b7ef7b") "./resources/images/drawn-line.png")
;(save-image (draw-rectangle-on-image image 20 20 200 100 10.0 "#000080") "./resources/images/drawn-rectangle.png")
;(save-image (draw-ellipse-on-image image 100 100 150 150 10.0 "#8a7443") "./resources/images/drawn-ellipse.png")
;(save-image (set-watermark-text image "Watermark" 90 200 "Arial" 64 "#ffffff") "./resources/images/watermark-text.png")
;(save-image (set-watermark-image image watermark-image 400 300) "./resources/images/watermark-image.png")
;(save-image (set-watermark-image image watermark-image) "./resources/images/watermark-image-tiled.png")
;(save-image (overlay-images image image2 0.5) "./resources/images/overlay.png")