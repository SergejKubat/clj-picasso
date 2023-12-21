;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns clj-picasso.filters
  (:import (java.awt.image BufferedImage)))

(defn create-pixel [red green blue]
  "Create new pixel value."
  (bit-or (bit-shift-left red 16)
          (bit-shift-left green 8)
          blue))

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