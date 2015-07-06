(ns example.app
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            [reagent.core :as reagent :refer [atom]]
            [example.handlers]
            [example.subs]
            [example.routes :refer [init-routes]]
            [example.sente :refer [init-websocket]]
            [example.components.nav.view :refer [nav]]))

(defn my-component []
  (let [component (subscribe [:component-to-render])]
    (fn []
      (if-let [main-component @component]
        [:header.bg-cover
         {:style {:background-image
                  "url(https://d262ilb51hltx0.cloudfront.net/max/2000/1*DZwdGMaeu-rvTroJYui6Uw.jpeg)"
                  :min-height "100vh"}}

         [nav]
         [main-component]
         ]))
    ))

(defn calling-component []
  (reagent/create-class
   {:component-will-mount #(do
                             (init-routes)
                             ;; (init-websocket)
                             )
    :reagent-render #(my-component)}))

(defn init []
  (dispatch-sync [:initialise-db])
  (reagent/render-component [calling-component]
                            (.getElementById js/document "app")))

;;(init)
