(ns example.my-app
  (:require
     [clojure.string     :as str]
     [clojure.java.io :as io]
     [ring.middleware.defaults :refer [site-defaults]]
     [compojure.core     :as comp :refer (defroutes GET POST)]
     [compojure.route    :as route]
     [hiccup.core        :as hiccup]
     [clojure.core.async :as async  :refer (<! <!! >! >!! put! chan go go-loop)]
     [taoensso.timbre    :as timbre :refer (tracef debugf infof warnf errorf)]
     [datomic.api :as d]
     [org.httpkit.server :as http-kit]
     [reloaded.repl :refer [system]]
     )
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

;;;; Logging config

;; (sente/set-logging-level! :trace) ; Uncomment for more logging

;;;; Server-side setup


(defn download-form [req]
  (let [{{artist :artist album :album} :params} req]
    (hiccup/html5
     [:h1 (str "Soulection Download for Louie Lastic")]
     [:hr]
     (form-to [:post "/louielastic"]
              (anti-forgery-field)
              (email-field {:placeholder "Email Address"} "email")
              (submit-button "Submit"))
     )))

(defn verify-email [req]
  (let [{{artist :artist album :album email :email} :params} req]
    (hiccup/html5
     [:h1 email])))

(defn index-pg-handler [req]
  (-> "index.html"
      io/resource
      slurp))


(defroutes my-routes
  (GET "/louielastic" req (download-form req))
  (POST "/louielastic" req (verify-email req))

  (route/not-found "<h1>Page not found</h1>"))

(def my-ring-handler
  (let [ring-defaults-config
        (-> site-defaults
            (assoc-in [:static :resources] "/")
            ;;(assoc-in [:security :anti-forgery] {:read-token (fn [req] (-> req :params :csrf-token))})
            )]

    (ring.middleware.defaults/wrap-defaults my-routes ring-defaults-config)))
;;;; Datomic testing

(def conn (:conn (:datomic-db system)))
