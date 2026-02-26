(defproject clj-uke "0.1.0-SNAPSHOT"
  :description "Ukulele note recognition"
  :url "http://github.com/bm3719/clj-uke"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[metosin/malli "0.20.0"]
                 [org.clojure/clojure "1.12.4"]]
  :repl-options {:init-ns clj-uke.core}
  :main clj-uke.core)
