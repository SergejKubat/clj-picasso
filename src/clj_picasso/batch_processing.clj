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
  "Takes a directory path as a parameter and returns a lazy sequence
  of all files in that directory (excluding subdirectories)."
  (->> (file-seq (io/file directory))                       ;; recursively traverse the directory
       (filter #(not (.isDirectory ^File %)))))             ;; filter out directories

(defn process-images-in-directory [^String directory process-fn]
  "Takes a directory path as a parameter and process all images in that directory."
  (let [image-files (get-all-files directory)]
    (doseq [image-file image-files]                         ;; iterate through image files
      (process-fn image-file))))                            ;; apply process function to image file