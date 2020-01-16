(ns rest-server.core
  (:require [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [monger.core :as mg]
            [ring.adapter.jetty :as jetty]
            [rest-server.handler :refer :all]))

(def connect (atom (mg/connect)))
(def db (atom (mg/get-db @connect "application-db")))

(defroutes app-routes
           (GET "/ping" [] ping)
           (POST "/sign-up" request (sign-up request @db))
          )

(defn -main
  [& args]
  (let [port 8080]
    (jetty/run-jetty (wrap-cors (wrap-json-body app-routes)
                                :access-control-allow-headers #{"accept"
                                                                "accept-encoding"
                                                                "accept-language"
                                                                "authorization"
                                                                "content-type"
                                                                "origin"}
                                :access-control-allow-origin [#".*"]
                                :access-control-allow-methods [:get :put :patch :options :post :delete]) {:port port})))