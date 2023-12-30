## Watermark

```clojure
(ns user-ns.core
  (:require [clj-picasso.loading :refer :all]
            [clj-picasso.watermark :refer :all]))
```

Then load images:

```clojure
;; Load first image
(def image (load-from-path "path/image1.png"))
;; Load watermark image
(def watermark-image (load-from-path "path/watermark-image.png"))
```

### Set text watermark

```clojure
;; Set watermark
(def output-image (set-watermark-text image "Watermark" 175 200))

;; Save output image
(save-image output-image "path/output-image.png")
```

Output:

![Output](../resources/images/watermark-text.png)

### Set image watermark

```clojure
;; Set watermark
(def output-image (set-watermark-image image watermark-image 400 300))

;; Save output image
(save-image output-image "path/output-image.png")
```

Output:

![Output](../resources/images/watermark-image.png)

### Set tiled image watermark

```clojure
;; Set watermark
(def output-image (set-watermark-image image watermark-image))

;; Save output image
(save-image output-image "path/output-image.png")
```

Output:

![Output](../resources/images/watermark-image-tiled.png)