;;   Copyright (c) Sergej Kubat. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) or later
;;   which can be found in the file LICENSE at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(defproject org.clojars.stavrogin/clj-picasso "1.0.0"
  :author "Sergej Kubat"
  :description "Image processing library for Clojure."
  :url "https://github.com/SergejKubat/clj_picasso"
  :scm {:name "git"
        :url "https://github.com/SergejKubat/clj_picasso"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org/"}]]
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:dependencies [[midje "1.10.9"]]
                   :plugins [[lein-midje "3.2.1"]]
                   :global-vars {*warn-on-reflection* true
                                 *unchecked-math* :warn-on-boxed
                                 *print-length* 16}
                   }}
  :source-paths ["src"]
  :repl-options {:init-ns clj-picasso.core})