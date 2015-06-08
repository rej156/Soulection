(ns soulection.components.home.view
  (:require [re-frame.core :refer [dispatch subscribe dispatch-sync]]
            [soulection.components.nav.view :refer [nav]]))

(defn component []
  (let [x 1]
    (fn []
      [:div.flex.flex-column.flex-center.mx-auto
       {:style {:padding-top "14em"}}
       [:ul.list-reset.border.rounded-left.rounded-right.bg-darken-4
        [:li.inline-block.mr1.h1.white "Discover"]
        [:li.inline-block.mr1.h1.white "Curate"]
        [:li.inline-block.mr1.h1.white "Share"]
        [:li.inline-block.mr1.h1.white "Support"]
        ]
       [:h1.h1.h1-responsive.white "Music"]
       [:a.h2.h2-responsive.border.border-orange.rounded.rounded-left.rounded-right.white
        {:style {:margin-top "4em"}
         :href "#/next-page"} "Join"]
       ]
      )))
