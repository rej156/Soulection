(ns example.db
  (:require
   [datomic.api :as d]
   [clojure.java.io :as io]
   [reloaded.repl :refer [system]]
   [buddy.hashers :as hashers]
   [environ.core :refer [env]]))

(def schema
  (delay
   (read-string
    (slurp (io/resource "datomic/schema.edn")))))

(def conn (:conn (:datomic-db system)))
(defn db [] (d/db conn))

(boot.core/load-data-readers!)

(defn create-schema []
  (d/transact
   conn
   @schema))

(defn delete-db []
  (d/delete-database (env :db-url)))

(defn create-account [email]
  (d/transact
   conn
   [{:db/id (d/tempid :db.part/user)
     :account/email email
     :account/verified 0
     :account/hash (hashers/encrypt (str (rand-int 1000)))}]))

(defn get-email-hash [email]
  (d/q '[:find ?hash
         :in $ ?email
         :where
         [?e :account/hash ?hash]
         [?e :account/email ?email]]
       (db) "ericjohnjuta@gmail.com"))

(get-email-hash "ericjohnjuta@gmail.com")
