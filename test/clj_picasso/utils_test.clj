(ns clj-picasso.utils-test
  (:require [midje.sweet :refer :all]
            [clj-picasso.loading :as loading]
            [clj-picasso.utils :as utils])
  (:import (java.awt.image BufferedImage)))

(def ^BufferedImage image (loading/load-from-path "./resources/images/input.png"))

(facts "Testing clamp function."
       (utils/clamp 5 15 20) => 15
       (utils/clamp 25 15 20) => 20
       (utils/clamp 18 15 20) => 18)

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
       (utils/calculate-mean-intensity image (.getWidth image) (.getHeight image)) => 297.103472)
