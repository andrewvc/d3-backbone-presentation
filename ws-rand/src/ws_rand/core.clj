(ns ws-rand.core
  (:use aleph.http
        noir.core
        lamina.core)
  (:require
   [noir-async.core :as na]
   [noir-async.utils :as na-util]
   [noir.server :as nr-server] ))

(na/defpage-async "/rand" {} conn
  (na-util/set-interval 500 #(na/async-push conn (str (* 100 (rand))))))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "3000"))
        noir-handler (nr-server/gen-handler {:mode mode})]
    (start-http-server
      (wrap-ring-handler noir-handler)
      {:port port :websocket true})))
