;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.comparison-test
  (:require [clj-picasso.comparison :as comp]
            [clj-picasso.loading :as loading]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image1 (loading/load-from-path "./resources/images/input.png"))
(def ^BufferedImage image2 (loading/load-from-path "./resources/images/input-2.png"))

(facts "Testing equal images function."
       (comp/equal-images? image1 image1) => true
       (comp/equal-images? image1 image2) => false)

(facts "Testing equal images function."
       (comp/mean-squared-error image1 image1) => 0.0
       (comp/mean-squared-error image1 image2) => 11221.82154666667
       (comp/mean-squared-error image1 (loading/load-from-path "./resources/images/drawn-line.png"))
       => 522.4540515555556
       (comp/mean-squared-error image1 (loading/load-from-path "./resources/images/drawn-rectangle.png"))
       => 899.4241368888889
       (comp/mean-squared-error image1 (loading/load-from-path "./resources/images/watermark-text.png"))
       => 501.3702808888889
       (comp/mean-squared-error image1 (loading/load-from-path "./resources/images/resized.png"))
       => (throws IllegalArgumentException))