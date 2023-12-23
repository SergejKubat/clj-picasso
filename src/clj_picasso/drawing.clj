;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.drawing
  (:require [clojure.string :as string])
  (:import (java.awt BasicStroke Color Graphics2D)
           (java.awt.geom Ellipse2D$Double Line2D$Double Rectangle2D$Double)
           (java.awt.image BufferedImage)))

(defn hex-color-to-decimal [hex-color]
  (let [hex-without-hash (if (string/starts-with? hex-color "#")
                           (subs hex-color 1)
                           hex-color)]
    (Integer/parseInt hex-without-hash 16)))

(defn get-pixel-channels [^long pixel]
  { :red (bit-and (bit-shift-right pixel 16) 0xFF)
    :green (bit-and (bit-shift-right pixel 8) 0xFF)
    :blue (bit-and pixel 0xFF)})

(defn get-color-channels [^String color]
  (let [pixel (hex-color-to-decimal color)]
    (get-pixel-channels pixel)))

; @TODO: remove side-effects
(defn ^BufferedImage draw-line-on-image [^BufferedImage image x1 y1 x2 y2 stroke ^String color]
  (let [^Graphics2D graphics (.getGraphics image)
        line (Line2D$Double. x1 y1 x2 y2)
        channels (get-color-channels color)
        color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
    (.setColor graphics color)
    (.setStroke graphics (BasicStroke. stroke))
    (.draw graphics line)
    (.dispose graphics))
  image)

; @TODO: remove side-effects
(defn ^BufferedImage draw-rectangle-on-image [^BufferedImage image x y rectangle-width rectangle-height stroke ^String color]
  (let [^Graphics2D graphics (.getGraphics image)
        rectangle (Rectangle2D$Double. x y rectangle-width rectangle-height)
        channels (get-color-channels color)
        color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
    (.setColor graphics color)
    (.setStroke graphics (BasicStroke. stroke))
    (.draw graphics rectangle)
    (.dispose graphics))
  image)

; @TODO: remove side-effects
(defn ^BufferedImage draw-ellipse-on-image [^BufferedImage image x y ellipse-width ellipse-height stroke ^String color]
  (let [^Graphics2D graphics (.getGraphics image)
        ellipse (Ellipse2D$Double. x y ellipse-width ellipse-height)
        channels (get-color-channels color)
        color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
    (.setColor graphics color)
    (.setStroke graphics (BasicStroke. stroke))
    (.draw graphics ellipse)
    (.dispose graphics))
  image)