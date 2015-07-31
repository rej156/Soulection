(ns example.verifyemail
  (:require [hiccup.core        :as hiccup]
            [clojurewerkz.mailer.core :refer [delivery-mode! with-settings
                                              with-defaults with-settings build-email deliver-email with-delivery-mode]]
            [environ.core :refer [env]])
  (:use [hiccup.form]
        [ring.util.anti-forgery]))

(defn verify-email [req]
  (let [{{email :email hash :hash} :params} req]
    (hiccup/html
     (hiccup/html
      [:h1 email]
      [:h1 hash]))))
