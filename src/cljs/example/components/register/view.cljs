(ns example.components.register.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            [taoensso.encore :as enc :refer [tracef debugf infof warnf errorf]]
            [example.sente :refer [chsk-send! chsk-state]]
            ))

(defn test-sente-callback []
  (debugf "Button 2 was clicked (will receive reply from server)")
  (chsk-send! [:example/button2 {:had-a-callback? "indeed"}] 5000
              (fn [cb-reply] (debugf "Callback reply: %s" cb-reply))))

(defn component []
  (let [x 1]
    (fn []
      [:div.register
       [:h1.white "Register-page"]
       [:button#btn2 {:on-click #(test-sente-callback)} "Sente Callback button"]
       [:a {:href "#/home"} "Click me to render the homepage!"]
       ])))

       ;;(debugf (:csrf-token @chsk-state))
