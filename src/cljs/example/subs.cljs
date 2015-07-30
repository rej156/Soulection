(ns example.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe register-sub]]
            [example.components.home.view :as home]
            [example.components.next-page.view :as next-page]
            [example.components.register.view :as register]))

(defn error-page []
  [:div [:h1 "404"]])

(register-sub
 :component-to-render
 (fn [db _]
   (reaction
    (condp = (:component @db)
      :home home/component
      :next-page next-page/component
      :register register/component
      (error-page)))))
