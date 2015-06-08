(ns soulection.components.home.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]))

(defn component []
  (let [x 1]
    (fn []
      [:div.home
       [:h1 "Home"]
       [:a {:href "#/next-page"} "Click me to render the next page!"]])))
