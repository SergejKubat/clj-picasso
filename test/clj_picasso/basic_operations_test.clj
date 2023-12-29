;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.basic-operations-test
  (:require [clj-picasso.basic-operations :as bs]
            [clj-picasso.comparison :as comp]
            [clj-picasso.loading :as loading]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image (loading/load-from-path "./resources/images/input.png"))

(fact "Testing resize function."
      (comp/equal-images? (bs/resize-image image 400 225) (loading/load-from-path "./resources/images/resized.png"))
      => true)

(fact "Testing scale function."
      (comp/equal-images? (bs/scale-image image 2.0) (loading/load-from-path "./resources/images/scaled.png"))
      => true)

(facts "Testing crop function."
       (comp/equal-images? (bs/crop-image image 200 200) (loading/load-from-path "./resources/images/cropped.png"))
       => true
       (comp/equal-images? (bs/crop-image image 0 0 200 200) (loading/load-from-path "./resources/images/cropped.png"))
       => true
       (bs/crop-image image 1000 1000) => (throws IllegalArgumentException))

(fact "Testing rotate function."
      (comp/equal-images? (bs/rotate-image image 180) (loading/load-from-path "./resources/images/rotated.png"))
      => true)

(fact "Testing mirror function."
      (comp/equal-images? (bs/mirror-image image) (loading/load-from-path "./resources/images/mirrored.png"))
      => true)