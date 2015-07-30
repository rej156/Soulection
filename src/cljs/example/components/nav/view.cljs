(ns example.components.nav.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            ))

(defn nav []
  (let [x 1]
    (fn []
      [:nav.cleafix.py3.px1
       [:a.button.button-transparent.h1.white {:href "#/next-page"} "LOGO"]
       [:a.button.button-transparent.right.p2.white {:href "#/next-page"} "MENU"]
       [:a.button.button-transparent.right.p2.white {:href "#/next-page"} "LOGIN"]
       [:a.button.button-transparent.right.p2.white {:href "#/register"} "JOIN"]
       ])))
