(ns ^:figwheel-always spotisearch.artists
    (:require [om.core :as om :include-macros true]
              [om.dom :as d :include-macros true]))

(defn artist-item
  [artist state]
  (let [name (get artist "name")
        image-url (get (first (get artist "images")) "url")
        image-style #js {:background-image (str "url(" image-url ")")}]
    (reify
      om/IRender
      (render [_]
        (d/div #js {:className "artist-container"}
          (d/div #js {:className "artist-image-container"
                      :style image-style} "")
          (d/h1 {} name))))))

(defn artists-view
  [artists state]
  (reify
    om/IRender
    (render [this]
      (if (some? artists)
        (d/div #js {:className "panel panel-default panel-body"}
          (d/h4 nil "Artists")
          (apply d/div nil
            (om/build-all artist-item artists)))
        nil))))
