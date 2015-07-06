(ns example.components.nav.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            [taoensso.encore :as enc    :refer [tracef debugf infof warnf errorf]]
            [example.sente :refer [chsk-send!]]))

(defn test-sente-callback []
  (debugf "Button 2 was clicked (will receive reply from server)")
  (chsk-send! [:example/button2 {:had-a-callback? "indeed"}] 5000
              (fn [cb-reply] (debugf "Callback reply: %s" cb-reply))))

(defn nav []
  (let [x 1]
    (fn []
      [:nav.cleafix.py3.px1
       [:a.button.button-transparent.h1.white {:href "#/next-page"} "LOGO"]
       [:a.button.button-transparent.right.p2.white {:href "#/next-page"} "MENU"]
       [:a.button.button-transparent.right.p2.white {:href "#/next-page"} "LOGIN"]
       [:a.button.button-transparent.right.p2.white {:href "#/next-page"} "JOIN"]
       [:button#btn2 {:on-click #(test-sente-callback)} "Sente Callback button"]
       ])))
