(ns rest-server.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]
            [monger.core :as mg]
            [clojure.data.json :as json]
            [monger.collection :as mc])
  (:gen-class))

(defn show-names [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    ((let [conn (mg/connect)
                   db (mg/get-db conn "tic-tac-toe")
                   coll "documents"]
               (mc/insert db coll {:first_name "John" :last_name "Lennon"})
               (mc/insert db coll {:first_name "Ringo" :last_name "Starr"})))})

(defroutes app-routes
           (GET "/" [] show-names))

(defn -main
  [& args]
  (let [port 3000]
    (server/run-server (wrap-defaults #'app-routes site-defaults)
                       {:port port})
    (println "Server started")))
