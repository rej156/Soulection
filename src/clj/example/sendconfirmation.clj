(ns example.sendconfirmation
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [with-settings deliver-email with-delivery-mode]]
            [environ.core :refer [env]]
            [example.sendalbum :refer [send-album-url-via-email]]
            [example.db :refer [get-hash-by-email create-account verification-status-for-email]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

;;(boot.core/load-data-readers!)

(defn verification-url [email]
  (str (env :app-host) "/verifyemail/" email "?hash=" (get-hash-by-email email)))

(defn send-verification-email [email]
  (let [verification-url (verification-url email)]
    (with-settings {:host (env :email-host)
                  :user (env :email-username)
                  :pass (env :email-password)
                  :port (env :email-port)
                  :tls true
                  }
    (with-delivery-mode :smtp
      (deliver-email {:from "ericjohnjuta@gmail.com" :to email :title
  "Soulection - Verify
  your email"}
                     "email/templates/verification-email.mustache"
                     {:verification-url verification-url}
                     :text/html)))))

(defn sendconfirmation-email [req]
  (let [{{artist :artist album :album email :email} :params} req
        account-verification-status (verification-status-for-email email)]
    (if (= 1 account-verification-status)
      (do
        (send-album-url-via-email email)
        (hiccup/html
         [:h1 "An album download link has been sent to your email!"]))
      (do
        (create-account email)
        (send-verification-email email)
        (hiccup/html
         [:h1 "A verification link has been sent to your email!"])))))

;;(sendconfirmation-email "lolol@msaail.com")

;;(get-hash-by-email "ericjohnjuta@gmail.com")





;; TODO
;; Create email address template with email,hash
;; Insert into db an account with email + verified flag + hash
;; Album/email/hash
;; Look up db if all ==
;; Generate S3 Timeout url
;; Email it to them or redirect them to it!
