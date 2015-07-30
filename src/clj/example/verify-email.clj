(ns example.verify-email
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings with-defaults with-settings build-email deliver-email]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))


(defn verify-email [req]
  (let [{{artist :artist album :album email :email} :params} req]
    (hiccup/html5
     [:h1 email])))
