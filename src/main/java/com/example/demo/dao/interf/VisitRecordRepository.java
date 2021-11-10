package com.example.demo.dao.interf;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.datastruct.VisitRecord;

/**
 * 
 * 浏览记录表访问接口
 * @author 86158
 *
 */
public interface VisitRecordRepository extends JpaRepository<VisitRecord,Integer>{
	
	/**
	 * 根据id删除
	 * @param id
	 */
	void deleteById(int id);
	
	/**
	 * 根据用户名删除
	 * @param userName
	 */
	void deleteByUserName(String userName);
	
	/**
	 * 根据名字删除
	 * @param goodsName
	 */
	void deleteByGoodsName(String goodsName);
	
	/**
	 * 根据id更新表格对应行
	 * @param id
	 * @param userName
	 * @param goodsName
	 * @param time
	 */
	@Transactional
	@Modifying
	@Query(value = "update visit_record set user_name = ?2,goods_name=?3,time=?4 where id=?1", nativeQuery = true)
	void updateById(int id,String userName,String goodsName,Timestamp time);
	
	/**
	 * 根据名字查找访问记录
	 * @param userName
	 * @return
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<VisitRecord> findByUserName(String userName);
	
	/**
	 * 根据商品名查找访问记录
	 * @param goodsName
	 * @return
	 */
	List<VisitRecord> findByGoodsName(String goodsName);
	
	
}
