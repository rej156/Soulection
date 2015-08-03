(ns example.systems
  (:require
   [example.my-app :refer [my-ring-handler]]
   [environ.core :refer [env]]
   [system.core :refer [defsystem]]
   [reloaded.repl :refer [system init start stop go reset]]
   [com.stuartsierra.component :as component]
   (system.components
    [datomic :refer [new-datomic-db]]
    [http-kit :refer [new-web-server]]))
 (:gen-class))

(defsystem dev-system
  [:datomic-db (new-datomic-db (env :db-url))
   :web (new-web-server (Integer. (env :http-port)) my-ring-handler)
   ])

(defsystem prod-system
  [:datomic-db (new-datomic-db (env :db-url))
   :web (new-web-server (Integer. (env :http-port)) my-ring-handler)
   ])
