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
  [:web (new-web-server (Integer. (env :http-port)) my-ring-handler)
   :datomic-db (new-datomic-db (env :db-url))])

;; (defsystem prod-system
;;   [:web (new-web-server (Integer. (env :http-port)) my-ring-handler)
;;    :datomic-db (new-datomic-db (env :db-url))])

(defn prod-system []
  "Assembles and returns components for an application in production"
  []
    (component/system-map
     :datomic-db (new-datomic-db (env :db-url))
     :web (new-web-server (env :http-port) my-ring-handler)))

(defn -main
  "Start a production system, unless a system is passed as argument (as in the dev-run task)."
  [& args]
  (let [system (or (first args) #'prod-system)]
    (reloaded.repl/set-init! system)
    (go)))
