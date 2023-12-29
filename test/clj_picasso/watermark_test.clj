;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.watermark-test
  (:require [clj-picasso.comparison :as comp]
            [clj-picasso.watermark :as watermark]
            [clj-picasso.loading :as loading]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image (loading/load-from-path "./resources/images/input.png"))
(def ^BufferedImage watermark-image (loading/load-from-path "./resources/images/watermark-example.png"))

(facts "Testing set watermark text function."
       (comp/equal-images? (watermark/set-watermark-text image "Watermark" 175 200)
                           (loading/load-from-path "./resources/images/watermark-text.png")) => true
       (comp/equal-images? (watermark/set-watermark-text image "Watermark" 175 200 "Arial")
                           (loading/load-from-path "./resources/images/watermark-text.png")) => true
       (comp/equal-images? (watermark/set-watermark-text image "Watermark" 175 200 "Arial" 32)
                           (loading/load-from-path "./resources/images/watermark-text.png")) => true
       (comp/equal-images? (watermark/set-watermark-text image "Watermark" 175 200 "Arial" 32 "#ffffff")
                           (loading/load-from-path "./resources/images/watermark-text.png")) => true)

(facts "Testing set watermark image function."
       (comp/equal-images? (watermark/set-watermark-image image watermark-image 400 300)
                           (loading/load-from-path "./resources/images/watermark-image.png")) => true
       (comp/equal-images? (watermark/set-watermark-image image watermark-image)
                           (loading/load-from-path "./resources/images/watermark-image-tiled.png")) => true)