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
     :account/hash (hashers/encrypt
                    (str (rand-int 1000))
                    {:algorithm :md5})}]))
;;(create-account "lol@test.com")

(defn get-email-hash [email]
  (-> (d/q '[:find [(pull ?e [:account/hash]) ...]
             :in $ ?email
             :where
             [?e :account/hash ?hash]
             [?e :account/email ?email]]
           (db) email)
      (first)
      :account/hash))

;;(get-email-hash "lol@test.com")
(defn get-email-by-hash [hash]
  (-> (d/q '[:find [(pull ?e [:account/email]) ...]
             :in $ ?hash
             :where
             [?e :account/email ?email]
             [?e :account/hash ?hash]]
           (db) hash)
      (first)
      :account/email))

(defn verify-email-with-hash [email hash]
  (let [account (-> (d/q '[:find ?e
                           :in $ ?email ?hash
                           :where
                           [?e :account/email ?email]
                           [?e :account/hash ?hash]]
                         (db) email hash)
                    (ffirst))]
    (-> (d/transact
         conn
         [{:db/id account
           :account/verified 1}]))))

(defn verification-status-for-email [email]
  (d/q '[:find ?verified
         :in $ ?email
         :where
         [?e :account/email ?email]
         [?e :account/verified ?verified]]
       (db) email))
