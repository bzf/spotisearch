(ns ^:figwheel-always spotisearch.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as d :include-macros true]
              [om-bootstrap.input :refer [input]]
              [om-bootstrap.button :refer [button]]
              [om-bootstrap.random :as r]
              [spotisearch.artists :as artists]
              [spotisearch.tracks :as tracks]
              [spotisearch.spotify :as api]))

(enable-console-print!)

(defonce app-state (atom {:search-result {:artists nil
                                          :tracks nil
                                          :albums nil
                                          :query nil}
                          :search-query ""}))

(defn handle-change
  "Grab the input element via the `input` reference."
  [owner state]
  (let [node (om/get-node owner "input")]
    (om/transact! state :search-query (fn [_] (.-value node)))))

(defn make-search
  [query]
  (api/search
    query
    "track,artist"
    (fn [result]
      (do
        (swap! app-state assoc-in [:search-result :query] query)
        (swap! app-state assoc-in [:search-result :artists]
               (get (get result "artists") "items"))
        (swap! app-state assoc-in [:search-result :tracks]
               (last (first (rest (get result "tracks")))))))))

(defn search-component
  [data owner]
  (reify
    om/IRender
    (render [this]
      (input {
        :type "text"
        :placeholder "Search here"
        :value (:search-query data)
        :on-change #(handle-change owner data)
        :addon-before
          (r/glyphicon {:glyph "search"})
        :addon-button-after
          (button {:bs-style "primary"
                   :on-click #(make-search (:search-query data))} "Search")}))))

(defn result-component
  [data owner]
  (let [query (:query data)
        artists (:artists data)
        tracks (:tracks data)]
    (reify
      om/IRender
      (render [this]
        (if (some? query)
          (d/div #js {:className ""}
                 (d/h3 nil (str "Showing results for '" query "'"))
                 (om/build artists/artists-view (take 5 artists))
                 (om/build tracks/tracks-table tracks))
          false)))))

(om/root
  (fn [data owner]
    (reify
      om/IRender
      (render [_]
        (d/div {}
          (om/build search-component data)
          (om/build result-component (:search-result data))))))
  app-state
  {:target (. js/document (getElementById "app"))})
