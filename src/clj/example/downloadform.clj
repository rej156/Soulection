(ns example.downloadform
  (:require
   [hiccup.core        :as hiccup])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

(defn download-form [req]
  (let [{{artist :artist album :album} :params} req]
    (hiccup/html
     [:h1 (str "Soulection Download for Louie Lastic")]
     [:hr]
     (form-to [:post "/louielastic"]
              (anti-forgery-field)
              (email-field {:placeholder "Email Address"} "email")
              (submit-button "Submit")))))
