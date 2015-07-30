(ns example.verifyemail
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings
                                              with-defaults with-settings build-email deliver-email with-delivery-mode]]
            [environ.core :refer [env]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

(defn verify-email [req]
  (let [{{artist :artist album :album email :email} :params} req]
    (hiccup/html
     (send-verification-email email))))

(defn send-verification-email [name]
  (with-settings {:host (env :email-host)
                  :user (env :email-username)
                  :pass (env :email-password)
                  :port (env :email-port)
                  :tls true
                  }
    (with-delivery-mode :smtp
      (deliver-email {:from "ericjohnjuta@gmail.com" :to "Eric <ericjohnjuta@gmail.com>"}
                     "email/templates/verification-email.mustache"
                     {:app-host (env :app-host)
                      :verification-url verification-url}
                     :text/html))))

;; TODO
;; Create email address template with email,hash
;; Insert into db an account with email + verified flag + hash
;; Album/email/hash
;; Look up db if all ==
;; Generate S3 Timeout url
;; Email it to them or redirect them to it!
