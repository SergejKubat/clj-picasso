## Comparison

```clojure
(ns user-ns.core
  (:require [clj-picasso.loading :refer :all]
            [clj-picasso.comparison :refer :all]))
```

Then load images:

```clojure
;; Load first image
(def image1 (load-from-path "path/image1.png"))
;; Load second image
(def image2 (load-from-path "path/image2.png"))
```

### Check if images are equal

```clojure
(equal-images? image1 image2) ;; Returns true or false
```

### Calculate mean squared error

```clojure
(mean-squared-error image1 image2) ;; Returns mean squared error
```