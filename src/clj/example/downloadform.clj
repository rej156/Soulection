(ns example.downloadform
  (:require
   [hiccup.core        :as hiccup])
  (:use [hiccup.form]
        [hiccup.page]
        [ring.util.anti-forgery]))

(defn download-form [req]
  (let [{{artist :artist album :album} :params} req]
    (hiccup/html
     (include-css "http://d2v52k3cl9vedd.cloudfront.net/basscss/7.0.3/basscss.min.css")
     [:title "Soulection Download"]
     [:div.center
      [:h1 (str "Soulection Download for Louie Lastic")]
      [:hr]
      (form-to [:post "/louielastic"]
               (anti-forgery-field)
               (email-field {:placeholder "Email Address"} "email")
               (submit-button {:class "btn btn-outline"} "Submit"))]
     )))
