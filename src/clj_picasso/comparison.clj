;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.comparison
  (:import (java.awt.image BufferedImage)))

(defn ^BufferedImage mean-squared-error [^BufferedImage image1 ^BufferedImage image2]
  "Calculate the mean squared error between two images."
  (let [width1 (.getWidth image1)
        height1 (.getHeight image1)
        width2 (.getWidth image2)
        height2 (.getHeight image2)]
    (if (and (= width1 width2) (= height1 height2))
      (let [pixels-count (* width1 height1)
            ^long sum (reduce + (for [x (range width1)
                                      y (range height1)]
                                  (let [pixel1 (.getRGB image1 x y)
                                        pixel2 (.getRGB image2 x y)
                                        red1 (bit-and (bit-shift-right pixel1 16) 0xFF)
                                        red2 (bit-and (bit-shift-right pixel2 16) 0xFF)
                                        green1 (bit-and (bit-shift-right pixel1 8) 0xFF)
                                        green2 (bit-and (bit-shift-right pixel2 8) 0xFF)
                                        blue1 (bit-and pixel1 0xFF)
                                        blue2 (bit-and pixel2 0xFF)
                                        red-diff (- red1 red2)
                                        blue-diff (- green1 green2)
                                        green-diff (- blue1 blue2)
                                        mse (+ (* red-diff red-diff) (* green-diff green-diff) (* blue-diff blue-diff))]
                                    mse)))]
        (double (/ sum (* 3 pixels-count))))
      (throw (IllegalArgumentException. "Image dimensions do not match.")))))