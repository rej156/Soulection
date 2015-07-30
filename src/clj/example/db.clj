(ns example.db
  (:require
   [datomic.api :as d]
   [clojure.java.io :as io]
   [reloaded.repl :refer [system]]
   [buddy.hashers :as hashers])

(def schema
  (delay
    (read-string
      (slurp (io/resource "datomic/schema.edn"))))

(def conn (:conn (:datomic-db system)))

(defn create-schema []
  (d/transact
   conn
   @schema))

(defn create-account [conn email]
  (d/transact
   conn
   [{:db/id #db/id[:db.part/account]
     :account/email email
     :user/verified 0
     :user/hash (hashers/encrypt (rand-int 1000)))}]))
