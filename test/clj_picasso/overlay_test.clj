;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.overlay-test
  (:require [clj-picasso.comparison :as comp]
            [clj-picasso.loading :as loading]
            [clj-picasso.overlay :as overlay]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image1 (loading/load-from-path "./resources/images/input.png"))
(def ^BufferedImage image2 (loading/load-from-path "./resources/images/input-2.png"))

(fact "Testing overlay function."
      (comp/equal-images? (overlay/overlay-images image1 image2 0.5)
                          (loading/load-from-path "./resources/images/overlay.png")) => true)