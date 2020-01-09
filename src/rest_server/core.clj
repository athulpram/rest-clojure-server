(ns rest-server.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json])
  (:gen-class))

(defn hello-world [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

(defroutes app-routes
           (GET "/" [] hello-world))

(defn -main
  [& args]
  (let [port 3000]
    (server/run-server (wrap-defaults #'app-routes site-defaults)
                       {:port port})
    (println "Server started")))
