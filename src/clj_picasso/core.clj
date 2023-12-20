(ns clj-picasso.core
  (:import (java.awt.image BufferedImage)
           [javax.imageio ImageIO]
           [java.io File IOException]))

(defn load-image [file-path]
  "This function loads an image from a file."
  (try
    (let [image (ImageIO/read (File. (str file-path)))]
      image)
    (catch IOException ex
           (println (str "Error loading image: " (.getMessage ex)))
      nil)))

(defn save-image [image file-path]
  "Save the given image to the specified file path."
  (try
    (ImageIO/write ^BufferedImage image "png" (File. (str file-path)))
    (catch IOException ex
      (println "Error saving image:" (.getMessage ex)))))

(def image (load-image "./resources/images/input.png"))

(save-image image "./resources/images/output.png")