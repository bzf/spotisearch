(defproject spotisearch-cljs "0.1.0-SNAPSHOT"
  :description "Small example in Clojurescript using Om"
  :url "http://github.com/bzf/spotisearch"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3297"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [sablono "0.3.4"]
                 [org.omcljs/om "0.8.8"]
                 [racehub/om-bootstrap "0.5.3"]
                 [cljs-ajax "0.3.14"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.5"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]

              :figwheel { :on-jsload "spotisearch.core/on-js-reload" }

              :compiler {:main spotisearch.core
                         :asset-path "js/compiled/out"
                         :output-to "resources/public/js/compiled/spotbot_cljs.js"
                         :output-dir "resources/public/js/compiled/out"
                         :source-map-timestamp true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/spotbot_cljs.js"
                         :main spotisearch.core
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {
             :css-dirs ["resources/public/css"] ;; watch and update CSS
             })
