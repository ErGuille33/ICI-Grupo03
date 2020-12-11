;FACTS ASSERTED BY GAME INPUT
	
(deftemplate MSPACMAN 
    (slot edibleGhost (type SYMBOL)) )
    
;DEFINITION OF THE ACTION FACT
(deftemplate ACTION
	(slot id) (slot info (default "")))
   
;RULES 

(defrule MsPacManChaseGhost
	(MSPACMAN (edibleGhost true)) 
	=>  
	(assert (ACTION (id MsPacManChaseGhost) (info "Comestible --> Perseguir") )))
	
(defrule MsPacManChasePill
	(MSPACMAN (edibleGhost false)) 
	=> 
	(assert (ACTION (id MsPacManChasePill) (info "No comestible --> comer pill") )))	
	
	