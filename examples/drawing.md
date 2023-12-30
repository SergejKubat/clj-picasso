## Drawing

First, you need to load the necessary namespaces:

```clojure
(ns user-ns.core
  (:require [clj-picasso.loading :refer :all]
            [clj-picasso.drawing :refer :all]))
```

Then load an image:

```clojure
;; Load an image
(def image (load-from-path "path/image.png"))
```

### Draw line on image

```clojure
;; Draw line on image
(def output-image (draw-line image 100 175 400 175 10.0 "#b7ef7b"))

;; Save output image
(save-image output-image "path/output-image.jpg")
```

Output:

![Output](../resources/images/drawn-line.png)

### Draw rectangle on image

```clojure
;; Draw rectangle on image
(def output-image (draw-rectangle image 20 20 200 100 10.0 "#000080"))

;; Save output image
(save-image output-image "path/output-image.jpg")
```

Output:

![Output](../resources/images/drawn-rectangle.png)

### Draw ellipse on image

```clojure
; Draw ellipse on image
(def output-image (draw-ellipse image 100 100 150 150 10.0 "#8a7443"))

;; Save output image
(save-image output-image "path/output-image.jpg")
```

Output:

![Output](../resources/images/drawn-ellipse.png)