;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.core
  (:require [clj-picasso.loading :as loading]
            [clj-picasso.basic-operations :as basic-operations]
            [clj-picasso.filters :as filters]
            [clj-picasso.drawing :as drawing]
            [clj-picasso.watermark :as watermark]
            [clj-picasso.overlay :as overlay])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image1 (loading/load-from-path "./resources/images/input.png"))
(def ^BufferedImage image2 (loading/load-from-path "./resources/images/input-2.png"))
(def ^BufferedImage watermark-image (loading/load-from-path "./resources/images/watermark-example.png"))

;(loading/save-image (basic-operations/resize-image image1 400 225)
;                    "./resources/images/resized.png")
;(loading/save-image (basic-operations/scale-image image1 2.0)
;                    "./resources/images/scaled.png")
;(loading/save-image (basic-operations/crop-image image1 200 200)
;                    "./resources/images/cropped.png")
;(loading/save-image (basic-operations/rotate-image image1 180)
;                    "./resources/images/rotated.png")
;(loading/save-image (basic-operations/mirror-image image1)
;                    "./resources/images/mirrored.png")
;(loading/save-image (basic-operations/generate-random-image 200 200)
;                    "./resources/images/random.png")
;(loading/save-image (filters/apply-grayscale image1)
;                    "./resources/images/grayscale.png")
;(loading/save-image (filters/apply-sepia image1)
;                    "./resources/images/sepia.png")
;(loading/save-image (filters/apply-negative image1)
;                    "./resources/images/negative.png")
;(loading/save-image (filters/apply-one-channel image1 "red")
;                    "./resources/images/red.png")
;(loading/save-image (filters/apply-one-channel image1 "green")
;                    "./resources/images/green.png")
;(loading/save-image (filters/apply-one-channel image1 "blue")
;                    "./resources/images/blue.png")
;(loading/save-image (filters/apply-median image1 2)
;                    "./resources/images/median.png")
;(loading/save-image (filters/apply-blur image1 20)
;                    "./resources/images/blurred.png")
;(loading/save-image (filters/adjust-brightness image1 1.5)
;                    "./resources/images/brightness.png")
;(loading/save-image (filters/adjust-contrast image1 1.5)
;                    "./resources/images/contrast.png")
;(loading/save-image (drawing/draw-line image1 100 175 400 175 10.0 "#b7ef7b")
;                    "./resources/images/drawn-line.png")
;(loading/save-image (drawing/draw-rectangle image1 20 20 200 100 10.0 "#000080")
;                    "./resources/images/drawn-rectangle.png")
;(loading/save-image (drawing/draw-ellipse image1 100 100 150 150 10.0 "#8a7443")
;                    "./resources/images/drawn-ellipse.png")
;(loading/save-image (watermark/set-watermark-text image1 "Watermark" 90 200 "Arial" 64 "#ffffff")
;                    "./resources/images/watermark-text.png")
;(loading/save-image (watermark/set-watermark-image image1 watermark-image 400 300)
;                    "./resources/images/watermark-image.png")
;(loading/save-image (watermark/set-watermark-image image1 watermark-image)
;                    "./resources/images/watermark-image-tiled.png")
;(loading/save-image (overlay/overlay-images image1 image2 0.5)
;                    "./resources/images/overlay.png")