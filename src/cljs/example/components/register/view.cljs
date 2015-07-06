(ns example.components.register.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]))

(defn component []
  (let [x 1]
    (fn []
      [:div.register
       [:h1.white "Register-page"]
       [:a {:href "#/home"} "Click me to render the homepage!"]])))
