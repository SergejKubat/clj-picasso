## Overlay

### Overlay two images

```
(require '[clj-picasso.core :as picasso])

;; Load first image
(def image1 (picasso/load-from-path "path/to/image1.png"))
;; Load second image
(def image2 (picasso/load-from-path "path/to/image2.png"))

;; Overlay images
(def output-image (overlay-images image1 image2 0.5))

;; Save output image
(picasso/save-image output-image "path/to/output-image.png")
```

Output:

![Output](../resources/images/overlay.png)