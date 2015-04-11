(ns rabbit.core
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))


(def ^{:const true}
  default-exchange-name "")

(defn -main
  [message]

  (println message)
  (let [conn  (rmq/connect {:host "10.240.128.1"})
        ch    (lch/open conn)
        qname "test3"]
    (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lq/declare ch qname {:exclusive false :auto-delete true})
    (lb/publish ch default-exchange-name qname (str message) {:content-type "text/plain" :type "scale"})
    (Thread/sleep 10000)
    (println "[main] Disconnecting...")
    (rmq/close ch)
    (rmq/close conn)))


