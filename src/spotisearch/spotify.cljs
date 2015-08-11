(ns ^:figwheel-always spotisearch.spotify
  (:require [clojure.string :as str]
            [ajax.core :refer [GET]]))

(def base-url
  "https://api.spotify.com/")

(defn build-url
  [endpoint]
  (str/join [base-url endpoint]))

(defn search
  "Returns a list of search results"
  [query query-type callback]
  (GET (str (build-url "v1/search/") "?q=" query "&type=" query-type)
       {:handler (fn [result]
                   (callback result))}))

(defn top-tracks-for-artist
  "Returns a list with the top artists for the given artist"
  [artist-uri callback]
  (GET (str/join [(build-url "v1/artists/")
                  artist-uri
                  "/top-tracks?country=se"])
       {:handler (fn [result]
                   (callback (last (first result))))}))
