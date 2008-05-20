package com.firstworldtoys.util; 

import org.compass.core.Compass;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.support.search.CompassSearchCommand;
import org.compass.core.support.search.CompassSearchHelper;

public class AndDefaultCompassSearchHelper extends CompassSearchHelper 
{ 
        public AndDefaultCompassSearchHelper(Compass compass) 
        { 
                super(compass); 
        } 
        
        public AndDefaultCompassSearchHelper(Compass compass, Integer pageSize) 
        { 
                super(compass, pageSize); 
        } 
        
        protected CompassQuery buildQuery(CompassSearchCommand searchCommand, 
      CompassSession session) 
        { 
                CompassQueryBuilder queryBuilder = session.queryBuilder(); 
                
                return queryBuilder.queryString(searchCommand.getQuery()).useAndDefaultOperator().toQuery(); 
        } 
} 