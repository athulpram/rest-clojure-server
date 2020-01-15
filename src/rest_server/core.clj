(ns rest-server.core
  (:require [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :refer [wrap-json-body]]
            [monger.core :as mg]
            [ring.adapter.jetty :as jetty]
            [rest-server.handler :refer :all]))

(def connect (atom (mg/connect)))
(def db (atom (mg/get-db @connect "application-db")))

(defroutes app-routes
           (GET "/ping" [] ping)
           (POST "/sign-up" request (sign-up request @db)))

(defn -main
  [& args]
  (let [port 8080]
    (jetty/run-jetty (wrap-json-body app-routes) {:port port})))
