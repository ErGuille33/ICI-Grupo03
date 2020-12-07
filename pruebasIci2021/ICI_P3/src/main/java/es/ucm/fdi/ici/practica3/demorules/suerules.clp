(deftemplate BLINKY
	(slot edible (type SYMBOL)))
	
(deftemplate INKY
	(slot edible (type SYMBOL)))
	
(deftemplate PINKY
	(slot edible (type SYMBOL)))

(deftemplate SUE
	(slot edible (type SYMBOL)))
    
(deftemplate ACTION
	(slot id))   
    
(defrule SUEmiddlePath
	(SUE (edible false)) => (assert (ACTION (id SUEmiddlePath))))

(defrule SUErunsAway
	(SUE (edible true)) =>  (assert (ACTION (id SUErunsAway))))