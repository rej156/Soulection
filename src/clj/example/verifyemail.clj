(ns example.verifyemail
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings
                                              with-defaults with-settings build-email deliver-email with-delivery-mode]]
            [environ.core :refer [env]]
            [example.db :refer [verify-email-with-hash]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

(defn verify-email [req]
  (let [{{email :email hash :hash} :params} req]
    (if (verify-email-with-hash email hash)
      (hiccup/html
       (hiccup/html
        [:h1 "Verified "]
        [:h1 email]))
      (hiccup/html
       [:h1 "Unable to verify"]))))
