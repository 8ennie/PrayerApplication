package databaseDAO;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Prayer_;
import database.Prayer;
import database.User;

public class PrayerDao extends JpaDao<Prayer, Integer> implements IPrayerDao{

	@Override
	public List<Prayer> getByUser(User user) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Prayer> cq = cb.createQuery(Prayer.class);
		Root<Prayer> u = cq.from(Prayer.class);
		cq.select(u);
		cq.where(cb.equal(u.get(Prayer_.prayerCreator), user));
		TypedQuery<Prayer> q = entityManager.createQuery(cq);
		List<Prayer> pList = q.getResultList();
		return pList;
	}

}
