(set-env!
 :source-paths   #{"src/clj" "src/cljs"}
 :resource-paths #{"resources"}
 :repositories #(into % [["datomic"   {:url      "https://my.datomic.com/repo"
                                       :username "ericjohnjuta@gmail.com"
                                       :password "794506e6-3dc0-4211-8894-76dbe34f06a3" }]])
 :dependencies '[[adzerk/boot-cljs      "0.0-3269-2" :scope "test"]
                 [adzerk/boot-reload    "0.2.6"      :scope "test"]
                 [adzerk/boot-cljs-repl "0.1.10-SNAPSHOT" :scope "test"]
                 ;;Datomic deps, requires datomic transactor and boot-datomic to be installed locally via maven
                 [tailrecursion/boot-datomic     "0.1.0-SNAPSHOT"]
                 [com.datomic/datomic-pro "0.9.5186"]
                 ;;[ch.qos.logback/logback-classic "1.0.1"]

                 [environ "1.0.0"]
                 [danielsz/boot-environ "0.0.3"]
                 [org.danielsz/system "0.1.8"]
                 ;; [org.clojure/clojure       "1.7.0-RC2"]
                 ;;[org.clojure/clojurescript "0.0-3308"]
                 [org.clojure/tools.nrepl "0.2.10"]

                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.taoensso/sente        "1.5.0-RC2"] ; <--- Sente

   ;;; ---> Choose (uncomment) a supported web server <---
                 [http-kit                  "2.1.19"]
                 ;; [org.immutant/web       "2.0.0-beta2"]

                 [ring                      "1.4.0-RC1"]
                 [ring/ring-defaults        "0.1.5"] ; Includes `ring-anti-forgery`, etc.

                 [compojure                 "1.3.4"] ; Or routing lib of your choice
                 [hiccup                    "1.0.5"] ; Optional, just for HTML

   ;;; Transit deps optional; may be used to aid perf. of larger data payloads
   ;;; (see reference example for details):
                 [com.cognitect/transit-clj  "0.8.275"]
                 [com.cognitect/transit-cljs "0.8.220"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-reload    :refer [reload]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[reloaded.repl :refer [init start stop go reset]]
 '[example.systems :refer [dev-system]]
 '[danielsz.boot-environ :refer [environ]]
 '[tailrecursion.boot-datomic :refer [datomic]]
 '[system.boot :refer [system run]])

(deftask dev
  "Run a restartable system in the Repl"
  []
  (comp
  (environ :env {:http-port 3000
                 :db-url "datomic:dev://localhost:4334/soulection"
                 })
   (datomic :license-key "Grh/3Awg3nZwCMtgXmcp24WpK4N1GdF5e/nMvi66bOkTysUpDwYejShryL+9TAUU5PYYBBye0tHI+gr7WEHGEcPeSp2YZNdAYsJmgP6MH/5Njzj24s0ixytQifVVUIbC05N+bvyhXzWC3NXcpUkslDfYVbV4KWhtDTQbolXhUZvG573AfzVP//tpRG3yqzakI+GtEMVjBe2gqCQXtBC1YZxW9RzzLofYSdBIuvDrEq1OgxN5AKdRPiZZZIrIMr9wCuCuEy5BE/q6AfVp1XKgK1CKJBHEfYkG3NTKtfDXvzWF8fzWoqfHubiuvi69PCiJDKh0c/ztd2lhkXi7Qkddpw==")
   (watch)
   (system :sys #'dev-system :auto-start true :hot-reload true :files ["my_app.clj"])
   (cljs-repl)
   (reload)
   (cljs :source-map true)
   ;; (repl :server true)
   ))

;;Must create prod boot task with prod-system
