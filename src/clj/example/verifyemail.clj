(ns example.verifyemail
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings
                                              with-defaults with-settings build-email deliver-email with-delivery-mode]]
            [environ.core :refer [env]]
            [example.db :refer [verify-email-with-hash]]
            [example.sendalbum :refer [send-album-url-via-email ]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

(defn verify-email [req]
  (let [{{email :email hash :hash} :params} req]
    (if (verify-email-with-hash email hash)
      (hiccup/html
       [:p (send-album-url-via-email)])
      (hiccup/html
       [:h1 "Unable to verify"]))))
