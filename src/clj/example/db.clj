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

(defn create-schema []
  (d/transact
   conn
   @schema))

(defn delete-db []
  (d/delete-database (env :db-url)))
;;(delete-db)

(defn create-account [email]
  (d/transact
   conn
   [{:db/id (d/tempid :db.part/user)
     :account/email email
     :account/verified 0
     :account/hash (hashers/encrypt
                    (str (rand-int 1000)))}]))

;;(create-account "lol@test.com")

(defn get-hash-by-email [email]
  (->> (d/q '[:find ?hash
              :in $ ?email
              :where
              [?e :account/hash ?hash]
              [?e :account/email ?email]]
            (db) email)
       ffirst))

;;(def my-hash (get-hash-by-email "ericjohnjuta@gmail.com"))

(defn get-email-by-hash [hash]
  (->> (d/q '[:find ?email
              :in $ ?hash
              :where
              [?e :account/email ?email]
              [?e :account/hash ?hash]]
            (db) hash)
       ffirst))

(defn get-account-id-with-hash-and-email [email hash]
  (->> (d/q '[:find ?e
              :in $ ?email ?hash
              :where
              [?e :account/email ?email]
              [?e :account/hash ?hash]]
            (db) email hash)
       ffirst))

;;(get-account-id-with-hash-and-email "lol@test.com" (get-hash-by-email "lol@test.com"))

(defn verify-email-with-hash [email hash]
  (let [account (get-account-id-with-hash-and-email email hash)]
    (d/transact
     conn
     [{:db/id account
       :account/verified 1}])))

(defn verification-status-for-email [email]
  (->> (d/q '[:find ?verified
              :in $ ?email
              :where
              [?e :account/email ?email]
              [?e :account/verified ?verified]]
            (db) email)
       ffirst))


;;(boot.core/load-data-readers!)
;;(verification-status-for-email "ericjohnjuta@gmail.com")
