;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:author "Sergej Kubat"}
  clj-picasso.batch-processing
  (:require [clojure.java.io :as io])
  (:import (java.io File)))

(defn get-all-files [^String directory]
  (->> (file-seq (io/file directory))
       (filter #(not (.isDirectory ^File %)))))

(defn process-images-in-directory [^String directory process-fn]
  (let [image-files (get-all-files directory)]
    (doseq [image-file image-files]
      (process-fn image-file))))

;(defn process-image [^File image-file]
;  (let [image (load-image image-file)
;        cropped-image (crop-image image 0 0 250 250)
;        mirrored-image (mirror-image cropped-image)
;        sepia-image (filters/convert-to-sepia mirrored-image)
;        output-path (.getParent image-file)]
;    (save-image sepia-image (str output-path "/processed_" (.getName image-file)))))