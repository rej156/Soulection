(ns example.sendalbum
  (:require [environ.core :refer [env]]
            [amazonica.core :refer [defcredential]])
  (:use [amazonica.aws.s3]
        [amazonica.aws.s3transfer]
        [clj-time.core]
        ))

(defcredential (env :aws-access-key) (env :aws-secret-key) (env :aws-endpoint))

(defn send-album-url-via-email []
  (generate-presigned-url "soulection"
                          "albums/louielastic.zip"
                          (-> 5 minutes from-now)))
