## Batch processing

### Example

```
(require '[clj-picasso.core :as picasso])

; Function for image processing
(defn process-image [^File image-file]
  (let [image (load-from-file image-file)
        cropped-image (crop-image image 0 0 250 250)
        mirrored-image (mirror-image cropped-image)
        sepia-image (filters/convert-to-sepia mirrored-image)
        output-path (.getParent image-file)]
    (save-image sepia-image (str output-path "/processed_" (.getName image-file)))))

; Process directory
(picasso/process-images-in-directory ""path/to/directory" process-image)
```