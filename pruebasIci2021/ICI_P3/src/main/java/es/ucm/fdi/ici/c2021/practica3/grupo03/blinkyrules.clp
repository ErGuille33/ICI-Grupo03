;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
	(slot edible (type SYMBOL)))
	
(deftemplate INKY
	(slot edible (type SYMBOL)))
	
(deftemplate PINKY
	(slot edible (type SYMBOL)))

(deftemplate SUE
	(slot edible (type SYMBOL)))
    
;DEFINITION OF THE ACTION FACT
(deftemplate ACTION
	(slot id) (slot info (default "")) ) 
   
;RULES 
(defrule BLINKYrunsAway
	(BLINKY (edible true)) 
	=>  
	(assert (ACTION (id BLINKYrunsAway) (info "Comestible --> huir") )))
	
(defrule BLINKYmiddlePath
	(BLINKY (edible false)) 
	=> 
	(assert (ACTION (id BLINKYmiddlePath) (info "No comestible --> perseguir") )))	
	
	