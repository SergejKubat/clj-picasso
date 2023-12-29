## Comparison

### Check if images are equal

```
(require '[clj-picasso.core :as picasso])

;; Load first image
(def image1 (picasso/load-from-path "path/to/image1.png"))
;; Load second image
(def image2 (picasso/load-from-path "path/to/image2.png"))

(picasso/equal-images? image1 image2) ;; Returns true or false
```

### Calculate mean squared error

```
(require '[clj-picasso.core :as picasso])

;; Load first image
(def image1 (picasso/load-from-path "path/to/image1.png"))
;; Load second image
(def image2 (picasso/load-from-path "path/to/image2.png"))

(picasso/mean-squared-error image1 image2) ;; Returns mean squared error
```