package org.foxbpm.social.impl.persistence;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.social.impl.entity.SocialUser;

public class SocialManager {
	
	

	public void addSocialUser(SocialUser socialUser){
		getSqlSession().insert("addSocialUser", socialUser);
	}
	
	public SocialUser findSocialUser(String userId){
		
		return (SocialUser)getSqlSession().selectOne("findSocialUser", userId);
	}
	
	public void deleteSocialUser(String userId){
		getSqlSession().delete("deleteSocialUser", userId);
	}
	
	public void updateSocialUser(SocialUser socialUser){
		getSqlSession().update("updateSocialUser", socialUser);
	}
	
	private ISqlSession getSqlSession(){
		return Context.getCommandContext().getSqlSession();
	}
  
}
