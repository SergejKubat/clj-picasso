;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.utils-test
  (:require [clj-picasso.loading :as loading]
            [clj-picasso.utils :as utils]
            [midje.sweet :refer :all])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image1 (loading/load-from-path "./resources/images/input.png"))
(def ^BufferedImage image2 (loading/load-from-path "./resources/images/input-2.png"))

(facts "Testing clamp function."
       (utils/clamp 5 150 200) => 150
       (utils/clamp 250 150 200) => 200
       (utils/clamp 150 150 200) => 150
       (utils/clamp 175 150 200) => 175
       (utils/clamp 195 150 200) => 195)

(facts "Testing hex to decimal function."
       (utils/hex-color-to-decimal "#ffffff") => 16777215
       (utils/hex-color-to-decimal "#000000") => 0
       (utils/hex-color-to-decimal "#ff0000") => 16711680
       (utils/hex-color-to-decimal "#00ff00") => 65280
       (utils/hex-color-to-decimal "#0000ff") => 255)

(facts "Testing get pixel channels function."
       (utils/get-pixel-channels 16777215) => {:red 255 :green 255 :blue 255}
       (utils/get-pixel-channels 0) => {:red 0 :green 0 :blue 0}
       (utils/get-pixel-channels 16711680) => {:red 255 :green 0 :blue 0}
       (utils/get-pixel-channels 65280) => {:red 0 :green 255 :blue 0}
       (utils/get-pixel-channels 255) => {:red 0 :green 0 :blue 255})

(facts "Testing get color channels function."
       (utils/get-color-channels "#ffffff") => {:red 255 :green 255 :blue 255}
       (utils/get-color-channels "#000000") => {:red 0 :green 0 :blue 0}
       (utils/get-color-channels "#ff0000") => {:red 255 :green 0 :blue 0}
       (utils/get-color-channels "#00ff00") => {:red 0 :green 255 :blue 0}
       (utils/get-color-channels "#0000ff") => {:red 0 :green 0 :blue 255})

(facts "Testing create pixel function."
       (utils/create-pixel 255 255 255) => 16777215
       (utils/create-pixel 0 0 0) => 0
       (utils/create-pixel 255 0 0) => 16711680
       (utils/create-pixel 0 255 0) => 65280
       (utils/create-pixel 0 0 255) => 255)

(facts "Testing calculate mean intensity function."
       (utils/calculate-mean-intensity image1 (.getWidth image1) (.getHeight image1)) => 297.103472
       (utils/calculate-mean-intensity image2 (.getWidth image2) (.getHeight image2)) => 408.7429386666667)

(facts "Testing get neighborhoods function."
       (utils/get-neighborhoods image1 (.getWidth image1) (.getHeight image1) 0 0 3)
       => (list (list 173 173 173) (list 173 173 173) (list 181 181 212))
       (utils/get-neighborhoods image1 (.getWidth image1) (.getHeight image1) 10 10 3)
       => (list (list 185 177 177) (list 166 185 185) (list 116 158 158))
       (utils/get-neighborhoods image2 (.getWidth image2) (.getHeight image2) 0 0 3)
       => (list (list 174 174 172) (list 174 174 172) (list 172 172 171))
       (utils/get-neighborhoods image2 (.getWidth image2) (.getHeight image2) 100 100 3)
       => (list (list 121 121 121) (list 123 122 121) (list 124 122 121)))

(facts "Testing calculate average color function."
       (utils/calculate-average-color (list (list 173 173 173) (list 173 173 173) (list 181 181 212))) => 11513786
       (utils/calculate-average-color (list (list 185 177 177) (list 166 185 185) (list 116 158 158))) => 10202541
       (utils/calculate-average-color (list (list 174 174 172) (list 174 174 172) (list 172 172 171))) => 11382187
       (utils/calculate-average-color (list (list 121 121 121) (list 123 122 121) (list 124 122 121))) => 8026489)