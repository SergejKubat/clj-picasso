;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.filters-test
  (:require [clj-picasso.comparison :as comp]
            [clj-picasso.filters :as filters]
            [clj-picasso.loading :as loading]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image (loading/load-from-path "./resources/images/input.png"))

(fact "Testing apply grayscale function."
      (comp/equal-images? (filters/apply-grayscale image)
                          (loading/load-from-path "./resources/images/grayscale.png")) => true)

(fact "Testing apply sepia function."
      (comp/equal-images? (filters/apply-sepia image)
                          (loading/load-from-path "./resources/images/sepia.png")) => true)

(fact "Testing apply negative function."
      (comp/equal-images? (filters/apply-negative image)
                          (loading/load-from-path "./resources/images/negative.png")) => true)

(facts "Testing apply one channel function."
       (comp/equal-images? (filters/apply-one-channel image "red")
                           (loading/load-from-path "./resources/images/red.png")) => true
       (comp/equal-images? (filters/apply-one-channel image "green")
                           (loading/load-from-path "./resources/images/green.png")) => true
       (comp/equal-images? (filters/apply-one-channel image "blue")
                           (loading/load-from-path "./resources/images/blue.png")) => true)

(fact "Testing apply median function."
      (comp/equal-images? (filters/apply-median image 2)
                          (loading/load-from-path "./resources/images/median.png")) => true)

(fact "Testing apply blur function."
      (comp/equal-images? (filters/apply-blur image 5)
                          (loading/load-from-path "./resources/images/blurred.png")) => true)

(fact "Testing adjust brightness function."
      (comp/equal-images? (filters/adjust-brightness image 1.5)
                          (loading/load-from-path "./resources/images/brightness.png")) => true)

(fact "Testing adjust contrast function."
      (comp/equal-images? (filters/adjust-contrast image 1.5)
                          (loading/load-from-path "./resources/images/contrast.png")) => true)