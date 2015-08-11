(ns ^:figwheel-always spotisearch.tracks
    (:require [om-bootstrap.table :refer [table]]
              [om.core :as om :include-macros true]
              [clojure.string :as str]
              [om.dom :as d :include-macros true]))

(defn tracks-track-row
  [track owner]
  (let [title (get track "name")
        artists (get track "artists")
        album (get track "album")]
    (reify om/IRender
      (render [_]
        (d/tr nil
          (d/td nil
            (d/a #js {:href (get track "uri")}
                 (get track "name")))
          (d/td nil
            (d/a #js {:href "#"}
                 (get (first artists) "name")))
          (d/td nil 
            (d/a #js {:href (get album "uri")}
                 (get album "name"))))))))

(defn tracks-table
  [tracks owner]
  (reify om/IRender
    (render [this]
      (d/div #js {:className "panel panel-default panel-body"}
        (d/h4 {} "Tracks")
        (table {}
          (d/thead nil
            (d/tr nil
              (d/td nil "Title")
              (d/td nil "Artists")
              (d/td nil "Album")))
          (apply d/tbody nil
            (om/build-all tracks-track-row tracks)))))))
