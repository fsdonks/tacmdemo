;;This is a demonstration script for
;;applying proposed TACM methods using
;;M4.  Where possible, we'll even show
;;some programmatic methods for modifying
;;the supply and substitution inputs
;;to save time on individuals having to munge
;;data....
(ns tacmdemo
  (:require [marathon 
             [core :as core] 
             [analysis :as analysis]]
            [spork.util [io :as io]]))
            

;;let's assume we have a path to a marathon workbook.
;;Change the path to suit (or create a similar folder structure).
(def path (io/file-path "~/Documents/tacmdemo/base-testdata-v7.xlsx"))

;;The simplest operation we'd like to perform is to execute
;;a post-processed run against the workbook, as if the user
;;had used the gui.

;;Thankfully, all of the gui functions have similarly-named
;;handler functions designed for programmatic access in the
;;marathon.core namespace, which makes this trivial.

;;The difference is, we'd like to add in some custom output
;;that's NOT turned on by default.  If you look through
;;the function calls in the source, there's a function
;;marathon.run/do-audited-run, which is leveraged.  This
;;function delegates to marathon.analyis/spit-history!, which
;;handles the low level grunt work of generating the simulation
;;history, computing eventful results, spitting to output files,
;;etc.  To do this, marathon.analysis/spit-history! looks to see
;;which outputs are required.  M4 has a bunch of possible outputs,
;;some of which can be sizeable either store or compute.  They
;;are delineated in the set below, copied from 
;;marathon.analysis/all-outputs:
#_(def all-outputs #{:history
                     :location-samples ;:locsamples
                     :locations
                     :depsamples
                     :deployment-records
                     :demandtrends
                     :patches
                     :event-log})

;;By default, we don't compute all these outputs, rather the sole 
;;simulation results that are typically needed (aka legacy-outputs):
#_(def legacy-outputs #{:deployment-records
                        :demandtrends
                        :locations
                        :event-log})

;;So, when M4 goes to simulate and spit outputs via 
;;marathon.analysis/spite-history!, it consults a var to infer which
;;outputs are required. These are stored in marathon.analysis/*outputs*, 
;;which is  a dynamic var in Clojure parlance.  That means, it's got
;;a default value associated with it - legacy-outputs - but the user
;;can override that value pretty easily by providing a different set
;;of inputs (which must be a subset of the known all-outputs), and
;;binding that to the *outputs* var prior to a run.

;;In our case, for TACM, we'd like to know additional information about
;;where units are in their readiness disposition, as indicated by unit location.
;;Fortunately, the :location-samples output contains this information, 
;;which when requested, will populate a tab-delimited file that captures
;;the location of every unit at every eventful point in the simulation (e.g.
;;when a unit moves).  This is similar in spirit to the discrete default
;; :locations output, which stores a log of discrete unit movements in
;;locations.txt, except that the output provided in locsamples.txt will
;;sampled for all units every time there's movement, and will be 
;;trivially pivoted or agreggated to provide a look at the disposition
;;of units over time (e.g. by location) via something like a stacked
;;area chart.

(binding [analysis/*outputs* (conj analysis/*outputs* :location-samples)]
  (core/post-processed-run path))

;;We get the typical post-processed run like normal, including charts, 
;;and the standard outputs, but we now have a text file
;;locsamples.txt.

;;More to follow (programmatic examples for munging the inputs to
;;create substitutions and srcs....).