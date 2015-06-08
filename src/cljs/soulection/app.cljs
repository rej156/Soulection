(ns soulection.app
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            [reagent.core :as reagent :refer [atom]]
            [soulection.handlers]
            [soulection.subs]
            [soulection.routes :refer [init-routes]]))

(defn my-component []
  (let [component (subscribe [:component-to-render])]
    (fn []
      (if-let [main-component @component]
        [main-component])
      )
  ))

(defn calling-component []
  (reagent/create-class
   {:component-will-mount #(init-routes)
    :reagent-render #(my-component)}))

(defn init []
  (dispatch-sync [:initialise-db])
  (reagent/render-component [calling-component]
                            (.getElementById js/document "app")))
