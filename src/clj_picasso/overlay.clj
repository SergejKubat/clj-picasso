;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.overlay
  (:require [clj-picasso.utils :as utils])
  (:import (java.awt.image BufferedImage)))

(defn ^BufferedImage overlay-images [^BufferedImage image1 ^BufferedImage image2 ^double transparency]
  "Overlay two images with adjustable transparency."
  (let [width (.getWidth image1)
        height (.getHeight image1)
        output-image (BufferedImage. width height (.getType image1))]
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
              new-pixel (utils/create-pixel alpha
                                      (int (+ (* (- 1.0 transparency) red1) (* transparency red2)))
                                      (int (+ (* (- 1.0 transparency) green1) (* transparency green2)))
                                      (int (+ (* (- 1.0 transparency) blue1) (* transparency blue2))))]
          (.setRGB output-image x y new-pixel))))
    output-image))