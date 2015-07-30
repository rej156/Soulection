(ns example.systems
  (:require
   [example.my-app :refer [my-ring-handler]]
   [taoensso.sente.server-adapters.http-kit :refer (sente-web-server-adapter)]
   ;; or
   ;; [taoensso.sente.server-adapters.immutant :refer (sente-web-server-adapter)]
   ;; Optional, for Transit encoding:
   [taoensso.sente.packers.transit :as sente-transit]
   [environ.core :refer [env]]
   [system.core :refer [defsystem]]
   (system.components
    [datomic :refer [new-datomic-db]]
    [http-kit :refer [new-web-server]])))

(defsystem dev-system
  [:web (new-web-server (Integer. (env :http-port)) my-ring-handler)
   :datomic-db (new-datomic-db (env :db-url))])

(defsystem prod-system
  [:web (new-web-server (Integer. (env :http-port)) my-ring-handler)
   :datomic-db (new-datomic-db (env :db-url))])
