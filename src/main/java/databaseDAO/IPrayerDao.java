package databaseDAO;

import java.util.List;

import database.Prayer;
import database.User;

public interface IPrayerDao extends IDao<Prayer,Integer>{

	public List<Prayer> getByUser(User userser);
	
}

