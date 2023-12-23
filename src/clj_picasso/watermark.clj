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

(defn ^BufferedImage set-watermark-text [^BufferedImage image x y ^String text ^String font-family font-size]
  (let [width (.getWidth image)
        height (.getHeight image)
        marked-image (BufferedImage. width height (.getType image))
        ; channels (get-color-channels color)
        ; color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))
        ]
    (doto (.createGraphics marked-image)
      (.drawImage image 0 0 width height nil)
      (.setFont (Font. font-family Font/PLAIN font-size))
      ; (.setColor color)
      (.drawString text (int x) (int y))
      (.dispose))
    marked-image))