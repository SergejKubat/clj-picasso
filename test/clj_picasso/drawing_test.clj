;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.drawing-test
  (:require [clj-picasso.comparison :as comp]
            [clj-picasso.drawing :as drawing]
            [clj-picasso.loading :as loading]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image (loading/load-from-path "./resources/images/input.png"))

(fact "Testing draw line function."
      (comp/equal-images? (drawing/draw-line image 100 175 400 175 10.0 "#b7ef7b")
                          (loading/load-from-path "./resources/images/drawn-line.png")) => true)

(fact "Testing draw rectangle function."
      (comp/equal-images? (drawing/draw-rectangle image 20 20 200 100 10.0 "#000080")
                          (loading/load-from-path "./resources/images/drawn-rectangle.png")) => true)

(fact "Testing draw ellipse function."
      (comp/equal-images? (drawing/draw-ellipse image 100 100 150 150 10.0 "#8a7443")
                          (loading/load-from-path "./resources/images/drawn-ellipse.png")) => true)