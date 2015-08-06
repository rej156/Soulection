(ns example.verifyemail
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings
                                              with-defaults with-settings build-email deliver-email with-delivery-mode]]
            [environ.core :refer [env]]
            [example.db :refer [verify-email-with-hash
                                verification-status-for-email get-account-id-with-hash-and-email]]
            [example.sendalbum :refer [send-album-url-via-email]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

;; Send album url if verified
;;Attempt to find account id and verify if not already
(defn verify-email [req]
  (let [{{email :email hash :hash} :params} req
        account-verification-status (verification-status-for-email email)]
    (if (= 1 account-verification-status)
      (do
        (send-album-url-via-email email)
        (hiccup/html
         [:h1 "An album download link has been sent to your email!"]))
      (let [account-id (get-account-id-with-hash-and-email email hash)]
        (if-not (nil? account-id)
          (do
            (verify-email-with-hash email hash)
            (send-album-url-via-email email)
            (hiccup/html
             [:h1 "An album download link has been sent to your email!"]))
          (hiccup/html
           [:h1 "Invalid verification link!"]))))))
