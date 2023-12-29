;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.drawing
  (:require [clj-picasso.utils :as utils])
  (:import (java.awt BasicStroke Color Graphics2D)
           (java.awt.geom Ellipse2D$Double Line2D$Double Rectangle2D$Double)
           (java.awt.image BufferedImage)))

(defn ^BufferedImage draw-line
  "Draws a line on specified image based on start and end coordinates, stroke size and color."
  ([^BufferedImage image x1 y1 x2 y2 stroke ^String color]
   (let [width (.getWidth image)
         height (.getHeight image)
         output-image (BufferedImage. width height (.getType image))
         ^Graphics2D graphics (.getGraphics output-image)
         line (Line2D$Double. x1 y1 x2 y2)
         channels (utils/get-color-channels color)
         color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
     (.drawImage graphics image 0 0 width height nil)
     (.setColor graphics color)
     (.setStroke graphics (BasicStroke. stroke))
     (.draw graphics line)
     (.dispose graphics)
     output-image))
  ([^BufferedImage image x1 y1 x2 y2 stroke]
   (draw-line image x1 y1 x2 y2 stroke "#ffffff"))          ;; default color is white
  ([^BufferedImage image x1 y1 x2 y2]
   (draw-line image x1 y1 x2 y2 1.0 "#ffffff")))            ;; default stroke is 1.0, and default color is white

(defn ^BufferedImage draw-rectangle
  "Draws a rectangle on specified image based on start coordinates, width, height, stroke size and color."
  ([^BufferedImage image x y rectangle-width rectangle-height stroke ^String color]
   (let [width (.getWidth image)
         height (.getHeight image)
         output-image (BufferedImage. width height (.getType image))
         ^Graphics2D graphics (.getGraphics output-image)
         rectangle (Rectangle2D$Double. x y rectangle-width rectangle-height)
         channels (utils/get-color-channels color)
         color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
     (.drawImage graphics image 0 0 width height nil)
     (.setColor graphics color)
     (.setStroke graphics (BasicStroke. stroke))
     (.draw graphics rectangle)
     (.dispose graphics)
     output-image))
  ([^BufferedImage image x y rectangle-width rectangle-height stroke]
   (draw-rectangle image x y rectangle-width rectangle-height stroke "#ffffff")) ;; default color is white
  ([^BufferedImage image x y rectangle-width rectangle-height]
   ;; default stroke is 1.0, and default color is white
   (draw-rectangle image x y rectangle-width rectangle-height 1.0 "#ffffff")))

(defn ^BufferedImage draw-ellipse
  "Draws an ellipse on specified image based on start coordinates, width, height, stroke size and color."
  ([^BufferedImage image x y ellipse-width ellipse-height stroke ^String color]
   (let [width (.getWidth image)
         height (.getHeight image)
         output-image (BufferedImage. width height (.getType image))
         ^Graphics2D graphics (.getGraphics output-image)
         ellipse (Ellipse2D$Double. x y ellipse-width ellipse-height)
         channels (utils/get-color-channels color)
         color (Color. (int (:red channels)) (int (:green channels)) (int (:blue channels)))]
     (.drawImage graphics image 0 0 width height nil)
     (.setColor graphics color)
     (.setStroke graphics (BasicStroke. stroke))
     (.draw graphics ellipse)
     (.dispose graphics)
     output-image))
  ([^BufferedImage image x y ellipse-width ellipse-height stroke]
   (draw-ellipse image x y ellipse-width ellipse-height stroke "#ffffff")) ;; default color is white
  ([^BufferedImage image x y ellipse-width ellipse-height]
   ;; default stroke is 1.0, and default color is white
   (draw-ellipse image x y ellipse-width ellipse-height 1.0 "#ffffff")))