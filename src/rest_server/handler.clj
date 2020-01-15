(ns rest-server.handler
  (:require [monger.collection :as mc]))

(defn sign-up [req, db]
    (mc/insert-and-return db "credentials" {:name (get-in req [:body "username"]) :password (get-in req [:body "password"])})
    )

(defn ping [req]
  {
   :status  200
   :headers {"Content-Type" "text/html"}
   :body    "pong"
   })