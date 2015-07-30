package com.baodian.dao.util;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.model.user.User;
import com.baodian.util.page.Page;

@Repository("utilDao")
public abstract class UtilDao {

	public HibernateTemplate ht;
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c
	public void add(Object obj) {
		ht.save(obj);
	}

//r
	/**
	 * 检查此对象的id是否存在
	 * @return true存在	false不存在
	 */
	@SuppressWarnings("unchecked")
	public boolean chkExit(int id, String obj) {
		List<Integer> objs = ht.find("select obj.id from " + obj + " obj where obj.id=" + id);
		return objs.size()==0? false: true;
	}
	/**
	 * 根据分页查找对象
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getObjsByPage(final String countSql, final String selectSql,
			final Page page, final List<String> params) {
		return ht.executeFind(
			new HibernateCallback<List<User>>() {
				public List<User> doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(countSql);
					for(int i=0; i<params.size(); i++) {
						query.setParameter(i, params.get(i));
					}
					page.countPage(((Long) query.list().get(0)).intValue());
					if(page.getCountNums() == 0) {
						return Collections.emptyList();
					}
					
					query = s.createQuery(selectSql)
						.setFirstResult(page.getFirstNum())
						.setMaxResults(page.getNum());
					for(int i=0; i<params.size(); i++) {
						query.setParameter(i, params.get(i));
					}
					return query.list();
				}
			}
		);
	}
	/**
	 * 按条件获取部分对象
	 * @param sql
	 * @param num
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getObjs(final String sql, final int num) {
		return ht.executeFind(
			new HibernateCallback<List<User>>() {
				public List<User> doInHibernate(Session s) throws HibernateException, SQLException {
					return s.createQuery(sql).setMaxResults(num).list();
				}
			}
		);
	}
//u
	/**
	 * 更新或者删除对象
	 * @param updateSql
	 * @param params 带‘?’的参数
	 * @return the number of instances updated/deleted
	 */
	public int bulkUpdate(final String updateSql, final List<String> params) {
		return ht.executeWithNativeSession(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(updateSql);
				if (params != null) {
					for (int i = 0; i < params.size(); i++) {
						queryObject.setParameter(i, params.get(i));
					}
				}
				return queryObject.executeUpdate();
			}
		});
	}
	public void updateObject(Object obj) {
		ht.saveOrUpdate(obj);
	}
//d
	/**
	 * 更加对象id删除此对象
	 * @param id
	 */
	public void delete(String id, String obj) {
		ht.bulkUpdate("delete from " + obj + " obj where obj.id=" + id);
		//bulkUpdate("delete from " + obj + " obj where obj.id=" + id, null);
	}

}
