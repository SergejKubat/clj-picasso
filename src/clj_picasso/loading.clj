;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.loading
  (:import (java.awt.image BufferedImage)
           (java.io File IOException)
           (javax.imageio ImageIO)))

(defn ^BufferedImage load-from-path [^String image-path]
  "Load an image from specified file path."
  (try
    (let [image (ImageIO/read (File. image-path))]
      image)
    (catch IOException ex
      (println (str "Error loading image: " (.getMessage ex)))
      nil)))

(defn ^BufferedImage load-from-file [^File image-file]
  "Load an image from specified file."
  (try (ImageIO/read image-file)
       (catch IOException ex
         (println (str "Error occurs during reading: " (.getMessage ex)))
         nil)))

(defn save-image [^BufferedImage image ^String file-path]
  "Save the given image to the specified file path."
  (try
    (ImageIO/write image "png" (File. (str file-path)))
    (catch IOException ex
      (println "Error saving image:" (.getMessage ex)))))