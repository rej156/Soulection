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

   ;;; ---> Choose (uncomment) a supported web server and adapter <---

     [org.httpkit.server :as http-kit]

     ;; or

     ;; [immutant.web    :as immutant]

     [reloaded.repl :refer [system]]
     )
  (:use [hiccup.form]))

;;;; Logging config

;; (sente/set-logging-level! :trace) ; Uncomment for more logging

;;;; Server-side setup


(defn download-form [req]
  (let [{{artist :artist album :album} :params} req]
    (hiccup/html
     [:h1 (str "Soulection Download for " artist " - " album)]
     [:hr]
     (form-to [:post (str "/" artist "/" album)]
              (email-field "email")
              (submit-button "Submit"))
     )))

(defn verify-email [req]
  (let [{{artist :artist album :album email :email} :params} req]
    [:h1 "lol"]))

(defn index-pg-handler [req]
  (-> "index.html"
      io/resource
      slurp))


(defroutes my-routes
  ;; (GET  "/"      req (index-pg-handler req))
  ;; (GET "/landing" req (landing-pg-handler req))
  ;; (GET  "/chsk"  req ((:ring-ajax-get-or-ws-handshake (:sente system)) req))
  ;; (POST "/chsk"  req ((:ring-ajax-post (:sente system)) req))
  ;; (POST "/login" req (login! req))
  ;; (POST "/logout" req (logout! req))
  (GET "/:artist/:album" req (download-form req))
  (POST "/:artist/:album" req (verify-email req))

  (route/not-found "<h1>Page not found</h1>"))

(def my-ring-handler
  (let [ring-defaults-config
        (-> site-defaults
            (assoc-in [:static :resources] "/")
            ;; (assoc-in [:security :anti-forgery] {:read-token (fn [req] (-> req :params :csrf-token))})
            )]

    (ring.middleware.defaults/wrap-defaults my-routes ring-defaults-config)))
;;;; Datomic testing

(def conn (:conn (:datomic-db system)))
