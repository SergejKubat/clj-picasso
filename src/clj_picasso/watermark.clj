;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.watermark
  (:import (java.awt Font)
           (java.awt.image BufferedImage)))

(defn ^BufferedImage set-watermark-text [^BufferedImage image ^String text x y ^String font-family font-size]
  "Add a text watermark to the original image at the specified position, font family and font size."
  (let [width (.getWidth image)
        height (.getHeight image)
        watermarked-image (BufferedImage. width height (.getType image))
        ; channels (get-color-channels color)
        ; color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))
        ]
    (doto (.createGraphics watermarked-image)
      (.drawImage image 0 0 width height nil)
      (.setFont (Font. font-family Font/PLAIN font-size))
      ; (.setColor color)
      (.drawString text (int x) (int y))
      (.dispose))
    watermarked-image))

(defn ^BufferedImage set-watermark-image
  ([^BufferedImage image ^BufferedImage watermark-image x y]
   "Add a image watermark to the original image at the specified position."
   (let [width (.getWidth image)
         height (.getHeight image)
         watermarked-image (BufferedImage. width height (.getType image))]
     (doto (.createGraphics watermarked-image)
       (.drawImage image 0 0 width height nil)
       (.drawImage watermark-image (int x) (int y) nil)
       (.dispose))
     watermarked-image))
  ([^BufferedImage original-image ^BufferedImage watermark-image]
   "Add a image watermark to the original image by tiling it."
   (let [width (.getWidth original-image)
         height (.getHeight original-image)
         watermark-width (.getWidth watermark-image)
         watermark-height (.getHeight watermark-image)
         watermarked-image (BufferedImage. width height (.getType original-image))
         graphics (.createGraphics watermarked-image)]
     (.drawImage graphics original-image 0 0 nil)
     (doseq [x (range (quot width watermark-width))]
       (doseq [y (range (quot height watermark-height))]
         (.drawImage graphics watermark-image (* (int x) watermark-width) (* (int y) watermark-height) nil)))
     (.dispose graphics)
     watermarked-image)))