## Watermark

### Set text watermark

```
(require '[clj-picasso.core :as picasso])

;; Load an image
(def image (picasso/load-from-path "path/to/image.png"))

;; Set watermark
(def output-image (picasso/set-watermark-text image "Watermark" 175 200))

;; Save output image
(picasso/save-image output-image "path/to/output-image.png")
```

Output:

![Output](../resources/images/watermark-text.png)

### Set image watermark

```
(require '[clj-picasso.core :as picasso])

;; Load an image
(def image (picasso/load-from-path "path/to/image.png"))
;; Load watermark image
(def image (picasso/load-from-path "path/to/watermark-image.png"))

;; Set watermark
(def output-image (picasso/set-watermark-image image watermark-image 400 300))

;; Save output image
(picasso/save-image output-image "path/to/output-image.png")
```

Output:

![Output](../resources/images/watermark-image.png)

### Set tiled image watermark

```
(require '[clj-picasso.core :as picasso])

;; Load an image
(def image (picasso/load-from-path "path/to/image.png"))
;; Load watermark image
(def image (picasso/load-from-path "path/to/watermark-image.png"))

;; Set watermark
(def output-image (picasso/set-watermark-image image watermark-image))

;; Save output image
(picasso/save-image output-image "path/to/output-image.png")
```

Output:

![Output](../resources/images/watermark-image-tiled.png)