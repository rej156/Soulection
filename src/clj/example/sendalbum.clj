(ns example.sendalbum
  (:require [environ.core :refer [env]]
            [amazonica.core :refer [defcredential with-credential]]
            [clojurewerkz.mailer.core :refer [with-settings deliver-email with-delivery-mode]])
  (:use [amazonica.aws.s3]
        [amazonica.aws.s3transfer]
        [clj-time.core]
        ))

(defn generate-album-url []
  (with-credential [(env :aws-access-key) (env :aws-secret-key) (env :aws-endpoint)]
    (generate-presigned-url "soulection"
                            "albums/louielastic.zip"
                            (-> 5 minutes from-now))))

(defn send-album-url-via-email [email]
  (let [album-url (generate-album-url)]
    (with-settings {:host (env :email-host)
                    :user (env :email-username)
                    :pass (env :email-password)
                    :port (env :email-port)
                    :tls true
                    }
      (with-delivery-mode :smtp
        (deliver-email {:from "ericjohnjuta@gmail.com" :to email
                        :title "Download your Soulection album!"}
                       "email/templates/album-email.mustache"
                       {:album-url album-url}
                       :text/html)))))
